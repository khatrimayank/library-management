package com.SpringBoot.RestApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.SpringBoot.RestApi.UserManagement.UserNotExistException;
import com.SpringBoot.RestApi.UserManagement.UserIsDeletedException;
import com.SpringBoot.RestApi.UserManagement.InvalidOtpException;
import com.SpringBoot.RestApi.UserManagement.ExpireOtpException;


import com.SpringBoot.RestApi.LibraryManagementTools.BookNotExistException;
import com.SpringBoot.RestApi.LibraryManagementTools.InvalidEnumRoleException;
import com.SpringBoot.RestApi.LibraryManagementTools.BookIsNotIssuedToUserException;
import com.SpringBoot.RestApi.LibraryManagementTools.BookNotAvailableException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<String> handleUserNotExistException(UserNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage() + "/n error code: " + ex.getErrorCode());
    }
    
    @ExceptionHandler(UserIsDeletedException.class)
    public ResponseEntity<String> handleUserIsDeletedException(UserIsDeletedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()+ "/n error code: " + ex.getErrorCode());
    }
    
    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<String> handleInvalidOtpException(InvalidOtpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()+ "/n error code: " + ex.getErrorCode());
    }

    // Add more @ExceptionHandler methods for other custom exceptions if needed
    
    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<String> handleBookNotAvailableException(BookNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + "/n error code : "+ex.getErrorCode());
    }
    
    @ExceptionHandler(BookIsNotIssuedToUserException.class)
    public ResponseEntity<String> handleBookIsNotIssuedToUserException(BookIsNotIssuedToUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()+ "/n error code: " + ex.getErrorCode());
    }

    @ExceptionHandler(BookNotExistException.class)
    public ResponseEntity<String> handleBookNotExistException(BookNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()+ "/n error code: " + ex.getErrorCode());
    }
    
    @ExceptionHandler(InvalidEnumRoleException.class)
    public ResponseEntity<String> handleInvalidEnumRoleException(InvalidEnumRoleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()+ "/n error code: " + ex.getErrorCode());
    }
    
    @ExceptionHandler(ExpireOtpException.class)
    public ResponseEntity<String> handleExpireOtpException(ExpireOtpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + "/n error code: " + ex.getErrorCode());
    }
    
}
