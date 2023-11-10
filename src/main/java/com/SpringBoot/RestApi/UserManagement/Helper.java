package com.SpringBoot.RestApi.UserManagement;

import java.time.LocalDateTime;
import java.util.Random;
import java.security.SecureRandom;

public class Helper {
	
	public static int randomOtpGenerator() {
		
		Random random = new Random();

	    // Generate a 6-digit random number
	    
		int min = 100000; // Smallest 6-digit number
	    
		int max = 999999; // Largest 6-digit number
	    
		int randomNumber = random.nextInt(max - min + 1) + min;

	    // Print the generated random number
	    
		return randomNumber;
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
  
	public static LocalDateTime futureTime() {
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		LocalDateTime validTill=currentTime.plusMinutes(10);
		
		return validTill;
		
	}
}
