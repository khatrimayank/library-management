package com.SpringBoot.RestApi.LibraryManagementTools;

public class InvalidTokenException extends RuntimeException {
	
	private final int errorCode;
	
    public InvalidTokenException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}