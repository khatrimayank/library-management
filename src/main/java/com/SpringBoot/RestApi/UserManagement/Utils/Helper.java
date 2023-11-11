package com.SpringBoot.RestApi.UserManagement.Utils;


import java.time.LocalDateTime;
import java.util.Random;

import com.SpringBoot.RestApi.UserManagement.Enums.TimeOperationEnum;

import java.security.SecureRandom;

public class Helper {
	
	public static int randomOtpGenerator(int size) {
        // Ensure that the size is within a valid range
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be a positive integer");
        }

        // Generate a random number with the specified size
        Random random = new Random();
        int lowerBound = (int) Math.pow(10, size - 1);
        int upperBound = (int) Math.pow(10, size) - 1;

        return lowerBound + random.nextInt((int) (upperBound - lowerBound + 1));
    }
	
	public static String randomTokenGenerator() {
		
		int length=20;
	    
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
		  
		SecureRandom random = new SecureRandom();
		
		StringBuilder sb = new StringBuilder(length);
		
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(characters.length());
			sb.append(characters.charAt(randomIndex));
		}
	        
		return sb.toString();

	    
	}

	
	public static LocalDateTime currentDateTime() {
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		return currentTime;
	}
  
	public static LocalDateTime timeChange(TimeOperationEnum operation , int timeChange) {
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		if(timeChange>0) {
			if (TimeOperationEnum.ADD.equals(operation)) {
				LocalDateTime validTill=currentTime.plusMinutes(timeChange);
				return validTill;
		    }
			else if(TimeOperationEnum.MINUS.equals(operation)) {
				LocalDateTime validTill=currentTime.minusMinutes(timeChange);
				return validTill;
			}
			else {
				throw new IllegalArgumentException("Invalid operation. Please enter 'add' or 'subtract'.");
			}
		}
		else {
			throw new IllegalArgumentException("Invalid operation. Please enter 'time change value greater than 0'.");
		}
	}
}



