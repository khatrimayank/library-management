package com.SpringBoot.RestApi.UserManagement;


public class TokenValueNotProvidedException extends RuntimeException {
	
	private final int errorCode;
	
    public TokenValueNotProvidedException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
