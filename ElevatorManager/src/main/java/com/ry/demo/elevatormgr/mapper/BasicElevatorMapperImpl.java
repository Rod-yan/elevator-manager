package com.ry.demo.elevatormgr.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ry.demo.elevatormgr.dto.BasicElevatorDto;
import com.ry.demo.elevatormgr.model.Elevator;

@Component
public class BasicElevatorMapperImpl implements Mapper<Elevator, BasicElevatorDto> {
	
	@Autowired
    ModelMapper modelMapper;
	
	@Override
	public BasicElevatorDto convertToDto(Elevator elevator) {
		return modelMapper.map(elevator, BasicElevatorDto.class);
	}
	
	@Override
	public Elevator convertToEntity(BasicElevatorDto updateElevatorDto) {
		return modelMapper.map(updateElevatorDto, Elevator.class);
	}
}
