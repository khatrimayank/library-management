package com.SpringBoot.RestApi.UserManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.RestApi.UserManagement.Entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
	
	UserDetails findByMobileNumber(String mobileNumber);

}
