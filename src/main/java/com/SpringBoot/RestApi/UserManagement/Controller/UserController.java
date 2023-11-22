package com.SpringBoot.RestApi.UserManagement.Controller;


import com.SpringBoot.RestApi.UserManagement.Dtos.UserDetailsInput;
import com.SpringBoot.RestApi.UserManagement.Dtos.UserDetailsOutput;
import com.SpringBoot.RestApi.UserManagement.Dtos.VerifyOtpInput;
import com.SpringBoot.RestApi.UserManagement.Entity.UserDetails;
import com.SpringBoot.RestApi.UserManagement.Entity.UserOtpRequests;
import com.SpringBoot.RestApi.UserManagement.Entity.UserTokens;
import com.SpringBoot.RestApi.UserManagement.Enums.TimeOperationEnum;
import com.SpringBoot.RestApi.UserManagement.Exceptions.*;
import com.SpringBoot.RestApi.UserManagement.Repository.UserDetailsRepository;
import com.SpringBoot.RestApi.UserManagement.Repository.UserOtpRequestsRepository;
import com.SpringBoot.RestApi.UserManagement.Repository.UserTokensRepository;
import com.SpringBoot.RestApi.UserManagement.Utils.Helper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	public int otp;
	
	@Autowired
	public  UserDetailsRepository userRepo;
	
	@Autowired
	public UserOtpRequestsRepository otpRepo;
	
	@Autowired
	public UserTokensRepository tokenRepo;
	
	@GetMapping("/user-details")
	
	public List<UserDetails> get_all_user_details() {
		
		return userRepo.findAll();
	}

	@PostMapping("/signup")
	public ResponseEntity<Object> enterUserDetails(@RequestBody UserDetailsInput user){
		
		UserDetails userToCreate = new UserDetails(user.name, user.mobileNumber, user.role);
	
		LocalDateTime timenow =Helper.currentDateTime();
		LocalDateTime validTill=Helper.timeChange(TimeOperationEnum.ADD, 10);
		
		userToCreate.setCreatedAt(timenow);
		userToCreate.setUpdatedAt(timenow);
		userToCreate.setIsDeleted(false);
		
		UserDetails createdUser = userRepo.save(userToCreate);
		
		otp = Helper.randomOtpGenerator(6); 
		
		UserOtpRequests otpId=new UserOtpRequests();
		
		otpId.setUserId(createdUser.getUserId());
		
		otpId.setOtp(otp);
		
		otpId.setCreatedAt(timenow);
		
		otpId.setValidTill(validTill);
		
		otpRepo.save(otpId);
		
		UserDetailsOutput outputDto = new UserDetailsOutput(createdUser);
		outputDto.setOtp(otp);
		
	    return (ResponseEntity.ok().body(outputDto));
	}
	
	@PostMapping ("/verify")
	public ResponseEntity<Object> verifyCredentials (@RequestBody VerifyOtpInput details ){
		
		String mobileNo=details.getMobileNo();
		
		int otp=details.getOtp();
		
		Optional<UserDetails> userWithMobileNo=Optional.ofNullable(userRepo.findByMobileNumber(mobileNo));
		
		if(userWithMobileNo.isPresent()) {
			
			UserDetails userWithDetails=userWithMobileNo.get();
			
			if (userWithDetails.isDeleted==false) {
				
				long userId=userWithDetails.getUserId();
				
				boolean isDeleted=userWithDetails.getIsDeleted();
			
			    UserOtpRequests otpDetailsWithId=otpRepo.findByUserId(userId);
			
			    LocalDateTime currentTime=Helper.currentDateTime();
			
			    LocalDateTime validTill=otpDetailsWithId.getValidTill();
			
		      	int comparisonResult=validTill.compareTo(currentTime);
			
			    if(otp==otpDetailsWithId.getOtp() && comparisonResult>=0) {
				
			    	String token=Helper.randomTokenGenerator();
				
				    LocalDateTime timeOfTokenCreated=Helper.currentDateTime();
				
			      	UserTokens tokenDetails = new UserTokens();
				
			    	tokenDetails.setUserId(userId);
				
				    tokenDetails.setToken(token);
				
				    tokenDetails.setCreatedAt(timeOfTokenCreated);
				
				    tokenDetails.setUpdatedAt(timeOfTokenCreated);
				
				    tokenDetails.setIsDeleted(isDeleted);
				
				    tokenRepo.save(tokenDetails);
				
				    return ResponseEntity.ok().body(tokenDetails);
				} 
			    
			    else if(otp==otpDetailsWithId.getOtp() && comparisonResult<0) {
			    	
			    	throw new ExpireOtpException ("The provided OTP :" + otp + " has expired.");
			    }
			    else {
			    	throw new InvalidOtpException("mobile number :" +mobileNo + " and otp :" + otp + "mismatched");
			    }
			    
			}   
			
			else {
				throw new UserIsDeletedException("For enterd mobile number :" + mobileNo + " user is deleted , so first create user with given mobile number");
			}
		}
		else {
			
			throw new UserNotExistException("user with entered mobile number :" + mobileNo + " is not exist , please firstly create user with provided mobile number");
		}

	}
}