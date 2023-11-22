package com.SpringBoot.RestApi.LibraryManagementTools;

public class InvalidEnumRoleException extends RuntimeException {
    public InvalidEnumRoleException(String message) {
        super(message);
    }
}