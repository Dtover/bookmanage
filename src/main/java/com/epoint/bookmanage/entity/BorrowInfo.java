package com.epoint.bookmanage.entity;

import java.sql.Timestamp;

public class BorrowInfo {

	private String borrowid;
	private String bookid;
	private String borrower;
	private String phone;
	private Timestamp borrowtime;
	private Timestamp returntime;
	
	private String bookname;
	
	private Boolean isreturn;
	
	public Boolean getIsreturn() {
		return isreturn;
	}
	public void setIsreturn(Boolean isreturn) {
		this.isreturn = isreturn;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getBorrowid() {
		return borrowid;
	}
	public void setBorrowid(String borrowid) {
		this.borrowid = borrowid;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Timestamp getBorrowtime() {
		return borrowtime;
	}
	public void setBorrowtime(Timestamp borrowtime) {
		this.borrowtime = borrowtime;
	}
	public Timestamp getReturntime() {
		return returntime;
	}
	public void setReturntime(Timestamp returntime) {
		this.returntime = returntime;
	}
	@Override
	public String toString() {
		return "BorrowInfo [borrowid=" + borrowid + ", bookid=" + bookid + ", borrower=" + borrower + ", phone=" + phone
				+ ", borrowtime=" + borrowtime + ", returntime=" + returntime + ", bookname=" + bookname + ", isreturn="
				+ isreturn + "]";
	}
	
}
