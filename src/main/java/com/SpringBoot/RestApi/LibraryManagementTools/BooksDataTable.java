package com.SpringBoot.RestApi.LibraryManagementTools;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="books")
public class BooksDataTable {
	
	@Column
	private String book_Name;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int book_id;
	
	@Column
	private String author;
	
	@Column
	private String status;
	
	@Column
	private String category;
	
	BooksDataTable(){
	}
	
	public BooksDataTable(String book_Name,String author,String status,String category) {
	
		this.book_Name=book_Name;
		this.author=author;
		this.status=status;
		this.category=category;
	}
	
	public int getId() {
		return book_id;
	}
	
	public String getBookName() {
		return book_Name;
	}
	

	public void setBook_Name(String book_Name) {
		this.book_Name = book_Name;
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
	

}
