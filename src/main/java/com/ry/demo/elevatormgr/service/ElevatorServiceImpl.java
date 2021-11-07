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
	
	private static final Logger LOG = LoggerFactory.getLogger(ElevatorServiceImpl.class);
	
	private ElevatorDao elevatorDao;
	
	@Autowired
	ElevatorServiceImpl(ElevatorDao elevatorDao) {
        this.elevatorDao = elevatorDao;
    }
	
	public Elevator getElevatorByDenomination(String denomination) {
		return elevatorDao.getElevatorByDenomination(denomination);
	}
	
	public void updateElevator(Elevator elevator) {
		Elevator entity = getElevatorByDenomination(elevator.getDenomination());
		if (entity != null) {
			// Alarm mechanism
			if (entity.getMaxWeight() < elevator.getCurrentWeight()) {
				LOG.warn("[HTTP 403] Forbidden error: Elevator: <{0}> has turned its alarm ON, "
				        + "max weight surpassed. Will not operate", entity.getDenomination());
				throw new AlarmMechanismException();
			}
			
			elevatorDao.updateElevator(elevator);		
			LOG.info("Elevator: <{0}> status has been updated successfully", entity.getDenomination());
		} else {
			throw new EntityNotFoundException();
		}
	}
}
