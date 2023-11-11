package com.SpringBoot.RestApi.UserManagement.Exceptions;

public class ExpireOtpException extends RuntimeException {
    public ExpireOtpException(String message) {
        super(message);
    }
}

