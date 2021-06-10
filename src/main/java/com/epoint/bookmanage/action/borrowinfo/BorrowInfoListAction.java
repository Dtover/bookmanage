package com.epoint.bookmanage.action.borrowinfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.epoint.bookmanage.entity.BorrowInfo;
import com.epoint.bookmanage.service.BorrowInfoService;

/**
 * Servlet implementation class BorrowInfoListAction
 */
public class BorrowInfoListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowInfoListAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		BorrowInfoService borrowInfoService = new BorrowInfoService();
		if("list".equals(method)) {
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			List<BorrowInfo> borrowInfos = borrowInfoService.getBorrowInfoList(pageIndex, pageSize, sortField, sortOrder);

			Map<String, Object> resultMap = new HashMap<>();
			int count = borrowInfoService.getBorrowNums();
			resultMap.put("data", borrowInfos);
			resultMap.put("total", count);
			response.getWriter().println(JSONObject.toJSONStringWithDateFormat(resultMap, "yyyy-mm-dd HH:mm:ss"));
		}else if("getBorrowId".equals(method)) {
			response.getWriter().println(borrowInfoService.getBorrowId());
		}else if("deleteBorrowInfo".equals(method)){
			String borrowid = request.getParameter("borrowid");
			int result = borrowInfoService.deleteBorrowInfo(borrowid);
			Map<String, String> resultMap = new HashMap<String, String>(); 
			if(result > 0) {
				resultMap.put("state", "1");
				resultMap.put("msg", "删除成功");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}else {
				resultMap.put("state", "2");
				resultMap.put("msg", "删除失败");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}
		}else if("returnBook".equals(method)) {
			String borrowid = request.getParameter("borrowid");
			String bookid = request.getParameter("bookid");
			int result = borrowInfoService.returnBook(borrowid, bookid);
			Map<String, String> resultMap = new HashMap<String, String>(); 
			if(result > 0) {
				resultMap.put("state", "1");
				resultMap.put("msg", "还书成功");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}else {
				resultMap.put("state", "2");
				resultMap.put("msg", "还书失败");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}
		}else if("getBookState".equals(method)) {
			String bookid = request.getParameter("bookid");
			int result = borrowInfoService.getBookState(bookid);
			Map<String, String> resultMap = new HashMap<String, String>(); 
			if(result > 0) {
				resultMap.put("state", "2");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}else {
				resultMap.put("state", "1");
				response.getWriter().print(JSONObject.toJSONString(resultMap));
			}
		}
	}

}
