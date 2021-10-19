package com.ry.demo.elevatormgr.model;

import java.util.Map;

public class Elevator {
	
	private String denomination;
	private Integer currentFloor;
	private Double currentWeight; 
	private Double maxWeight;
	private Map<Floor, Boolean> availableFloors;
	
	public Elevator() {}
	
	public Elevator(String denomination, Double maxWeight) {
		this.denomination = denomination;
		this.currentFloor = 0;
		this.currentWeight = 0.0;
		this.maxWeight = maxWeight;
	}
	
	public String getDenomination() {
		return denomination;
	}
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}
	public Integer getCurrentFloor() {
		return currentFloor;
	}
	public void setCurrentFloor(Integer currentFloor) {
		this.currentFloor = currentFloor;
	}
	public Double getCurrentWeight() {
		return currentWeight;
	}
	public void setCurrentWeight(Double currentWeight) {
		this.currentWeight = currentWeight;
	}
	public Double getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}
	public Map<Floor, Boolean> getAvailableFloors() {
		return availableFloors;
	}
	public void setAvailableFloors(Map<Floor, Boolean> availableFloors) {
		this.availableFloors = availableFloors;
	}
}
