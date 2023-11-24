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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.RestApi.UserManagement.Helper;
import com.SpringBoot.RestApi.UserManagement.RoleEnum;
import com.SpringBoot.RestApi.UserManagement.TokenValueNotProvidedException;
import com.SpringBoot.RestApi.UserManagement.UserDetails;
import com.SpringBoot.RestApi.UserManagement.UserDetailsRepository;
import com.SpringBoot.RestApi.UserManagement.UserTokens;
import com.SpringBoot.RestApi.UserManagement.UserTokensRepository;

@RestController
public class LibraryController {

	@Autowired
	private BooksRepository bookRepo;

	@Autowired
	public UserDetailsRepository userRepo;

	@Autowired
	public UserTokensRepository tokenRepo;

	@Autowired
	BooksToUserRepository booksToUserRepo;


	@GetMapping("/books/v1/{id}")

	public BooksData getBookById(@PathVariable int id) {
        
		Optional<BooksData> book = bookRepo.findByBookId(id);
		
		if(book.isPresent()) {
			
			return book.get();
			
		}
		
		throw new BookNotExistException(("Book with book id :" + id + "Not exist"),404);
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
//		return bookRepo.findEntitiesWithParams(filter.getBookName() , filter.getAuthor() , filter.getStatus() , filter.getCategory() , filter.getQuantity());

    	return bookRepo.findEntitiesWithParams(bookName , author , status , category, quantity);
    }


	@GetMapping("/books/v1/all")
    public List<BooksData> getListOfAllBooks() {

		return bookRepo.findAll();
	}


	@PostMapping("/books/v1")
	public ResponseEntity<Object> bookToCreate(@RequestBody BooksData book , @RequestHeader (name="tokenKey" , required = false)  String tokenValue) {

		if(tokenValue==null || tokenValue.isEmpty()) {
			
			throw new TokenValueNotProvidedException(("Token not provided for user verification  , please provide valid Token "),400);		}
		
		Optional<UserTokens> isTokenDetails = tokenRepo.findByToken(tokenValue);

		if(! isTokenDetails.isPresent()) {

			throw new InvalidTokenException(("entered token: "+ tokenValue + " is mismatched"),400);

		}

		UserTokens tokenDetails = isTokenDetails.get();

		long userId = tokenDetails.getUserId();

		UserDetails userById = userRepo.findById(userId).get();

		if (userById.getRole().equals(RoleEnum.ADMIN) || userById.getRole().equals(RoleEnum.LIBRARIAN)) {

			bookRepo.save(book);
			
			return ResponseEntity.ok().body(book);
		}
		
		else {
			throw new InvalidEnumRoleException(("User Role must be either ADMIN or LIBRARIAN"),400);
		}
		
	}


	@DeleteMapping("/books/v1/{bookId}")
	public void deleteBook(@PathVariable int bookId,@RequestHeader (name="tokenKey" , required = false)  String tokenValue) {
		
		if(tokenValue==null || tokenValue.isEmpty()) {
			throw new TokenValueNotProvidedException(("Token not provided for user verification  , please provide valid Token "),400);		}

		Optional<UserTokens> isTokenDetails = tokenRepo.findByToken(tokenValue);

		if(! isTokenDetails.isPresent()) {

			throw new InvalidTokenException(("entered token: "+ tokenValue + " is mismatched"),400);

		}

		UserTokens tokenDetails = isTokenDetails.get();

		long userId = tokenDetails.getUserId();

		UserDetails userById = userRepo.findById(userId).get();

		System.out.println(userById.getRole());
		System.out.println(RoleEnum.LIBRARIAN);
		
		
		if ((!(userById.getRole().equals(RoleEnum.ADMIN))) && (!(userById.getRole().equals(RoleEnum.LIBRARIAN)))) {

			throw new InvalidEnumRoleException(("User Role must be either ADMIN or LIBRARIAN"),400);

		}

		Optional<BooksData> isBook = bookRepo.findById(bookId);

		if(! isBook.isPresent()) {

			throw new BookNotExistException(("Book with  Book Id does not exist"),404);
		}
			//TODO1: instead of hard delete, do soft delete (have one flag in entity, is_deleted)

		BooksData book = isBook.get();

		book.setIsDeleted(true);

		bookRepo.save(book);
	}


	@PutMapping("/books/v1/{bookId}")
	public ResponseEntity<Object> updateBookDetails (@PathVariable int bookId,@RequestBody BooksDataInput bookData,@RequestHeader (name="tokenKey" , required = false)  String tokenValue){

		if(tokenValue==null || tokenValue.isEmpty()) {
			
			throw new TokenValueNotProvidedException(("Token not provided for user verification  , please provide valid Token "),400);		}


       Optional<UserTokens> isToken =tokenRepo.findByToken(tokenValue);

       if(!isToken.isPresent()) {
    	   throw new InvalidTokenException(("entered token: "+ tokenValue + " is mismatched"),400);
       }

	   UserTokens tokenDetails = isToken.get();

	   long userId = tokenDetails.getUserId();

	   UserDetails existingUser= userRepo.findById(userId).get();

	   if(! existingUser.role.equals(RoleEnum.ADMIN) && ! existingUser.role.equals(RoleEnum.LIBRARIAN)) {

		   throw new InvalidEnumRoleException(("User Role must be either ADMIN or LIBRARIAN"),400);
	   }

	   Optional<BooksData> isBookWithGivenId=bookRepo.findById(bookId);

	   if(! isBookWithGivenId.isPresent()) {

		   throw new BookNotExistException(("Book with id : " + bookId + " not found"),404);

	   }

	   BooksData existingBook=isBookWithGivenId.get();

	   if(existingBook.isDeleted()) {

		   throw new BookNotAvailableException(("Book with book id :"+bookId+" is deleted , not available to update"),400);
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

	public ResponseEntity<Object> updateBookPartially(@PathVariable int bookId , @RequestBody BooksDataInput bookData , @RequestHeader (name="tokenKey" , required = false)  String tokenValue){

		if(tokenValue==null || tokenValue.isEmpty()) {
			
			throw new TokenValueNotProvidedException(("Token not provided for user verification  , please provide valid Token "),400);		}

		Optional<UserTokens> isToken =tokenRepo.findByToken(tokenValue);

		if(! isToken.isPresent()) {

			throw new InvalidTokenException(("entered token: "+ tokenValue + " is mismatched"),400);
		}

	   UserTokens tokenDetails = isToken.get();

	   long userId = tokenDetails.getUserId();

	   UserDetails existingUser= userRepo.findById(userId).get();

	   if(! existingUser.role.equals(RoleEnum.ADMIN) && ! existingUser.role.equals(RoleEnum.LIBRARIAN)) {

		   throw new InvalidEnumRoleException(("User Role must be either ADMIN or LIBRARIAN"),400);
	   }

	   Optional<BooksData> isBookWithGivenId=bookRepo.findById(bookId);

	   if(! isBookWithGivenId.isPresent()) {
			throw new BookNotExistException(("Book with id : " + bookId + " not found"),404);
		}


       BooksData existingBook=isBookWithGivenId.get();

	   if(existingBook.isDeleted()) {

		   throw new BookNotAvailableException(("Book with book id :"+bookId+" is deleted , not available to update"),400);
	   }

		if(bookData.author != null) {
			existingBook.setAuthor(bookData.author);
		}

		if(bookData.category != null) {
			existingBook.setAuthor(bookData.category);
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

		return ResponseEntity.ok().body("BOOK with ID : " + bookId + " updated successfully");

	}

	@PostMapping("/books/v1/issue/{bookId}")

	public ResponseEntity<Object> issueBook(@PathVariable int bookId , @RequestHeader ("tokenKey") String tokenValue) {

		if(tokenValue==null || tokenValue.isEmpty()) {
			
			throw new TokenValueNotProvidedException(("Token not provided for user verification  , please provide valid Token "),400);		}
		Optional<UserTokens> isTokenDetails = tokenRepo.findByToken(tokenValue);

		if(! isTokenDetails.isPresent()) {
			throw new InvalidTokenException(("entered token: "+ tokenValue + " is mismatched"),400);
		}

		UserTokens tokenDetails = isTokenDetails.get();

		long userId = tokenDetails.getUserId();
		
		Optional<BooksToUser> isBookAlreadyIssued = booksToUserRepo.findByBookIdAndUserIdAndIsDeleted(bookId,userId,false);
		
		if(isBookAlreadyIssued.isPresent()) {
			
			throw new BookNotAvailableException(("Book with Book Id : " + bookId + "Is already issued to you "),400);
			
		}

		BooksToUser bookToUser = new BooksToUser();

	    Optional<BooksData> isBookExist = bookRepo.findById(bookId);

	    if(! isBookExist.isPresent()) {

	    	throw new BookNotExistException((" Book with BookId " + bookId + " Not Exist "),404 );
	    }

	    BooksData book=isBookExist.get();

		if(book.isDeleted()) {
			throw new BookNotAvailableException(("Book with book id : "+bookId+" is deleted , not available to update"),400);
		}

		int quantityAvailableBefore = book.getQuantity();

    	if (quantityAvailableBefore<=0) {

    		throw new BookNotAvailableException((" Book with BookId: " + bookId + " Not Available to Issue "),400);
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
	    return ResponseEntity.ok().body(" Book with Book Id: " + bookId + " is issued to User with User Id: " +userId );
	}

	@PostMapping("/books/v1/return/{bookId}")
	public void returnBook(@PathVariable int bookId,@RequestHeader ("tokenKey")  String tokenValue , @RequestParam (required = false) Integer book_Id , @RequestParam (required = false) Long user_Id ) {

		if(tokenValue==null || tokenValue.isEmpty()  ) {
			
			throw new TokenValueNotProvidedException(("Token not provided for user verification  , please provide valid Token "),400);
		}
		
        Optional<UserTokens> isTokenDetails = tokenRepo.findByToken(tokenValue);

		if(! isTokenDetails.isPresent()) {

			throw new InvalidTokenException(("entered token: "+ tokenValue + " is mismatched") ,400);
		}

		UserTokens tokenDetails = isTokenDetails.get();

		long userId = tokenDetails.getUserId();

		Optional<BooksToUser> isBook = booksToUserRepo.findByBookIdAndUserIdAndIsDeleted(bookId,userId,false);

		if (! isBook.isPresent()  ) {
			throw new BookIsNotIssuedToUserException((" Book with BookId " + bookId + " is not issued to user with userId: "+ userId),400 );

		}

		BooksToUser bookToUser = isBook.get();

        bookToUser.setActualReturnTime(Helper.currentDateTime());
        
        bookToUser.setIsDeleted(true);
        
        booksToUserRepo.save(bookToUser);

    	BooksData book = bookRepo.findById(bookId).get();

    	int quantityAvailableBefore = book.getQuantity();

    	int quantityAvailableAfter = quantityAvailableBefore+1;

    	book.setQuantity(quantityAvailableAfter);

    	book.setStatus("AVAILABLE");

    	bookRepo.save(book);
	}
}
