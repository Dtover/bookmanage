package com.epoint.bookmanage.action.bookinfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.epoint.bookmanage.entity.BookInfo;
import com.epoint.bookmanage.service.BookInfoService;

/**
 * Servlet implementation class BookInfoEditAction
 */
public class BookInfoEditAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookInfoEditAction() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookInfoService bookInfoService = new BookInfoService();
		String bookid = request.getParameter("bookid");
		BookInfo bookInfo = bookInfoService.findBookById(bookid);
		String result = JSON.toJSONString(bookInfo);
		response.getWriter().print(result);
	}

}
