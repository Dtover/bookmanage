package com.epoint.bookmanage.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 
 * @author 锁梦
 */

public class JDBCUtil {
	private static DataSource dataSource;
	
	public DataSource getDataSource() {
		if(null == dataSource) {
			try {
				Properties properties = new Properties();
				properties.load(JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
				dataSource = DruidDataSourceFactory.createDataSource(properties);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataSource;
	}
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			dataSource = new JDBCUtil().getDataSource();
			connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection, PreparedStatement pstmt, ResultSet rSet) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(rSet != null) {
			try {
				rSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		String sql = "select count(*) as booksums from bookinfo ";
		try {
			pstmt = connection.prepareStatement(sql);
			rSet = pstmt.executeQuery();
			while(rSet.next()) {
				System.out.println(rSet.getString("booksums"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection, pstmt, rSet);
		}
	}
}
