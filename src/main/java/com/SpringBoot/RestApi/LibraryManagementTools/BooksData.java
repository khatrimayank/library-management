package com.SpringBoot.RestApi.LibraryManagementTools;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="books_details")
public class BooksData {
	
	@Column(name="book_name")
	public String bookName;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="book_id")
	public int bookId;
	
	@Column
	public String author;
	
	@Column
	public String status;
	
	@Column
	public String category;
	
	@Column
	public int quantity;
	
	
	@Column(name="is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
	public boolean isDeleted;
	

	BooksData(){
	}
	
	public BooksData(String bookName,String author,String status,String category,int quantity) {
	
		this.bookName=bookName;
		this.author=author;
		this.status=status;
		this.category=category;
		this.quantity=quantity;
	}
	

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public int getId() {
		return bookId;
	}
	
	public String getBookName() {
		return bookName;
	}
	

	public void setBookName(String book_name) {
		this.bookName = book_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public String getAuthor() {
		return author;
	}
	
	public String getCategory() {
		return category;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int no) {
		this.quantity=no;
	}

}
