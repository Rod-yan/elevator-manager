package com.ry.demo.elevatormgr.dao;

import com.ry.demo.elevatormgr.model.Building;
import org.springframework.stereotype.Repository;

@Repository
public class BuildingMockPersistenceDaoImpl implements BuildingDao {

  @Override
  public Building getDefaultBuilding() {
    return Building.getInstance();
  }
}
