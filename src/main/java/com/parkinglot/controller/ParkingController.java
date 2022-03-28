package com.parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.Car;
import com.parkinglot.service.ParkingService;
import com.parkinglot.validation.ParkingValidation;

@RestController
@RequestMapping("/api")
public class ParkingController {
    
	private ParkingValidation parkingValidation; 
	private ParkingService parkingService; 
	
	@Autowired
	private ParkingController(ParkingService parkingService,ParkingValidation parkingValidation) {
		this.parkingService=parkingService;
		this.parkingValidation=parkingValidation;
	}
	
	@PostMapping(value = "/enter")  
	public String enter(@RequestBody Car car) {
		try {
			parkingValidation.entryValidation(car);	
			return "Spot No : "+parkingService.entry(car);			
		} catch (Exception e) {
			return e.getMessage();
		}
      }
	
	@PostMapping(value = "/exit")  
	public String exit(@RequestBody Car car) {
		try {
			parkingValidation.exitValidation(car);
			parkingService.exit(car);	
			return "Exited Successfully ";
		} catch (Exception e) {
			return e.getMessage();
		}
      }
	
}
