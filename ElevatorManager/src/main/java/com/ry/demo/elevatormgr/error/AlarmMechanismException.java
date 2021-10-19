package com.ry.demo.elevatormgr.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class AlarmMechanismException extends RuntimeException {
	public AlarmMechanismException() {
		super();
	}
}