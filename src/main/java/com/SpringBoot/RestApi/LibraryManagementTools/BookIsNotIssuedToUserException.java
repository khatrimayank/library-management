package com.SpringBoot.RestApi.LibraryManagementTools;

public class BookIsNotIssuedToUserException extends RuntimeException {
	
	private final int errorCode;

	public BookIsNotIssuedToUserException(String message,int errorCode) {
		super(message);
	    this.errorCode=errorCode;
	}
	
	public int getErrorCode() {
        return errorCode;
    }
}
