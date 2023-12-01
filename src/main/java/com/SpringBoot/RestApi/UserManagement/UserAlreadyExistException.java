package com.SpringBoot.RestApi.UserManagement;

public class UserAlreadyExistException extends RuntimeException {
	
	private final int errorCode;
	
	public UserAlreadyExistException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
	}
	
	 public int getErrorCode() {
	        return errorCode;
	 }

}
