package com.epoint.bookmanage.action.bookinfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanage.entity.BookInfo;
import com.epoint.bookmanage.service.BookInfoService;

/**
 * Servlet implementation class BookInfoAddAction
 */
public class BookInfoAddAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookInfoAddAction() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookInfoService bookInfoService = new BookInfoService();
		String bookinfostr = request.getParameter("data");
		BookInfo bookInfo = JSONObject.parseObject(bookinfostr, BookInfo.class);
		boolean isExist = bookInfoService.findBookWithAuthor(bookInfo);
		Map<String, String> resultMap = new HashMap<String, String>();
		if (!isExist) {
			int result = bookInfoService.addBookInfo(bookInfo);
			if(result != -1) {
				resultMap.put("state", "1");
				resultMap.put("msg", "保存成功");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}
		} else {
			resultMap.put("state", "2");
			resultMap.put("msg", "书籍已经存在");
			response.getWriter().print(JSONObject.toJSONString(resultMap));
		}
	}

}
