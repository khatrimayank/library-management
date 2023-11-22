package com.SpringBoot.RestApi.LibraryManagementTools;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.SpringBoot.RestApi.UserManagement.Entity.UserDetails;
import com.SpringBoot.RestApi.UserManagement.Repository.UserDetailsRepository;



public class Helper {

	@Autowired
	public UserDetailsRepository userRepo;
	
	@Autowired
	public BooksRepository bookRepo;
	
	public Boolean doesUserExist(long userId) {
		
		Optional<UserDetails> userById = userRepo.findById(userId);
		
		if(userById.isPresent()) {
			
			return true;
		}
		
		return false;
		
	}
	
	
	
}
