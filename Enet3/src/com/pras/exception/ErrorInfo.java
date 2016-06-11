package com.pras.exception;

public class ErrorInfo {
	
	
	private int status;
	private String message;
	
	public ErrorInfo(){
		
	}
	
	public ErrorInfo(int status, String message) {
		this.status = status;
		this.message= message;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
