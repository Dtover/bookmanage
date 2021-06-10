package com.epoint.bookmanage.service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import com.epoint.bookmanage.dao.BorrowInfoDao;
import com.epoint.bookmanage.entity.BorrowInfo;

public class BorrowInfoService {
	
	BorrowInfoDao borrowInfoDao = new BorrowInfoDao();

	public int getBorrowNums() {
		return borrowInfoDao.getBorrowNums();
	}
	
	public List<BorrowInfo> getBorrowInfoList(int pageIndex, int pageSize, String sortField, String sortOrder){
		return borrowInfoDao.getBookList(pageIndex, pageSize, sortField, sortOrder);
	}
	
	public String getBorrowId() {
		String prefix = "Borrow" + Calendar.getInstance().get(Calendar.YEAR);
		String maxId = borrowInfoDao.getMaxBorrowId();
		if(maxId == null) {
			maxId = prefix + "0000";
		}
		int newId = Integer.valueOf(maxId.substring(10, 14));
		newId++;
		DecimalFormat dFormat = new DecimalFormat("0000");
		return prefix+dFormat.format(newId);
	}

	public int saveBorrowInfo(BorrowInfo borrowInfo) {
		return borrowInfoDao.saveBorrowInfo(borrowInfo);
	}
	
	public int deleteBorrowInfo(String borrowid) {
		return borrowInfoDao.deleteBorrowInfo(borrowid);
	}
	
	
	public int returnBook(String borrowid, String bookid) {
		return borrowInfoDao.returnBook(borrowid, bookid);
	}
	
	
	public int getBookState(String bookid) {
		return borrowInfoDao.getBookState(bookid);
	}
	
}
