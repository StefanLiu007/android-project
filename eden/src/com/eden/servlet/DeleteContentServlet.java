package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.service.ContentService;
import com.eden.service.impl.ContentServiceImpl;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Servlet implementation class DeleteContentServlet
 */
@WebServlet("/DeleteContentServlet")
@MultipartConfig
public class DeleteContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess=false;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String contentId=request.getParameter("contentId");
		String searchContent=request.getParameter("searchContent");
		int currentPage=Integer.parseInt(request.getParameter("currentPage"));
		String checkId=request.getParameter("checkId");
		ContentService contentService= new ContentServiceImpl();
		int num=contentService.getContentCount(10, searchContent);
		if (checkId!=null) {
			String [] stringArr= checkId.split(",");
			for (String string : stringArr) {
				isSuccess=contentService.deleteContent(string);
			}
		}else {
			isSuccess=contentService.deleteContent(contentId);
		}
		if (isSuccess) {
			if (num%10==1) {
				currentPage=currentPage-1;
			}
			if (currentPage==0) {
				response.getWriter().println("<script type=\"text/javascript\">window.alert(\"删除成功\");location.href=\""+request.getContextPath()+"/ShowContentServlet.do?\";</script>");
			}
			response.getWriter().println("<script type=\"text/javascript\">window.alert(\"删除成功\");location.href=\""+request.getContextPath()+"/ShowContentServlet.do?currentPage="+currentPage+"\";</script>");
		}else {
			response.getWriter().println("<script type=\"text/javascript\">window.alert(\"删除失败\");location.href=\""+request.getContextPath()+"/ShowContentServlet.do\";</script>");
		}
	}

}
