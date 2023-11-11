package com.SpringBoot.RestApi.LibraryManagementTools;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {
	
	@Autowired
	private LibraryReposit repo;
	
	@GetMapping("/books-v1/{id}")
	
	public Optional<BooksDataTable> get_book_by_id(@PathVariable int id) {
		
		return repo.findById(id);
	}
	
	@GetMapping("/books-v1")
    public List<BooksDataTable> get_book_by_id1() {
		
		return repo.findAll();	
	}
	
	@PostMapping("/books-v1")
	public BooksDataTable book_details(@RequestBody BooksDataTable book) {
		return repo.save(book);
	}
	
	@DeleteMapping("/books-v1/{id}")
	public void delete_book(@PathVariable int id) {
		repo.deleteById(id);
	}
	
	@PutMapping("/books-v1/{id}")
	public Optional<BooksDataTable> update_book_by_id (@PathVariable int id ){
		
		Optional<BooksDataTable> book = repo.findById(id);
		
		if (book.isPresent()){
			
			BooksDataTable existing_book=book.get();
			
			existing_book.setBook_Name("Science");
			existing_book.setAuthor("A");
			existing_book.setStatus("Available");
			existing_book.setCategory("Research");

      		return Optional.of(repo.save(existing_book));
		}
		
		else {
			
			return Optional.empty();
		}
	}  
	
	@PutMapping("/books-v2/{id}")
	public ResponseEntity<Object> getResponseEntity (@PathVariable int id){
		
		Optional<BooksDataTable> book = repo.findById(id);
		
		if (book.isPresent()) {
			
			BooksDataTable existingBook=book.get();
			
			existingBook.setBook_Name("Maths");
			existingBook.setAuthor("B");
			existingBook.setStatus("ISSUED");
			existingBook.setCategory("MATHEMATICS");
			
			repo.save(existingBook);
			
			return ResponseEntity.ok().body("BOOK with ID" + id + "updated successfully");
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PatchMapping("/books-v1/{id}")
	public ResponseEntity<Object> update_book_partially(@PathVariable int id){
		
		Optional<BooksDataTable> bookWithGivenId=repo.findById(id);
		
		if(bookWithGivenId.isPresent()) {
			BooksDataTable existingBook=bookWithGivenId.get();
			
			existingBook.setBook_Name("Learn java with SpringFramework");
			existingBook.setAuthor("C");
			existingBook.setStatus("Issued");
			
			repo.save(existingBook);
			
			return ResponseEntity.ok().body("Book with given id " + id + "updated succesfully");
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
}
