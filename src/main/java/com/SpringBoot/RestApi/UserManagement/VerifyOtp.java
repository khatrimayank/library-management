package com.SpringBoot.RestApi.UserManagement;

public class VerifyOtp {

	public String mobileNumber;
	
	public String getMobileNo() {
		return mobileNumber;
	}

	public void setMobileNo(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int otp;
	
	
}
