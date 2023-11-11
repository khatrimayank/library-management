package com.SpringBoot.RestApi.UserManagement.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_tokens")
public class  UserTokens{
	
	@Id
	private long userId;
	
	private String token;
	
	private Boolean is_deleted;
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long id) {
		this.userId=id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsDeleted() {
		return is_deleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.is_deleted = isDeleted;
	}

	public LocalDateTime getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdatedAt(LocalDateTime update_at) {
		this.updated_at = updated_at;
	}
    
	public UserTokens() {}
}
