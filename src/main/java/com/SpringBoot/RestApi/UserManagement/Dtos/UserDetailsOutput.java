package com.SpringBoot.RestApi.UserManagement.Dtos;

import com.SpringBoot.RestApi.UserManagement.Entity.UserDetails;

public class UserDetailsOutput extends UserDetails {
	private int otp;
	
	public UserDetailsOutput(UserDetails userDetails) {
		super(userDetails.name, userDetails.mobileNumber, userDetails.role);
		this.userId = userDetails.userId;
		this.createdAt = userDetails.createdAt;
		this.updatedAt = userDetails.updatedAt;
		this.isDeleted = userDetails.isDeleted;
	}
	
	public int getOtp() {
		return this.otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	
}
