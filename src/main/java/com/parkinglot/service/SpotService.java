package com.parkinglot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkinglot.data.Floors;
import com.parkinglot.data.ParkedVehicles;
import com.parkinglot.data.Spots;
import com.parkinglot.dto.Car;
import com.parkinglot.repository.FloorRepository;
import com.parkinglot.repository.SpotsRepository;
import com.parkinglot.util.Constants;
import com.parkinglot.util.VacancyStatus;

@Service
public class SpotService {
	
	private SpotsRepository spotsRepository;	
	private FloorsService floorService;
	
	@Autowired
	public SpotService(SpotsRepository spotsRepository,FloorsService floorService){
	this.floorService=floorService;
	this.spotsRepository=spotsRepository;
	}
	
	/*parking spot is identified based on vehicle weight and height, such that floors having 
	height>=vehicleHeight and available weight capacity>=vehicleWeight are selected and then 
	the floor with minimum difference between its height and vehicle height is selected for spot
	and one spot from that floor no is assigned for the entering vehicle*/
	
	public Long markSpot(Car car) throws Exception {

		Floors floor=floorService.getSuitableFloor(car);
		List<Spots> availableSpots= spotsRepository.findAllByStatusAndFloorNoOrderByIdDesc(VacancyStatus.VACANT.name(),floor.getId());
		if(availableSpots==null || availableSpots.size()<1)
		throw new Exception(Constants.NOT_AVAILABLE);
		Spots selectedSpot=availableSpots.get(0);
		selectedSpot.setStatus(VacancyStatus.OCCUPIED.name());
		spotsRepository.save(selectedSpot);
		floorService.updateFloorStatsAfterEntry(floor, car);
		return selectedSpot.getId();

	}
	
	public void releaseSpot(ParkedVehicles vehicle) {
		Optional<Spots> spot=spotsRepository.findById(vehicle.getSpotId());
		spot.get().setStatus(VacancyStatus.VACANT.name());
		spotsRepository.save(spot.get());	
		floorService.updateFloorStatsAfterExit(spot.get(), vehicle);
	}
	
}
