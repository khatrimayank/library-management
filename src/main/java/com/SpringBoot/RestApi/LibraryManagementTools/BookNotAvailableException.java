package com.SpringBoot.RestApi.LibraryManagementTools;

public class BookNotAvailableException extends RuntimeException {
	
	private final int errorCode;
	
    public BookNotAvailableException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}