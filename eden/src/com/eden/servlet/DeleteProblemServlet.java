package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.service.ProblemService;
import com.eden.service.impl.ProblemServiceImpl;

/**
 * Servlet implementation class DeleteProblemServlet
 */
@WebServlet("/DeleteProblemServlet")
public class DeleteProblemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		ProblemService service = new ProblemServiceImpl();
		boolean flag = service.deleteProblemByID(id);
		if(flag){
			request.getRequestDispatcher("/ShowProblemServlet").forward(request, response);
		}else{
			response.getWriter().println("<script type=\"text/javascript\">alert(\"删除失败\");location.href = \""+request.getContextPath()+"/ShowProblemServlet.do\";</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
