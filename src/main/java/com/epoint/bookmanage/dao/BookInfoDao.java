package com.epoint.bookmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.epoint.bookmanage.entity.BookInfo;
import com.epoint.bookmanage.util.JDBCUtil;

public class BookInfoDao {
	
	public String getBookNameById(String bookid) {

		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		String result = "";
		String sql = "select bookname from bookinfo where bookid = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, bookid);
			rSet = pstmt.executeQuery();
			if(rSet.first()) {
				result = rSet.getString("bookname");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
		
	}
	
	/**
	 * getBookNums Method
	 * @return
	 */
	public int getBookNums() {

		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = 0;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(*) as booknums from bookinfo ");
		try {
			pstmt = connection.prepareStatement(stringBuilder.toString());
			rSet = pstmt.executeQuery();
			while(rSet.next()) {
				result = rSet.getInt("booknums");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}
	
	/**
	 *  
	 * @param bookInfo
	 * @return
	 */
	
	public int addBookInfo(BookInfo bookInfo) {

		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = -1;
		String sql = "insert into bookinfo (bookid, bookname, publisher, author, booktype, remain) values(?,?,?,?,?,?) ";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  bookInfo.getBookid());
			pstmt.setString(2,  bookInfo.getBookname());
			pstmt.setString(3,  bookInfo.getPublisher());
			pstmt.setString(4,  bookInfo.getAuthor());
			pstmt.setInt(5,  bookInfo.getBooktype());
			pstmt.setInt(6, bookInfo.getRemain());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}
	
	public int findBookWithAuthor(BookInfo bookInfo) {

		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = -1;
		String sql = "select count(1) from bookinfo where author = ? and bookname = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  bookInfo.getAuthor());
			pstmt.setString(2,  bookInfo.getBookname());
			rSet = pstmt.executeQuery();
			if(rSet.first()) {
				result = rSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;

	}
	
	public BookInfo findBookById(String bookid) {
		
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		BookInfo result = null;
		String sql = "select * from bookinfo where bookid = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, bookid);
			rSet = pstmt.executeQuery();
			while(rSet.next()) {
				BookInfo bookInfo = new BookInfo();
				bookInfo.setBookid(rSet.getString("bookid"));
				bookInfo.setBookname(rSet.getString("bookname"));
				bookInfo.setAuthor(rSet.getString("author"));
				bookInfo.setPublisher(rSet.getString("publisher"));
				bookInfo.setBooktype(rSet.getInt("booktype"));
				bookInfo.setRemain(rSet.getInt("remain"));
				result = bookInfo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}
	
	public int updateBookInfo(BookInfo bookInfo) {
		
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = -1;
		String sql = "update bookinfo set publisher = ?, booktype = ? where bookid = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  bookInfo.getPublisher());
			pstmt.setInt(2,  bookInfo.getBooktype());
			pstmt.setString(3, bookInfo.getBookid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	public int deleteBookInfo(String bookid) {
		
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int bookres = -1;
		int borrowres = -1;
		String booksql = "delete from bookinfo where bookid = ?";
		String borrowsql = "delete from borrowinfo where bookid = ?";
		try {
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(booksql);
			pstmt.setString(1, bookid);
			bookres = pstmt.executeUpdate();
			
			pstmt = connection.prepareStatement(borrowsql);
			pstmt.setString(1, bookid);
			borrowres = pstmt.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		if(bookres >= 0 && borrowres >= 0) {
			return 1;
		}else {
			return 0;
		}
	}

	public int getBookInfoNums(String focustype, String context){
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = -1;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(1) from bookinfo ");
		if(!StringUtils.isEmpty(context)) {
			if(!StringUtils.isEmpty(focustype)) {
				stringBuilder.append("where bookname like \'%" + context + "%\' and booktype = " + focustype);
			}else {
				stringBuilder.append("where bookname like \'%" + context + "%\' ");
			}
		}else {
			if(!StringUtils.isEmpty(focustype)) {
				stringBuilder.append("where booktype = " + focustype);
			}
		}
		try {
			pstmt = connection.prepareStatement(stringBuilder.toString());
			rSet = pstmt.executeQuery();
			if(rSet.first()) {
				result = rSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}

	public List<BookInfo> getBookList(int pageIndex, int pageSize, String focustype, String context, String sortField, String sortOrder){
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from bookinfo ");
		if(!StringUtils.isEmpty(context)) {
			if(!StringUtils.isEmpty(focustype)) {
				stringBuilder.append("where bookname like \'%" + context + "%\' and booktype = " + focustype);
			}else {
				stringBuilder.append("where bookname like \'%" + context + "%\' ");
			}
		}else {
			if(!StringUtils.isEmpty(focustype)) {
				stringBuilder.append("where booktype = " + focustype);
			}
		}
		if(!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortOrder)) {
			stringBuilder.append(" order by " + sortField + " " + sortOrder);
		}
		if(-1 != pageSize && -1 != pageIndex) {
			stringBuilder.append(" limit " + pageIndex * pageSize + "," + pageSize);
		}
		List<BookInfo> result = new ArrayList<BookInfo>();
		try {
			pstmt = connection.prepareStatement(stringBuilder.toString());
			rSet = pstmt.executeQuery();
			while(rSet.next()) {
				BookInfo bookInfo = new BookInfo();
				bookInfo.setBookid(rSet.getString("bookid"));
				bookInfo.setBookname(rSet.getString("bookname"));
				bookInfo.setAuthor(rSet.getString("author"));
				bookInfo.setPublisher(rSet.getString("publisher"));
				bookInfo.setBooktype(rSet.getInt("booktype"));
				bookInfo.setRemain(rSet.getInt("remain"));
				result.add(bookInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}

	
	/**
	 * Get all available books
	 * @return
	 */
	public List<Map<String, String>> getBooksAvailable(){
		
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		String sql = "select bookid, bookname from bookinfo where remain > 0";
		List<Map<String, String>> result = new ArrayList<>();
		try {
			pstmt = connection.prepareStatement(sql);
			rSet = pstmt.executeQuery();
			while(rSet.next()) {
				Map<String, String> r = new HashMap<String, String>();
				r.put("id", rSet.getString("bookid"));
				r.put("text", rSet.getString("bookname"));
				result.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}
	
}
