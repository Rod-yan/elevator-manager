package com.ry.demo.elevatormgr.dao;

import com.ry.demo.elevatormgr.model.Elevator;

public interface ElevatorDao {

  Elevator getElevatorByDenomination(String denomination);

  void updateElevator(Elevator elevator);

}
