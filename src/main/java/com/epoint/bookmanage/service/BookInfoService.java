package com.epoint.bookmanage.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.epoint.bookmanage.dao.BookInfoDao;
import com.epoint.bookmanage.entity.BookInfo;

/**
 *  
 * @author 锁梦
 *
 */

public class BookInfoService {
	
	BookInfoDao bookInfoDao = new BookInfoDao();
	
//	public List<BookInfo> getBookList(int pageIndex, int pageSize, String sortField, String sortOrder){
//		return bookInfoDao.getBookList(pageIndex, pageSize, sortField, sortOrder);
//	}
	
	public int getBookNums() {
		return bookInfoDao.getBookNums();
	}
	
	public int getBookInfoNums(String focustype, String context){
		return bookInfoDao.getBookInfoNums(focustype, context);
	}
	
	public int addBookInfo(BookInfo bookinfo) {
		bookinfo.setBookid(UUID.randomUUID().toString());
		return bookInfoDao.addBookInfo(bookinfo);
	}
	
	public boolean findBookWithAuthor(BookInfo bookinfo) {
		return bookInfoDao.findBookWithAuthor(bookinfo) > 0 ? true : false;
	}
	
	public BookInfo findBookById(String bookid) {
		return bookInfoDao.findBookById(bookid);
	}
	
	public int updateBookInfo(BookInfo bookInfo) {
		return bookInfoDao.updateBookInfo(bookInfo);
	}

	public int deleteBookInfo(String bookid) {
		return bookInfoDao.deleteBookInfo(bookid);
	}
	
	public List<BookInfo> getBookList(int pageIndex, int pageSize, String focustype, String context, String sortField, String sortOrder){
		return bookInfoDao.getBookList(pageIndex, pageSize, focustype, context, sortField, sortOrder);
	}
	
	public String getBookNameById(String bookid) {
		return bookInfoDao.getBookNameById(bookid);
	}
	
	public List<Map<String, String>> getBooksAvailable(){
		return bookInfoDao.getBooksAvailable();
	}
	
}
