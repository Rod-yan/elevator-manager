package com.ry.demo.elevatormgr.dao;

import com.ry.demo.elevatormgr.model.Elevator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ElevatorMockPersistenceDaoImpl implements ElevatorDao {

  private final BuildingMockPersistenceDaoImpl buildingDao;

  @Autowired
  ElevatorMockPersistenceDaoImpl(BuildingMockPersistenceDaoImpl buildingDao) {
    this.buildingDao = buildingDao;
  }


  @Override
  public Elevator getElevatorByDenomination(String denomination) {
    for (Elevator e : buildingDao.getDefaultBuilding().getElevators()) {
      if (e.getDenomination().equalsIgnoreCase(denomination)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public void updateElevator(Elevator elevator) {
    Set<Elevator> elevators = buildingDao.getDefaultBuilding().getElevators();
    for (Elevator e : elevators) {
      if (e.getDenomination().equalsIgnoreCase(elevator.getDenomination())) {
        elevators.remove(e);
        e.setCurrentFloor(elevator.getCurrentFloor());
        e.setCurrentWeight(elevator.getCurrentWeight());
        elevators.add(e);
        break;
      }
    }
  }
}
