package com.parkinglot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkinglot.data.Floors;
import com.parkinglot.data.ParkedVehicles;
import com.parkinglot.data.Spots;
import com.parkinglot.dto.Car;
import com.parkinglot.repository.FloorRepository;
import com.parkinglot.util.Constants;

@Service
public class FloorsService {
	
	private FloorRepository floorRepository;
	
	@Autowired 
	public FloorsService (FloorRepository floorRepository){
		this.floorRepository=floorRepository;
	}
	
	public Floors getSuitableFloor(Car car) throws Exception{
		
		List<Floors> floors=floorRepository.findFloorsWithAvalableCapacity(car.getWeight(),car.getHeight());
		if(floors==null || floors.size()<1)
			throw new Exception(Constants.NOT_AVAILABLE);		
		Floors floor= floors.stream().sorted((f1, f2)->f1.getMaxHeight().subtract(car.getHeight()).
                compareTo(f2.getMaxHeight().subtract(car.getHeight()))).
                collect(Collectors.toList()).get(0);
		return floor;
	}
	
	public void updateFloorStatsAfterEntry(Floors floor,Car car) {
		floor.setCurrentWeight(floor.getCurrentWeight().add(car.getWeight()));
		floorRepository.save(floor);	
	}
	
	public void updateFloorStatsAfterExit(Spots spot,ParkedVehicles parkedVehicle) {
		floorRepository.updateFloorStatusAfterExit(spot.getFloorNo(),parkedVehicle.getVehicleWeight());

	}
}
