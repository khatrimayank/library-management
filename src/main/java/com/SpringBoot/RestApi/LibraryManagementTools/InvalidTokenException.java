package com.SpringBoot.RestApi.LibraryManagementTools;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}