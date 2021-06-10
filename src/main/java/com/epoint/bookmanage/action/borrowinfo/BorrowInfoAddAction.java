package com.epoint.bookmanage.action.borrowinfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanage.entity.BorrowInfo;
import com.epoint.bookmanage.service.BorrowInfoService;

public class BorrowInfoAddAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BorrowInfoAddAction() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookinfostr = request.getParameter("data");
		BorrowInfo borrowInfo = JSONObject.parseObject(bookinfostr, BorrowInfo.class);
		BorrowInfoService borrowInfoService = new BorrowInfoService();
		int count = borrowInfoService.saveBorrowInfo(borrowInfo);
		Map<String, String> resultMap = new HashMap<String, String>();
		if(count > 0) {
			resultMap.put("state", "1");
			resultMap.put("msg", "借阅成功");
			response.getWriter().print(JSONObject.toJSONString(resultMap));
		}else {
			resultMap.put("state", "2");
			resultMap.put("msg", "借阅失败");
			response.getWriter().print(JSONObject.toJSONString(resultMap));
		}
	}
}
