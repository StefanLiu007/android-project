package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.domain.CommentPage;
import com.eden.service.CommentService;
import com.eden.service.impl.CommentServiceImpl;

/**
 * Servlet implementation class ShowCommentServlet
 */
@WebServlet("/ShowCommentServlet")
public class ShowCommentServlet extends HttpServlet {
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
		CommentService service = new CommentServiceImpl();
		
		String sCurrentPage = request.getParameter("currentPage");
		if(sCurrentPage == null){
			sCurrentPage = "1";
		}
		
		String searchContent = request.getParameter("searchContent");
		CommentPage page = service.getCommentByPage(Integer.parseInt(sCurrentPage), 1,searchContent);
		request.setAttribute("page", page);//servlet==>jsp
		if(searchContent != null){
			request.setAttribute("searchContent", searchContent);//servlet==>jsp
		}
		request.getRequestDispatcher("/showComment1.jsp").forward(request, response);
	}

}
