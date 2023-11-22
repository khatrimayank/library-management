package com.SpringBoot.RestApi.LibraryManagementTools;

public class BookIsNotIssuedToUserException extends RuntimeException {
	
	public BookIsNotIssuedToUserException(String message) {
		super(message);
	}
}
