package com.SpringBoot.RestApi.LibraryManagementTools;

public class BookNotExistException extends RuntimeException {
    
	private final int errorCode;
	
	public BookNotExistException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}