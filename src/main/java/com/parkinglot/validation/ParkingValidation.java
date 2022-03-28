package com.parkinglot.validation;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parkinglot.dto.Car;
import com.parkinglot.repository.ParkedVehicleRepository;
import com.parkinglot.repository.SpotsRepository;
import com.parkinglot.util.Constants;
import com.parkinglot.util.VacancyStatus;

@Component
public class ParkingValidation {
	
	private ParkedVehicleRepository parkedVehicleRepository;
	private SpotsRepository spotsRepository;
	
	@Autowired
	private ParkingValidation(ParkedVehicleRepository parkedVehicleRepository,SpotsRepository spotsRepository){
		this.parkedVehicleRepository=parkedVehicleRepository;
		this.spotsRepository=spotsRepository;
	}
	
	public void entryValidation(Car car) throws Exception {
		if(car.getRegNo()==null || car.getRegNo().isEmpty())
			throw new Exception(Constants.Valid_REGISTRATION_REQUIRED);
		if(car.getWeight()==null || car.getWeight().compareTo(BigDecimal.ONE)==-1)
			throw new Exception(Constants.VALID_WEIGHT_REQUIRED);
		if(car.getHeight()==null || car.getHeight().compareTo(BigDecimal.ONE)==-1)
			throw new Exception(Constants.VALID_HEIGHT_REQUIRED);
		if(spotsRepository.findCountByStatus(VacancyStatus.VACANT.name())<1)
			throw new Exception(Constants.NOT_AVAILABLE);
		if(parkedVehicleRepository.findByRegistrationNoAndExitTimeIsNull(car.getRegNo())!=null)
			throw new Exception(Constants.ALREADY_PRESENT);
	}
	
	public void exitValidation(Car car) throws Exception {
		if(parkedVehicleRepository.findByRegistrationNoAndExitTimeIsNull(car.getRegNo())==null)
			throw new Exception(Constants.VEHICLE_NOT_PRESENT);
	}
}
