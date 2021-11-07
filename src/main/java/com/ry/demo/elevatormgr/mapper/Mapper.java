package com.ry.demo.elevatormgr.mapper;

public interface Mapper<T, U> {
	
	U convertToDto(T entity);
	
	T convertToEntity(U dto);
	
}
