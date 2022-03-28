package com.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkinglot.data.ParkedVehicles;

public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicles, Long> {
	public ParkedVehicles findByRegistrationNoAndExitTimeIsNull(String regNo);
}
