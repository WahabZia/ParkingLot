package com.parkinglot.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkinglot.data.Floors;

public interface FloorRepository extends JpaRepository<Floors, Long> {
	
    @Query("SELECT f FROM Floors f WHERE f.currentWeight+:waight<=f.maxWeight and  f.maxHeight>=:height")
	public List<Floors> findFloorsWithAvalableCapacity(@Param("waight") BigDecimal vehicleWeight, @Param("height") BigDecimal vehicleHeight);

    @Modifying
    @Query("update Floors f set f.currentWeight =f.currentWeight-:weight  where f.id=:id")
    void updateFloorStatusAfterExit(@Param("id") Long id,@Param("weight") BigDecimal vehicleWeight );

}
