package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.domain.ContentPage;
import com.eden.service.ContentService;
import com.eden.service.impl.ContentServiceImpl;

/**
 * Servlet implementation class ShowContentServlet
 */
@WebServlet("/ShowContentServlet")
public class ShowContentServlet extends HttpServlet {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		ContentService contentService = new ContentServiceImpl();
		String currentPage = request.getParameter("currentPage");
		if (currentPage==null) {
			currentPage="1";
		}
		String searchContent=request.getParameter("searchContent");
		ContentPage page=contentService.getContents(Integer.parseInt(currentPage),10,searchContent);
		request.setAttribute("ContentPage", page);
		if (searchContent!=null) {
			request.setAttribute("searchContent", searchContent);
		}
		request.getRequestDispatcher("/Content.jsp").forward(request, response);
	}

}
