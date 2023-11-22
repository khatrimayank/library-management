package com.SpringBoot.RestApi.LibraryManagementTools;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}