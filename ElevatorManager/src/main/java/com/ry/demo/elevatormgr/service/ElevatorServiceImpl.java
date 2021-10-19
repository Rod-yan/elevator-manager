package com.ry.demo.elevatormgr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ry.demo.elevatormgr.dao.ElevatorDao;
import com.ry.demo.elevatormgr.error.AlarmMechanismException;
import com.ry.demo.elevatormgr.error.EntityNotFoundException;
import com.ry.demo.elevatormgr.model.Elevator;

@Service("elevatorService")
public class ElevatorServiceImpl implements ElevatorService {
	
	private static final Logger logger = LoggerFactory.getLogger(ElevatorServiceImpl.class);
	
	@Autowired
	private ElevatorDao elevatorDao;
	
	public Elevator getElevatorByDenomination(String denomination) {
		return elevatorDao.getElevatorByDenomination(denomination);
	}
	
	public void updateElevator(Elevator elevator) {
		Elevator entity = getElevatorByDenomination(elevator.getDenomination());
		if (entity != null) {
			// Alarm mechanism
			if (entity.getMaxWeight() < elevator.getCurrentWeight()) {
				logger.warn("[HTTP 403] Forbidden error: Elevator: <"+entity.getDenomination()+"> has turned its alarm ON, max weight surpassed. Will not operate");
				throw new AlarmMechanismException();
			}
			
			elevatorDao.updateElevator(elevator);		
			logger.info("Elevator: <"+entity.getDenomination()+"> status has been updated successfully");
		} else {
			throw new EntityNotFoundException();
		}
	}
}
