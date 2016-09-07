package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.service.CommentService;
import com.eden.service.impl.CommentServiceImpl;
/**
 * Servlet implementation class DeleteCommentServlet
 */
@WebServlet("/DeleteCommentServlet")
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String commentId = request.getParameter("commentId");
		System.out.println(commentId);
		CommentService service = new CommentServiceImpl();
		boolean flag = service.deleteCommentBycommentId(commentId);
		if(flag){
		
			request.getRequestDispatcher("/ShowCommentServlet").forward(request, response);
		}else{
			
			response.getWriter().println("<script type=\"text/javascript\">alert(\"É¾³ý³É¹¦\");location.href = \""+request.getContextPath()+"/ShowCommentServlet.do\";</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
