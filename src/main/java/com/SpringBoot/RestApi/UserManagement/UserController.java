package com.SpringBoot.RestApi.UserManagement;

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

	//TODO: change this to fetch otp of single user, take user id in query or path param
	@GetMapping("/user-otp-details")
	public List<UserOtpRequests> get_all_otp_details() {
		
		return otpRepo.findAll();
	}

	//TODO: same as above like OTP details
	@GetMapping("/user-tokens")
	public List<UserTokens> get_all_token_details() {
		
		return tokenRepo.findAll();
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Object> enterUserDetails(@RequestBody UserDetailsInput user){
		
		UserDetails userToCreate = new UserDetails(user.name, user.mobileNumber, user.role);
	
		LocalDateTime timenow =Helper.currentDateTime();
		
		LocalDateTime validTill=Helper.futureTime();
		
		userToCreate.setCreatedAt(timenow);
		userToCreate.setUpdatedAt(timenow);
		userToCreate.setIsDeleted(false);
		
		UserDetails createdUser = userRepo.save(userToCreate);
		
		otp = Helper.randomOtpGenerator(); 
		
		UserOtpRequests otpId=new UserOtpRequests();
		
		otpId.setUserId(createdUser.getUserId());
		
		otpId.setOtp(otp);
		
		otpId.setCreatedAt(timenow);
		
		otpId.setValidTill(validTill);
		
		otpRepo.save(otpId);
		
	    return ResponseEntity.ok().body("for adding user with details otp is sent on mobile number and otp is:" +otp);
	    
	}
	
	@PostMapping ("/verify")
	public ResponseEntity<Object> verifyCredentials (@RequestBody VerifyOtp details ){
		
		String mobileNo=details.getMobileNo();
		
		int otp=details.getOtp();
		
		Optional<UserDetails> userWithMobileNo=Optional.ofNullable(userRepo.findByMobileNumber(mobileNo));
		
		if(userWithMobileNo.isPresent()) {
			
			UserDetails userWithDetails=userWithMobileNo.get();
			
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
				
				return ResponseEntity.ok().body("user credentials verified , token is generated and token is:" + token);
				
			}
			
		}
		
		return ResponseEntity.ok().body("couldn't verify credentials , please input correct mobile number with otp within 10 minutes after otp is generated.");
		
	}
	
	
}