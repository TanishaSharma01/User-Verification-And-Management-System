package com.nagarro.dto;

import java.time.LocalDateTime;

//Custom Error Response DTO
public class ErrorResponse {

	String message;
	int code;
	LocalDateTime timeStamp;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public ErrorResponse(String message, int code, LocalDateTime timeStamp) {
		this.message = message;
		this.code = code;
		this.timeStamp = timeStamp;
	}
	public ErrorResponse() {
		
	}
	
	
}
