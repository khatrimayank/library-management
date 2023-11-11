package com.SpringBoot.RestApi.UserManagement.Exceptions;

public class UserNotExistException extends RuntimeException {

	public UserNotExistException(String message) {
        super(message);
        
	}
	
}
