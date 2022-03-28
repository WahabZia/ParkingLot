package com.parkinglot.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkinglot.data.ParkedVehicles;
import com.parkinglot.dto.Car;
import com.parkinglot.repository.ParkedVehicleRepository;
import com.parkinglot.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
public class ParkingService {
	
	private ParkedVehicleRepository parkedVehicleRepository; 
	private SpotService spotService; 
	
	@Autowired
	public ParkingService(ParkedVehicleRepository parkedVehicleRepository,SpotService spotService){
		this.parkedVehicleRepository=parkedVehicleRepository;
		this.spotService=spotService;
	}
	
	@Transactional
	public Long entry(Car car) throws Exception {
		ParkedVehicles vehicle=new ParkedVehicles();
		vehicle.setEntryTime(LocalDateTime.now());
		vehicle.setRegistrationNo(car.getRegNo());
		//Rate Per Minute Logic could be changed as it was not specified in the problem statement .as per this implementation ratePerMinute=weight*.1+height*.1
		BigDecimal ratePerMinute=car.getHeight().multiply(BigDecimal.valueOf(.1)).add(car.getWeight().multiply(BigDecimal.valueOf(.1))); 
		vehicle.setRatePerMinute(ratePerMinute);
		vehicle.setSpotId(spotService.markSpot(car));
		vehicle.setVehicleWeight(car.getWeight());
		parkedVehicleRepository.save(vehicle);
		transportVehicleToSpot(vehicle);
	    System.out.println("Entry Done at : "+vehicle.getEntryTime());
	    //For now i am returning assigned spot id but here we could have a receipt id returned which user can then use to 
	    return vehicle.getSpotId();
	}
	
	@Transactional
	public void exit(Car car) {
		ParkedVehicles vehicle= parkedVehicleRepository.findByRegistrationNoAndExitTimeIsNull(car.getRegNo());
		vehicle.setExitTime(LocalDateTime.now());
		spotService.releaseSpot(vehicle);
		charge(vehicle);
		transportVehicleToSpot(vehicle);
		parkedVehicleRepository.save(vehicle);
	}
	
	public void charge(ParkedVehicles vehicle) {
		//For total charge calculation total parked time duration is calculated in milliseconds between entry time exit time and then then converted into minutes for multiplication  
		BigDecimal totalCharges=vehicle.getRatePerMinute().
				multiply(BigDecimal.valueOf(ChronoUnit.MILLIS.between(vehicle.getEntryTime(), vehicle.getExitTime())).divide(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(60)));
		totalCharges=Util.roundDecimalToHalfEvenWithScale(totalCharges, 2);
		System.out.println("Amount Charged : "+totalCharges);

		//transaction could be processed from here after
	}
	
	public void transportVehicleToSpot(ParkedVehicles vehicle) {
	    System.out.println("Moving Vehicle NO : "+vehicle.getRegistrationNo() +"  To Spot ID "+vehicle.getSpotId());

	}
}
