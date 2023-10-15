package com.ry.demo.elevatormgr.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Building {

  private static Building instance;

  private Integer id;
  private List<Floor> floors;
  private Set<Elevator> elevators;
  private String description;

  private Building() {
    final int UPPERFLOORLIMIT = 50;
    final Double PUBLIC_ELEVATOR_MAXCAPACITY = 1000.0;
    final Double FREIGHT_ELEVATOR_MAXCAPACITY = 1000.0;

    id = 1;
    elevators = new HashSet<>(0);
    description = "Default Building";
    this.floors = new ArrayList<>();
    int i = -1;
    while (i <= UPPERFLOORLIMIT) {
      this.floors.add(new Floor(i));
      i++;
    }

    Elevator publ = new Elevator("Public", PUBLIC_ELEVATOR_MAXCAPACITY);
    HashMap<Floor, Boolean> publicFloorsMap = new HashMap<>();
    publicFloorsMap.put(new Floor(-1), true);
    i = 0;
    while (i <= 49) {
      publicFloorsMap.put(new Floor(i), false);
      i++;
    }
    publicFloorsMap.put(new Floor(50), true);
    publ.setAvailableFloors(publicFloorsMap);
    elevators.add(publ);

    i = -1;
    Elevator freight = new Elevator("Freight", FREIGHT_ELEVATOR_MAXCAPACITY);
    HashMap<Floor, Boolean> freightFloorsMap = new HashMap<>();
    while (i <= UPPERFLOORLIMIT) {
      freightFloorsMap.put(new Floor(i), false);
      i++;
    }
    freight.setAvailableFloors(freightFloorsMap);
    elevators.add(freight);
  }

  public static synchronized Building getInstance() {
    if (instance == null) {
      instance = new Building();
    }
    return instance;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<Floor> getFloors() {
    return new ArrayList<>(floors);
  }

  public void setFloors(List<Floor> floors) {
    this.floors = new ArrayList<>(floors);
  }

  public Set<Elevator> getElevators() {
    return new HashSet<>(elevators);
  }

  public void setElevators(Set<Elevator> elevators) {
    this.elevators = new HashSet<>(elevators);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
