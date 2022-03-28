package com.parkinglot.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parked_vehicles")
public class ParkedVehicles implements Serializable{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String registrationNo;
	private BigDecimal ratePerMinute;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private BigDecimal vehicleWeight;
	private Long spotId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public BigDecimal getRatePerMinute() {
		return ratePerMinute;
	}
	public void setRatePerMinute(BigDecimal ratePerMinute) {
		this.ratePerMinute = ratePerMinute;
	}
	public LocalDateTime getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(LocalDateTime entryTime) {
		this.entryTime = entryTime;
	}
	public LocalDateTime getExitTime() {
		return exitTime;
	}
	public void setExitTime(LocalDateTime exitTime) {
		this.exitTime = exitTime;
	}
	public Long getSpotId() {
		return spotId;
	}
	public void setSpotId(Long spotId) {
		this.spotId = spotId;
	}
	public BigDecimal getVehicleWeight() {
		return vehicleWeight;
	}
	public void setVehicleWeight(BigDecimal vehicleWeight) {
		this.vehicleWeight = vehicleWeight;
	}	
	
}
