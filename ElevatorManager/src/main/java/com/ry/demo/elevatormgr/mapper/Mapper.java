package com.ry.demo.elevatormgr.mapper;

public interface Mapper<entityClazz, dtoClazz> {
	
	dtoClazz convertToDto(entityClazz entity);
	
	entityClazz convertToEntity(dtoClazz dto);
	
}