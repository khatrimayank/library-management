package com.SpringBoot.RestApi.LibraryManagementTools;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BooksRepository extends JpaRepository<BooksData,Integer> {

	@Query("SELECT b FROM BooksData b " +
	           "WHERE (:bookName IS NULL OR b.bookName = :bookName) " +
	           "AND (:author IS NULL OR b.author = :author) " +
	           "AND (:status IS NULL OR b.status = :status) " +
	           "AND (:category IS NULL OR b.category = :category) " +
	           "AND (:quantity IS NULL OR b.quantity = :quantity)")

	List<BooksData> findEntitiesWithParams(
			@Param("bookName") String bookName,
	        @Param("author") String author,
	        @Param("status") EnumStatus status,
	        @Param("category") String category,
            @Param("quantity") Integer quantity);
	
	Optional<BooksData> findByBookIdAndIsDeletedFalse(int bookId);

}