package com.SpringBoot.RestApi.UserManagement;


import com.SpringBoot.RestApi.LibraryManagementTools.InvalidEnumRoleException;
import com.SpringBoot.RestApi.LibraryManagementTools.InvalidTokenException;
import com.SpringBoot.RestApi.UserManagement.UserIsDeletedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")

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

		Optional<UserDetails> isUserAlreadyExist =Optional.ofNullable(userRepo.findByMobileNumberAndIsDeletedFalse(user.mobileNumber));

		if(isUserAlreadyExist.isPresent()) {
			
			throw new UserAlreadyExistException(("User with Mobile Number already Exist , You Can Generate New Otp"),400);
				
		}

		if (!((user.role).equals(RoleEnum.ADMIN)) && !((user.role).equals(RoleEnum.LIBRARIAN)) && !( (user.role).equals(RoleEnum.USER))) {

			throw new InvalidEnumRoleException(("User role must be either ADMIN or LIBRARIAN or USER "),400);
		}

		UserDetails userToCreate = new UserDetails(user.name, user.mobileNumber, user.role);

		LocalDateTime timenow =Helper.currentDateTime();
		LocalDateTime validTill=Helper.timeChange(TimeOperationEnum.ADD, 10);

		userToCreate.setCreatedAt(timenow);
		userToCreate.setUpdatedAt(timenow);
		userToCreate.setIsDeleted(false);

		userRepo.save(userToCreate);

		otp = Helper.randomOtpGenerator(6);

		UserOtpRequests otDetails=new UserOtpRequests();

		otDetails.setUserId(userToCreate.getUserId());

		otDetails.setOtp(otp);

		otDetails.setCreatedAt(timenow);

		otDetails.setValidTill(validTill);

		otpRepo.save(otDetails);

		UserDetailsOutput outputDto = new UserDetailsOutput(userToCreate);

		outputDto.setOtp(otp);

	    return (ResponseEntity.ok().body(outputDto));
	}

	@PostMapping("/new-otp")
	public ResponseEntity<Object> generateNewOtp (@RequestBody  NewOtpOutput mobDetails ){

		Optional<UserDetails> isUserExist = Optional.ofNullable(userRepo.findByMobileNumberAndIsDeletedFalse(mobDetails.mobileNumber));

		if(! isUserExist.isPresent()) {
			throw new UserNotExistException(("User with MobileNumber not exist"),404);
		}

		UserDetails userDetails = isUserExist.get();

		otp = Helper.randomOtpGenerator(6);

		LocalDateTime timenow =Helper.currentDateTime();
		LocalDateTime validTill=Helper.timeChange(TimeOperationEnum.ADD, 10);

		UserOtpRequests otpDetails=new UserOtpRequests();

		otpDetails.setUserId(userDetails.getUserId());

		otpDetails.setOtp(otp);

		otpDetails.setCreatedAt(timenow);

		otpDetails.setValidTill(validTill);

		otpRepo.save(otpDetails);

		UserDetails userToCreate = new UserDetails(userDetails.name, userDetails.mobileNumber, userDetails.role);

		userToCreate.setUserId(userDetails.getUserId());
		userToCreate.setCreatedAt(timenow);
		userToCreate.setUpdatedAt(timenow);
		userToCreate.setIsDeleted(false);

		UserDetailsOutput outputDto = new UserDetailsOutput(userToCreate);

		outputDto.setOtp(otp);

	    return (ResponseEntity.ok().body(outputDto));
	}

	@PostMapping ("/verify")
	public ResponseEntity<Object> verifyCredentials (@RequestBody VerifyOtpInput details ){

		String mobileNo=details.getMobileNo();

		int otp=details.getOtp();

		Optional<UserDetails> userWithMobileNo=Optional.ofNullable(userRepo.findByMobileNumberAndIsDeletedFalse(mobileNo));

		if(!userWithMobileNo.isPresent()) {
			throw new UserNotExistException(("user with entered mobile number :" + mobileNo + " is not exist , please firstly create user with provided mobile number"),404);
		}

		UserDetails userWithDetails=userWithMobileNo.get();


		long userId=userWithDetails.getUserId();

		boolean isDeleted=userWithDetails.getIsDeleted();

	    UserOtpRequests otpDetailsWithId=otpRepo.findByUserId(userId);

	    LocalDateTime currentTime=Helper.currentDateTime();

	    LocalDateTime validTill=otpDetailsWithId.getValidTill();

      	int comparisonResult=validTill.compareTo(currentTime);

	    if(otp!=otpDetailsWithId.getOtp()) {
	    	throw new InvalidOtpException(("mobile number :" +mobileNo + " and otp :" + otp + " mismatched"),400);
	    }

	    if(comparisonResult<0) {
        	throw new ExpireOtpException (("The provided OTP :" + otp + " has expired."),400);
        }

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

	@DeleteMapping("/delete-user")

	public ResponseEntity<Object> deleteUser (@RequestHeader(name="tokenKey" , required=false) String tokenValue) {

		if(tokenValue == null || tokenValue.isEmpty()) {

			throw new TokenValueNotProvidedException(("Token not provided for user verification  , please provide valid Token "),400);
		}


	    Optional<UserTokens> isUserTokenExist = tokenRepo.findByTokenAndIsDeletedFalse(tokenValue);

	    if(! isUserTokenExist.isPresent()) {

	    	throw new InvalidTokenException(("entered token: "+ tokenValue + " is Invalid"),400);

	    }

	    UserTokens userTokenDetails = isUserTokenExist.get();

	    long userId =userTokenDetails.getUserId();
	    
	    Optional<UserDetails> isUserAlreadyExist =Optional.ofNullable(userRepo.findByUserIdAndIsDeletedFalse(userId));
	    
		if(! isUserAlreadyExist.isPresent()) {

			throw new UserNotExistException(("user with given id " + userId +" already deleted"),404);

		}
		
		UserDetails user = isUserAlreadyExist.get();
		
		userTokenDetails.setIsDeleted(true);
		
		tokenRepo.save(userTokenDetails);
		
		user.setIsDeleted(true);
		
		userRepo.save(user);

		return ResponseEntity.ok().body("user with userId : " +userId +" is deleted");
		
    }
		
}
