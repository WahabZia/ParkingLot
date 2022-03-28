package com.parkinglot.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "floors")
public class Floors implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal maxHeight;
	private BigDecimal maxWeight;
	private BigDecimal currentWeight;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(BigDecimal maxHeight) {
		this.maxHeight = maxHeight;
	}
	public BigDecimal getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(BigDecimal maxWeight) {
		this.maxWeight = maxWeight;
	}
	public BigDecimal getCurrentWeight() {
		return currentWeight;
	}
	public void setCurrentWeight(BigDecimal currentWeight) {
		this.currentWeight = currentWeight;
	}
	
}