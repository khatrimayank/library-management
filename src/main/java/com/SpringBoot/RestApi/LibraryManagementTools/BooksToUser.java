package com.SpringBoot.RestApi.LibraryManagementTools;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="books_to_user")
public class BooksToUser {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="serial_number")
	public int serialNumber;
	
	public long userId;
	
    public int bookId;
	
	public LocalDateTime issueTime;
	
	public LocalDateTime returnTime;

	public LocalDateTime actualReturnTime;
	
	
	@Column(name="is_returned", columnDefinition = "BOOLEAN DEFAULT false")
	public boolean isReturned;
	
	
	public boolean isReturned() {
		return isReturned;
	}

	public void setIsReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public LocalDateTime getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(LocalDateTime issueTime) {
		this.issueTime = issueTime;
	}

	public LocalDateTime getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(LocalDateTime returnTime) {
		this.returnTime = returnTime;
	}

	public LocalDateTime getActualReturnTime() {
		return actualReturnTime;
	}

	public void setActualReturnTime(LocalDateTime actualReturnTime) {
		this.actualReturnTime = actualReturnTime;
	}

	
}
