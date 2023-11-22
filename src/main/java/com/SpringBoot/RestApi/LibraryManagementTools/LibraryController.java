package com.SpringBoot.RestApi.LibraryManagementTools;

import java.util.List;
import java.util.NoSuchElementException;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.RestApi.UserManagement.Entity.UserDetails;
import com.SpringBoot.RestApi.UserManagement.Entity.UserTokens;
import com.SpringBoot.RestApi.UserManagement.Enums.RoleEnum;
import com.SpringBoot.RestApi.UserManagement.Exceptions.UserNotExistException;
import com.SpringBoot.RestApi.UserManagement.Repository.UserDetailsRepository;
import com.SpringBoot.RestApi.UserManagement.Repository.UserTokensRepository;
import com.SpringBoot.RestApi.UserManagement.Utils.Helper;

@RestController
public class LibraryController {
	
	UserDetails createdUser;
	
	@Autowired
	private BooksRepository bookRepo;
	
	@Autowired 
	public UserDetailsRepository userRepo;
	
	@Autowired 
	public UserTokensRepository tokenRepo;
	
	@Autowired 
	BooksToUserRepository booksToUserRepo;
	
	
	@GetMapping("/books/v1/{id}")
	
	public Optional<BooksData> getBookById(@PathVariable int id) {
		
		return bookRepo.findById(id);
	}
	
    @GetMapping("/books/v1")
	
	public List<BooksData> findBook(@RequestParam(required = false) String bookName,
			                        @RequestParam(required = false) String author,
			                        @RequestParam(required = false) String status,
			                        @RequestParam(required = false) String category,
			                        @RequestParam(required = false) Integer quantity){
		
//    	DataFilter filter = new DataFilter();
//    	
//    	filter.setBookName(bookName);
//    	filter.setAuthor(author);
//    	filter.setStatus(status);
//    	filter.setCategory(category);
//    	filter.setQuantity(quantity);    	
//    	
//		return bookRepo.findBookBy(filter.getBookName() , filter.getAuthor() , filter.getStatus() , filter.getCategory() , filter.getQuantity());
	    
    	return bookRepo.findBookBy(bookName , author , status , category, quantity);
    }
	

	@GetMapping("/books/v1/all")
    public List<BooksData> getListOfAllBooks() {
		
		return bookRepo.findAll();	
	}
	
	
	@PostMapping("/books/v1")
	public BooksData bookToCreate(@RequestBody BooksData book , @RequestHeader ("tokenKey")  String tokenValue) {
		
		Optional<UserTokens> isTokenDetails = tokenRepo.findByUserToken(tokenValue);
		
		if(! isTokenDetails.isPresent()) {
			
			throw new InvalidTokenException("entered token: "+ tokenValue + " is mismatched");
		
		}
		
		UserTokens tokenDetails = isTokenDetails.get();
		
		long userId = tokenDetails.getUserId();
		
		UserDetails userById = userRepo.findById(userId).get();
		
		if (! userById.getRole().equals(RoleEnum.ADMIN) || ! userById.getRole().equals(RoleEnum.LIBRARIAN)) {
			
			throw new InvalidEnumRoleException("User Role must be either ADMIN or LIBRARIAN");
			
		}
		
		return bookRepo.save(book);
	}
	
	
	@DeleteMapping("/books/v1/{bookId}")
	public void deleteBook(@PathVariable int bookId,@RequestHeader ("tokenKey")  String tokenValue) {
		
		Optional<UserTokens> isTokenDetails = tokenRepo.findByUserToken(tokenValue);
		
		if(! isTokenDetails.isPresent()) {
		
			throw new InvalidTokenException("entered token: "+ tokenValue + " is mismatched");
		
		}
			
		UserTokens tokenDetails = isTokenDetails.get();
		
		long userId = tokenDetails.getUserId();
		
		UserDetails userById = userRepo.findById(userId).get();
		
		if (! userById.getRole().equals(RoleEnum.ADMIN) || ! userById.getRole().equals(RoleEnum.LIBRARIAN)) {
			
			throw new InvalidEnumRoleException("User Role must be either ADMIN or LIBRARIAN");
		
		}
			
		Optional<BooksData> isBook = bookRepo.findById(bookId);	
		
		if(! isBook.isPresent()) {
			
			throw new BookNotExistException("Book with  Book Id does not exist");
		}
			//TODO1: instead of hard delete, do soft delete (have one flag in entity, is_deleted)
		
		BooksData book = isBook.get(); 
		
		book.setIsDeleted(true);
		
		bookRepo.save(book);
	}
		

	@PutMapping("/books/v1/{bookId}")
	public ResponseEntity<Object> updateBookDetails (@PathVariable int bookId,@RequestBody BooksDataInput bookData,@RequestHeader ("tokenKey")  String tokenValue){
				
       Optional<UserTokens> isToken =tokenRepo.findByUserToken(tokenValue);
		
       if(!isToken.isPresent()) {
    	   throw new InvalidTokenException("entered token: "+ tokenValue + " is mismatched");
       }
			
	   UserTokens tokenDetails = isToken.get();
	   
	   long userId = tokenDetails.getUserId();
	   
	   UserDetails existingUser= userRepo.findById(userId).get();
	   
	   if(! existingUser.role.equals(RoleEnum.ADMIN) || ! existingUser.role.equals(RoleEnum.LIBRARIAN)) {
		   
		   throw new InvalidEnumRoleException("User Role must be either ADMIN or LIBRARIAN");
	   }
		   
	   Optional<BooksData> isBookWithGivenId=bookRepo.findById(bookId);
			
	   if(! isBookWithGivenId.isPresent()) {
		
		   throw new BookNotExistException("Book with id : " + bookId + " not found");
	   
	   }
		   
	   BooksData existingBook=isBookWithGivenId.get();
	   
	   if(existingBook.isDeleted()==true) {
		   
		   throw new BookNotAvailableException("Book with book id :"+bookId+" is deleted , not available to update");
	   }
			
	   existingBook.setBookName(bookData.bookName);
	   existingBook.setAuthor(bookData.author);
	   existingBook.setStatus(bookData.status);
	   existingBook.setCategory(bookData.category);
	   existingBook.setQuantity(bookData.quantity);
			
	   bookRepo.save(existingBook);
			
	   return ResponseEntity.ok().body("BOOK with ID" + bookId + "updated successfully");
	
   }
		
   	
	@PatchMapping("/books/v1/{bookId}")
	
	public ResponseEntity<Object> updateBookPartially(@PathVariable int bookId , @RequestBody BooksDataInput bookData , @RequestHeader ("tokenKey")  String tokenValue){
		
		Optional<UserTokens> isToken =tokenRepo.findByUserToken(tokenValue);
		
		if(! isToken.isPresent()) {
			
			throw new InvalidTokenException("entered token: "+ tokenValue + " is mismatched");
		}
			
	   UserTokens tokenDetails = isToken.get();
	   
	   long userId = tokenDetails.getUserId();
	   
	   UserDetails existingUser= userRepo.findById(userId).get();
	   
	   if(! existingUser.role.equals(RoleEnum.ADMIN) || ! existingUser.role.equals(RoleEnum.LIBRARIAN)) {
		   
		   throw new InvalidEnumRoleException("User Role must be either ADMIN or LIBRARIAN");
	   }
	   
	   Optional<BooksData> isBookWithGivenId=bookRepo.findById(bookId);
	   
	   if(! isBookWithGivenId.isPresent()) {
			throw new BookNotExistException("Book with id : " + bookId + " not found");
		}
		
			
       BooksData existingBook=isBookWithGivenId.get();
	   
	   if(existingBook.isDeleted()==true) {
		   
		   throw new BookNotAvailableException("Book with book id :"+bookId+" is deleted , not available to update");
	   }
		
		if(bookData.author != null) {
			existingBook.setAuthor(bookData.author);
		}
		
		if(bookData.status != null) {
			existingBook.setStatus(bookData.status);
		}
		
		if(bookData.bookName !=null) {
			existingBook.setBookName(bookData.bookName);
		}
		
		if(bookData.author !=null) {
			existingBook.setAuthor(bookData.author);
		}
		
		if(bookData.status!=null) {
			
			existingBook.setStatus(bookData.status);
		}
		
		bookRepo.save(existingBook);
		
		return ResponseEntity.ok().body("BOOK with ID" + bookId + "updated successfully");
		
	}

	@PostMapping("/books/v1/issue/{bookId}")
	
	public ResponseEntity<Object> issueBook(@PathVariable int bookId , @RequestHeader ("tokenKey") String tokenValue) {
		
		Optional<UserTokens> isTokenDetails = tokenRepo.findByUserToken(tokenValue);
		
		if(! isTokenDetails.isPresent()) {
			throw new InvalidTokenException("entered token: "+ tokenValue + " is mismatched");
		}
			
		UserTokens tokenDetails = isTokenDetails.get();
		
		long userId = tokenDetails.getUserId();
		
		BooksToUser bookToUser = new BooksToUser(); 
	    
	    Optional<BooksData> isBookExist = bookRepo.findById(bookId);
	    
	    if(! isBookExist.isPresent()) {
	    	
	    	throw new BookNotExistException(" Book with BookId " + bookId + " Not Exist " );
	    }
	    
	    BooksData book=isBookExist.get();
		   
		if(book.isDeleted()==true) {
			throw new BookNotAvailableException("Book with book id :"+bookId+" is deleted , not available to update");
		}
		
		int quantityAvailableBefore = book.getQuantity();
    	
    	if (quantityAvailableBefore<=0) {
    		
    		throw new BookNotAvailableException(" Book with BookId " + bookId + " Not Available to Issue ");
    	}
	    
    	bookToUser.setUserId(userId);
	    bookToUser.setBookId(bookId);
	    bookToUser.setIssueTime(Helper.currentDateTime());
	    bookToUser.setReturnTime(Helper.returnTime(30));
	    bookToUser.setActualReturnTime(null);
	    
	    booksToUserRepo.save(bookToUser);
    	
    	
    		
    	book.setQuantity(quantityAvailableBefore-1);
  
 
    	int quantityAvailableAfter = book.getQuantity();
    	
    	if(quantityAvailableAfter>0) {
    		book.setStatus("AVAILABLE");
    	}
        
    	bookRepo.save(book);
	    return ResponseEntity.ok().body(" Book with Book Id: " + bookId + "is issued to User with User Id: " + userId );
	} 

	@PostMapping("/books/v1/return/{bookId}")
	public void returnBook(@PathVariable int bookId,@RequestHeader ("tokenKey")  String tokenValue , @RequestParam (required = false) Integer book_Id , @RequestParam (required = false) Long user_Id ) {
        
        Optional<UserTokens> isTokenDetails = tokenRepo.findByUserToken(tokenValue);
		
		if(! isTokenDetails.isPresent()) {
			
			throw new InvalidTokenException("entered token: "+ tokenValue + " is mismatched");
		}
		
		UserTokens tokenDetails = isTokenDetails.get();
		
		long userId = tokenDetails.getUserId();
		
		Optional<BooksToUser> isBook = booksToUserRepo.findBy(bookId,userId); 
	    
		if (! isBook.isPresent()) {
			throw new BookIsNotIssuedToUserException(" Book with BookId " + bookId + " is not issued to user with userId: "+ userId );	
			
		}	
			
		BooksToUser bookToUser = isBook.get();
		
        bookToUser.setActualReturnTime(Helper.currentDateTime());
        
        booksToUserRepo.save(bookToUser);
    	
    	BooksData book = bookRepo.findById(bookId).get();
    	
    	int quantityAvailableBefore = book.getQuantity();
    	
    	int quantityAvailableAfter = quantityAvailableBefore+1;
    	
    	book.setQuantity(quantityAvailableAfter);
    	
    	book.setStatus("AVAILABLE");
    	
    	bookRepo.save(book);
	}
}
