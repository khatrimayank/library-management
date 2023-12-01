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
@Table(name="user_details")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	public long userId;
	
	@Column(columnDefinition="VARCHAR(25)")
	public String name;

	@Column(name="mobile_number" ,columnDefinition="VARCHAR(15)")
	public String mobileNumber;
	
	public RoleEnum role;
	
	@Column(name="is_deleted" , columnDefinition = "BOOLEAN CHECK (is_deleted IN (true,false))")
	public Boolean isDeleted;
	
	@Column(name="created_at")
	public LocalDateTime createdAt;
	
	
	@Column(name="updated_at")
	public LocalDateTime updatedAt;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public UserDetails() {
		
	}
		
	public UserDetails(String name, String mobileNumber, RoleEnum role) {
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.role = role;
	}
	
	public void updateDefaultFields(LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isDeletedAt) {
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDeleted = isDeletedAt;
	}
}