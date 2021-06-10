package com.epoint.bookmanage.action.bookinfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanage.service.BookInfoService;
import com.epoint.bookmanage.service.BorrowInfoService;

public class BookInfoDeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookInfoDeleteAction() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookInfoService bookInfoService = new BookInfoService();
		BorrowInfoService borrowInfoService = new BorrowInfoService();
		String[] bookids = request.getParameter("bookid").split(",");
		Map<String, String> resultMap = new HashMap<>();
		int stateres = -1;
		int delres = -1;
		if(bookids.length > 1) {
			for(int i = 0; i < bookids.length; i++) {
				stateres = borrowInfoService.getBookState(bookids[i]);
				if(stateres > 0) {
					resultMap.put("state", "2");
					resultMap.put("msg", "无法删除");
					response.getWriter().print(JSONObject.toJSONString(resultMap));
					return;
				}
			}
			for(int i = 0; i < bookids.length; i++) {
				delres = bookInfoService.deleteBookInfo(bookids[i]);
				if(delres == 0) {
					resultMap.put("state", "2");
					resultMap.put("msg", "删除失败");
					response.getWriter().print(JSONObject.toJSONString(resultMap));
					return;
				}
			}
			resultMap.put("state", "1");
			resultMap.put("msg", "删除成功");
			response.getWriter().print(JSONObject.toJSONString(resultMap));
		}else {
			String bookname = bookInfoService.getBookNameById(bookids[0]);
			stateres = borrowInfoService.getBookState(bookids[0]);
			if(stateres > 0) {
				resultMap.put("state", "2");
				resultMap.put("msg", "《" + bookname + "》 存在归还信息，无法删除");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
				return;
			}
			delres = bookInfoService.deleteBookInfo(bookids[0]);
			if(delres == 0) {
				resultMap.put("state", "2");
				resultMap.put("msg", "《" + bookname + "》 存在归还信息，无法删除");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}else {
				resultMap.put("state", "1");
				resultMap.put("msg", "《" + bookname + "》 删除成功");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}
		}
	}

}
