package com.SpringBoot.RestApi.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOtpRequestsRepository extends JpaRepository<UserOtpRequests,Long>{
	
	UserOtpRequests findByUserId(long userId);

	
}
