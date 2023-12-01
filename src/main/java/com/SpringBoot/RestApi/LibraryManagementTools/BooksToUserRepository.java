package com.SpringBoot.RestApi.LibraryManagementTools;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BooksToUserRepository extends JpaRepository<BooksToUser,Integer> {
	
	Optional<BooksToUser> findByBookIdAndUserIdAndIsReturnedFalse(int bookId , long userId);
	
	@Query("SELECT b FROM BooksToUser b " +
		   "WHERE (b.actualReturnTime > b.returnTime) OR (b.actualReturnTime IS NULL AND CURRENT_TIMESTAMP > b.returnTime)"+
		   "AND (:userId IS NULL or b.userId= :userId)")
    
	List<BooksToUser> findBooksBasedOnConditions(
			@Param("userId") long userId);
	
}
