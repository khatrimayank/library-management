package com.SpringBoot.RestApi.LibraryManagementTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.RestApi.UserManagement.UserDetailsRepository;

@Service
public class LibraryService {

	private final UserDetailsRepository userRepo;
	
	@Autowired
	public LibraryService(UserDetailsRepository userRepo) {
		this.userRepo = userRepo;
	}
}
