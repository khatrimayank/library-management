package com.SpringBoot.RestApi.UserManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_otp_requests")
public class UserOtpRequests {
	

	@Id
	@Column(name="user_id")
	public long userId;
	
	
	public int otp;
	
	@Column(name="created_at")
	public LocalDateTime createdAt;
	
	
	@Column(name="valid_till")
	public LocalDateTime validTill;
    
	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public int getOtp() {
		return otp;
	}


	public void setOtp(int otp) {
		this.otp = otp;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getValidTill() {
		return validTill;
	}


	public void setValidTill(LocalDateTime validTill) {
		this.validTill = validTill;
	}
	

	public UserOtpRequests() {}
	

}
