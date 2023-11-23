package com.SpringBoot.RestApi.LibraryManagementTools;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksToUserRepository extends JpaRepository<BooksToUser,Integer> {
	
	
	
	Optional<BooksToUser> findByBookIdAndUserIdAndIsDeleted(int bookId , long userId ,boolean isDeleted);

}
