package com.SpringBoot.RestApi.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
	
	UserDetails findByMobileNumber(String mobileNumber);

}
