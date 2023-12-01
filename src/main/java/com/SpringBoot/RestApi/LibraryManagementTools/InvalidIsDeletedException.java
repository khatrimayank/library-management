package com.SpringBoot.RestApi.LibraryManagementTools;

public class InvalidIsDeletedException extends RuntimeException {

	private final int errorCode;
    
	public InvalidIsDeletedException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    
}
