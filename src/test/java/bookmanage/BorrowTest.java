package bookmanage;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.junit.Test;

import com.epoint.bookmanage.dao.BorrowInfoDao;
import com.epoint.bookmanage.util.JDBCUtil;

public class BorrowTest {
	
	BorrowInfoDao borrowInfoDao = new BorrowInfoDao();

	@Test
	public void returnBook() {

	}
}
