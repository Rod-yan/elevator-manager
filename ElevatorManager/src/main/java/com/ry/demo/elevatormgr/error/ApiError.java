package com.ry.demo.elevatormgr.error;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	
	private String errorMessage;
	private String detail;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private ApiError() {
		timestamp = LocalDateTime.now();
	}
	
	public ApiError(String message, String detail) {
        this();
        this.errorMessage = message;
        this.detail = detail;
    }

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ApiError [errorMessage=" + errorMessage + ", detail=" + detail + ", timestamp=" + timestamp + "]";
	}
}