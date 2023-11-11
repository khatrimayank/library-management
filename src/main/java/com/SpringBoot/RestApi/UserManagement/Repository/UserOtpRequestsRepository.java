package com.SpringBoot.RestApi.UserManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.RestApi.UserManagement.Entity.UserOtpRequests;

public interface UserOtpRequestsRepository extends JpaRepository<UserOtpRequests,Long>{
	
	UserOtpRequests findByUserId(long userId);

	
}
