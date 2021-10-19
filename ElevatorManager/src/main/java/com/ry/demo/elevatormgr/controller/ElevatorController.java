package com.ry.demo.elevatormgr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ry.demo.elevatormgr.dto.BasicElevatorDto;
import com.ry.demo.elevatormgr.error.AlarmMechanismException;
import com.ry.demo.elevatormgr.error.ApiError;
import com.ry.demo.elevatormgr.error.EntityNotFoundException;
import com.ry.demo.elevatormgr.mapper.BasicElevatorMapperImpl;
import com.ry.demo.elevatormgr.model.Elevator;
import com.ry.demo.elevatormgr.model.Floor;
import com.ry.demo.elevatormgr.service.ElevatorService;

@RestController
public class ElevatorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ElevatorController.class);
	
	@Autowired
    ElevatorService elevatorService;
	@Autowired
    BasicElevatorMapperImpl elevatorUpdateMapper;
	
	@GetMapping(value = { "/elevators/{denomination}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getElevator(@PathVariable("denomination") String denomination) {
		logger.info("GetElevator - HTTP Get - Request received for resource /elevators/" + denomination);
		Elevator elevator = null;
		try {
			elevator = elevatorService.getElevatorByDenomination(denomination.toLowerCase());
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<ApiError>(new ApiError("Unhandled exception", "Please contact your systems' administrator"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (elevator != null) {
			logger.info("GetElevator - HTTP Get - Request for resource /elevators/" + denomination + " is successful");
			return new ResponseEntity<BasicElevatorDto>(elevatorUpdateMapper.convertToDto(elevator), HttpStatus.OK);
		} else {
			return new ResponseEntity<ApiError>(new ApiError("Not found", "No elevator was found for the specified denomination"),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping(value = { "/elevators/{denomination}" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateElevator(@PathVariable("denomination") String denomination, 
    		@Valid @RequestBody BasicElevatorDto updateElevatorDto, @RequestHeader HttpHeaders headers) {
		
		logger.info("UpdateElevator - HTTP Patch - Request received for resource /elevators/" + denomination
				+ ", current weight: " + updateElevatorDto.getCurrentWeight()
				+ ", selected floor: " + updateElevatorDto.getCurrentFloor());
		
		Elevator elevator = elevatorUpdateMapper.convertToEntity(updateElevatorDto);
		elevator.setDenomination(denomination.toLowerCase());
		
		try {
			if (!isKeycardAuthenticationSuccessful(elevator, headers)) {
				return new ResponseEntity<ApiError>(new ApiError("Access denied", 
						"You need a specific Keycard to access this Floor"), 
						HttpStatus.FORBIDDEN);
			}
			
			elevatorService.updateElevator(elevator);
		} catch (AlarmMechanismException ex) {
			return new ResponseEntity<ApiError>(new ApiError("Won't operate", ex.getLocalizedMessage()), 
					HttpStatus.FORBIDDEN);
		} catch (EntityNotFoundException ex) {
			return new ResponseEntity<ApiError>(new ApiError("Not found", ex.getLocalizedMessage()),
					HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<ApiError>(new ApiError("Unhandled exception", "Please contact your systems' administrator"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("UpdateElevator - HTTP Patch - Request for resource /elevators/" + denomination + " is successful");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }
	
	private boolean isKeycardAuthenticationSuccessful(Elevator elevator, HttpHeaders headers) {
		// Keycard authorization constraint
		Elevator entity = elevatorService.getElevatorByDenomination(elevator.getDenomination());
		if (entity != null) {
			for (Map.Entry<Floor, Boolean> entry : entity.getAvailableFloors().entrySet()) {
				if (entry.getKey().getNumber() == elevator.getCurrentFloor() && entry.getValue() == true) {
					if (!isBasicAuthenticationSuccessful(headers.getFirst(HttpHeaders.AUTHORIZATION))){
						logger.warn("[HTTP 403] Forbidden error: Provided Keycard is not authorized to access requested floor");
						return false;
					}
				}
			}
		} else {
			throw new EntityNotFoundException();
		}
		
		return true;
	}
	
	private boolean isBasicAuthenticationSuccessful(String authHeader) {
		return "Basic YWRtaW46YWRtaW4xMjM=".equalsIgnoreCase(authHeader);
	}
}
