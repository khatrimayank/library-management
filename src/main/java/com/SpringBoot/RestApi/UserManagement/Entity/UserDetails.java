package com.SpringBoot.RestApi.UserManagement.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.SpringBoot.RestApi.UserManagement.Enums.RoleEnum;

@Entity
@Table(name="user_details")
public class UserDetails {

	@Id
	public long userId;
	
	public String name;

	public String mobileNumber;
	
	public RoleEnum role;
	
	public Boolean is_deleted;
	
	public LocalDateTime created_at;
	
	public LocalDateTime updated_at;
	
	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNumber;
	}

	public void setMobileNo(String mobile_number) {
		this.mobileNumber = mobile_number;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public Boolean getIsDeleted() {
		return is_deleted;
	}

	public void setIsDeleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public LocalDateTime getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.created_at = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updated_at = updatedAt;
	}
	
	public UserDetails() {}
	
	public UserDetails(String name, String mobile_no, RoleEnum role) {
		this.name = name;
		this.mobileNumber = mobile_no;
		this.role = role;
	}
}