package com.ry.demo.elevatormgr.service;

import com.ry.demo.elevatormgr.dao.ElevatorDao;
import com.ry.demo.elevatormgr.error.AlarmMechanismException;
import com.ry.demo.elevatormgr.error.EntityNotFoundException;
import com.ry.demo.elevatormgr.model.Elevator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("elevatorService")
public class ElevatorServiceImpl implements ElevatorService {

  private static final Logger LOG = LoggerFactory.getLogger(ElevatorServiceImpl.class);

  private final ElevatorDao elevatorDao;

  @Autowired
  ElevatorServiceImpl(ElevatorDao elevatorDao) {
    this.elevatorDao = elevatorDao;
  }

  public Elevator getElevatorByDenomination(String denomination) {
    return elevatorDao.getElevatorByDenomination(denomination);
  }

  public void updateElevator(Elevator elevator) {
    Elevator entity = getElevatorByDenomination(elevator.getDenomination());
    if (entity == null) {
      String message = String.format("Elevator: <%s> couldn't be found",
          elevator.getDenomination());
      throw new EntityNotFoundException(message);
    }

    // Alarm mechanism
    if (entity.getMaxWeight() < elevator.getCurrentWeight()) {
      String message = String.format(
          "Elevator: <%s> has turned its alarm ON, max weight surpassed. "
              + "Will not operate", entity.getDenomination());
      LOG.warn(message);
      throw new AlarmMechanismException(message);
    }

    elevatorDao.updateElevator(elevator);
    LOG.info("Elevator: <{}> status has been updated successfully", entity.getDenomination());
  }
}
