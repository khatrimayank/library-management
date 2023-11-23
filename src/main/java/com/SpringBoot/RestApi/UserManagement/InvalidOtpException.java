package com.SpringBoot.RestApi.UserManagement;

public class InvalidOtpException extends RuntimeException {
	
	private final int errorCode;
	
    public InvalidOtpException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
