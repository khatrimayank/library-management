package com.SpringBoot.RestApi.UserManagement.Exceptions;

public class UserIsDeletedException extends RuntimeException {
	
	public UserIsDeletedException(String message) {
        super(message);
    }

}
