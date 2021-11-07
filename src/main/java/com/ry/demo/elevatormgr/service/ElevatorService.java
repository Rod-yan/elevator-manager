package com.ry.demo.elevatormgr.service;

import com.ry.demo.elevatormgr.model.Elevator;

public interface ElevatorService {
	
	Elevator getElevatorByDenomination(String denomination);
    
    void updateElevator(Elevator elevator);
    
}
