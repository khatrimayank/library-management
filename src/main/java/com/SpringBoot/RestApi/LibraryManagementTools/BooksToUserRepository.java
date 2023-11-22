package com.SpringBoot.RestApi.LibraryManagementTools;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksToUserRepository extends JpaRepository<BooksToUser,Long> {
	
	Optional<BooksToUser> findBy(int bookId , long userId);

}
