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
	public long userId;
	
	public int otp;
	
	public LocalDateTime created_at;
	
	public LocalDateTime valid_till;
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long user_id) {
		this.userId = user_id;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public LocalDateTime getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.created_at = createdAt;
	}

	public LocalDateTime getValidTill() {
		return valid_till;
	}

	public void setValidTill(LocalDateTime validTill) {
		this.valid_till = validTill;
	}

	public UserOtpRequests() {}
	

}
