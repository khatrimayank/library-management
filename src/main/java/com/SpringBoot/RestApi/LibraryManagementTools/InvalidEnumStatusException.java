package com.SpringBoot.RestApi.LibraryManagementTools;

public class InvalidEnumStatusException extends RuntimeException{

private final int errorCode;
    
	public InvalidEnumStatusException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
}
