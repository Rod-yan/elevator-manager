package com.ry.demo.elevatormgr.dao;

import org.springframework.stereotype.Repository;

import com.ry.demo.elevatormgr.model.Building;

@Repository
public class BuildingMockPersistenceDaoImpl implements BuildingDao {
	
	@Override
	public Building getDefaultBuilding() {
		return Building.getInstance();
	}
}
