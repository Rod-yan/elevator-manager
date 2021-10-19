package com.ry.demo.elevatormgr.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Building {
	
	private Integer id;
	private List<Floor> floors;
	private Set<Elevator> elevators;
	private String description;
	
	private static Building instance;
    
    private Building() {
    	id = 1;
    	elevators = new HashSet<Elevator>(0);
    	description = "Default Building";
    	this.floors = new ArrayList<Floor>();
    	int i = -1;
    	while (i <= 50) {
    		this.floors.add(new Floor(i++));
    	}
    	
    	Elevator publ = new Elevator("Public", 1000.0);
    	HashMap<Floor, Boolean> publicFloorsMap = new HashMap<Floor, Boolean>();
    	publicFloorsMap.put(new Floor(-1), true);
    	i = 0;
    	while (i <= 49) {
    		publicFloorsMap.put(new Floor(i++), false);
    	}
    	publicFloorsMap.put(new Floor(50), true);
    	publ.setAvailableFloors(publicFloorsMap);
    	elevators.add(publ);
    	
    	i = -1;
    	Elevator freight = new Elevator("Freight", 3000.0);
    	HashMap<Floor, Boolean> freightFloorsMap = new HashMap<Floor, Boolean>();
    	while (i <= 50) {
    		freightFloorsMap.put(new Floor(i++), false);
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
		return floors;
	}
	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}
	public Set<Elevator> getElevators() {
		return elevators;
	}
	public void setElevators(Set<Elevator> elevators) {
		this.elevators = elevators;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
