package com.SpringBoot.RestApi.UserManagement;

public class ExpireOtpException extends RuntimeException {
	private final int errorCode;
	
    public ExpireOtpException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}

