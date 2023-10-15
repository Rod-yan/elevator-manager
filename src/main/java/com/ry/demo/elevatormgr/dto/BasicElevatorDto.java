package com.ry.demo.elevatormgr.dto;

import java.util.Map;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ry.demo.elevatormgr.model.Floor;

public class BasicElevatorDto {

  @JsonProperty(access = Access.READ_ONLY)
  private String denomination;

  @NotNull(message = "{elevator.currentFloor.null_value}")
  @Min(value = -1, message = "{elevator.currentFloor.lower_limit_exceeded}")
  @Max(value = 50, message = "{elevator.currentFloor.upper_limit_exceeded}")
  private Integer currentFloor;
  @NotNull(message = "{elevator.currentWeight.null_value}")
  @DecimalMin(value = "0.0", message = "{elevator.currentWeight.lower_limit_exceeded}")
  @DecimalMax(value = "3500.0", message = "{elevator.currentWeight.upper_limit_exceeded}")
  private Double currentWeight;

  @JsonProperty(access = Access.READ_ONLY)
  private Float maxWeight;

  @JsonIgnore
  private Map<Floor, Boolean> availableFloors;

  @JsonIgnore
  public String getDenomination() {
    return denomination;
  }

  public void setDenomination(String denomination) {
    this.denomination = denomination;
  }

  @JsonIgnore
  public Float getMaxWeight() {
    return maxWeight;
  }

  public void setMaxWeight(Float maxWeight) {
    this.maxWeight = maxWeight;
  }

  public Double getCurrentWeight() {
    return currentWeight;
  }

  public void setCurrentWeight(Double currentWeight) {
    this.currentWeight = currentWeight;
  }

  public Integer getCurrentFloor() {
    return currentFloor;
  }

  public void setCurrentFloor(Integer currentFloor) {
    this.currentFloor = currentFloor;
  }

  @JsonIgnore
  public Map<Floor, Boolean> getAvailableFloors() {
    return availableFloors;
  }

  public void setAvailableFloors(Map<Floor, Boolean> availableFloors) {
    this.availableFloors = availableFloors;
  }

}
