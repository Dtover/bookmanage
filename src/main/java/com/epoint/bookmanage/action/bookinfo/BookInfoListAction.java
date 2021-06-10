package com.epoint.bookmanage.action.bookinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanage.entity.BookInfo;
import com.epoint.bookmanage.service.BookInfoService;

public class BookInfoListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookInfoListAction() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookInfoService bookInfoService = new BookInfoService();
		String method = request.getParameter("method");
		if(method != null && "getavailable".equals(method)) {
			List<Map<String, String>> resultMap = new ArrayList<>();
			resultMap = bookInfoService.getBooksAvailable();
			response.getWriter().print(JSONObject.toJSONString(resultMap));
		}else {
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			String context = request.getParameter("context");
			String focustype = request.getParameter("focustype");
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			List<BookInfo> bookInfos = bookInfoService.getBookList(pageIndex, pageSize, focustype, context, sortField, sortOrder);
			int bookinfonum = bookInfoService.getBookInfoNums(focustype, context);
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("data", bookInfos);
			resultMap.put("total", bookinfonum);
			response.getWriter().print(JSONObject.toJSONString(resultMap));
		}

	}

}
