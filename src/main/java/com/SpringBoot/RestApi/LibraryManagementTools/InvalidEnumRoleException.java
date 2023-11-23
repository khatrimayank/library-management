package com.SpringBoot.RestApi.LibraryManagementTools;

public class InvalidEnumRoleException extends RuntimeException {
	
	private final int errorCode;
    
	public InvalidEnumRoleException(String message , int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
}