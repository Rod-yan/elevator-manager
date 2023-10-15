package com.ry.demo.elevatormgr.mapper;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ry.demo.elevatormgr.dto.BasicElevatorDto;
import com.ry.demo.elevatormgr.model.Elevator;

@Component
public class BasicElevatorMapperImpl implements Mapper<Elevator, BasicElevatorDto> {

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public BasicElevatorDto convertToDto(Elevator elevator) {
    return modelMapper.map(elevator, BasicElevatorDto.class);
  }

  @Override
  public Elevator convertToEntity(@Valid BasicElevatorDto updateElevatorDto) {
    return modelMapper.map(updateElevatorDto, Elevator.class);
  }
}
