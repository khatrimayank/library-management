package com.SpringBoot.RestApi.LibraryManagementTools;

public class BookNotExistException extends RuntimeException {
    public BookNotExistException(String message) {
        super(message);
    }
}