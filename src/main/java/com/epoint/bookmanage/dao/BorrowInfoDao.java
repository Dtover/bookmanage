package com.epoint.bookmanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.epoint.bookmanage.entity.BorrowInfo;
import com.epoint.bookmanage.util.JDBCUtil;

public class BorrowInfoDao {
	
	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
	public List<BorrowInfo> getBookList(int pageIndex, int pageSize, String sortField, String sortOrder){
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from borrowinfo left join bookinfo using(bookid)");
		if(!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortOrder)) {
			stringBuilder.append(" order by " + sortField + " " + sortOrder);
		}
		if(-1 != pageSize && -1 != pageIndex) {
			stringBuilder.append(" limit " + pageIndex * pageSize + "," + pageSize);
		}
		List<BorrowInfo> result = new ArrayList<BorrowInfo>();
		try {
			pstmt = connection.prepareStatement(stringBuilder.toString());
			rSet = pstmt.executeQuery();
			while(rSet.next()) {
				BorrowInfo borrowInfo = new BorrowInfo();
				borrowInfo.setBorrowid(rSet.getString("borrowid"));
				borrowInfo.setBookid(rSet.getString("bookid"));
				borrowInfo.setBookname(rSet.getString("bookname"));
				borrowInfo.setBorrower(rSet.getString("borrower"));
				borrowInfo.setPhone(rSet.getString("phone"));
				borrowInfo.setBorrowtime(rSet.getTimestamp("borrowtime"));
				borrowInfo.setReturntime(rSet.getTimestamp("returntime"));
				borrowInfo.setIsreturn(rSet.getBoolean("isreturn"));
				result.add(borrowInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}
	

	/**
	 * Get the number of borrow info
	 * @return
	 */
	public int getBorrowNums() {

		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = 0;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(1) from borrowinfo ");
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
	
	public String getMaxBorrowId() {
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		String result = "";
		String sql = "select max(borrowid) as maxid from borrowinfo";
		try {
			pstmt = connection.prepareStatement(sql);
			rSet = pstmt.executeQuery();
			if(rSet.first()) {
				result = rSet.getString("maxid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
		
	}

	@SuppressWarnings("resource")
	public int saveBorrowInfo(BorrowInfo borrowInfo) {
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = -1;
		int bookres = -1;
		String sql = "insert into borrowinfo (borrowid, bookid, borrower, phone, borrowtime, returntime) values (?,?,?,?,?,?)";
		String bookSql = "update bookinfo set remain = remain - 1 where bookid = ?";
		try {

			connection.setAutoCommit(false);

			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, borrowInfo.getBorrowid());
			pstmt.setString(2, borrowInfo.getBookid());
			pstmt.setString(3, borrowInfo.getBorrower());
			pstmt.setString(4, borrowInfo.getPhone());
			pstmt.setTimestamp(5, new Timestamp(borrowInfo.getBorrowtime().getTime()));
			if(null != borrowInfo.getReturntime()) {
				pstmt.setTimestamp(6, new Timestamp(borrowInfo.getReturntime().getTime()));
			}else {
				pstmt.setTimestamp(6, null);
			}
			result = pstmt.executeUpdate();
			
			pstmt = connection.prepareStatement(bookSql);
			pstmt.setString(1, borrowInfo.getBookid());
			bookres = pstmt.executeUpdate();
			
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		if(result > 0 && bookres > 0) {
			return 1;
		}else {
			return 0;
		}
		
	}
	
	public int deleteBorrowInfo(String borrowid) {

		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = -1;
		String sql = "delete from borrowinfo where borrowid = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, borrowid);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	public int returnBook(String borrowid, String bookid) {

		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int bookres = -1;
		int borrowres = -1;
		String booksql = "update bookinfo set remain = remain + 1 where bookid = ?";
		String borrowsql = "update borrowinfo set returntime = ?, isreturn = true where borrowid = ?";
		try {
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(booksql);
			pstmt.setString(1, bookid);
			bookres = pstmt.executeUpdate();
			
			pstmt = connection.prepareStatement(borrowsql);
			pstmt.setTimestamp(1, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			pstmt.setString(2, borrowid);
			borrowres = pstmt.executeUpdate();
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
		if (bookres > 0 && borrowres > 0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public int getBookState(String bookid) {
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		int result = 0;
		String sql = "select count(1) from borrowinfo where bookid = ? and isreturn = false";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, bookid);
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

}
