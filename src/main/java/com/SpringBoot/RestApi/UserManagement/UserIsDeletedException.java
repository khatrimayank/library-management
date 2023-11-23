package com.SpringBoot.RestApi.UserManagement;

public class UserIsDeletedException extends RuntimeException {
	
	private final int errorCode;
	
	public UserIsDeletedException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
	}
	
	 public int getErrorCode() {
	        return errorCode;
	 }

}
