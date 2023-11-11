package com.SpringBoot.RestApi.UserManagement.Exceptions;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}
