package com.SpringBoot.RestApi.UserManagement;

public class UserNotExistException extends RuntimeException {
	private final int errorCode;
	
	public UserNotExistException(String message , int errorCode) {
        super(message);
        this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
        return errorCode;
    }
}
