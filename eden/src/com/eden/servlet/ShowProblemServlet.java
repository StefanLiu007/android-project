package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.domain.ProblemPage;
import com.eden.service.ProblemService;
import com.eden.service.impl.ProblemServiceImpl;

/**
 * Servlet implementation class ShowExpert
 */
@WebServlet("/ShowProblemServlet")
public class ShowProblemServlet extends HttpServlet {
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
		ProblemService service=new ProblemServiceImpl();
		String sCurrentPage=request.getParameter("currentPage");
		if (sCurrentPage==null) {
			sCurrentPage="1";
		}
		String searchContent=request.getParameter("searchContent");
		if(searchContent!=null){
		searchContent=new String(searchContent.getBytes("ISO-8859-1"),"utf-8");
		}
		ProblemPage page=service.getProblemByPage(Integer.parseInt(sCurrentPage), 10, searchContent);
		request.setAttribute("page", page);
		if (searchContent!=null) {
			request.setAttribute("searchContent", searchContent);
		}
		request.getRequestDispatcher("/showProblem1.jsp").forward(request, response);
	}

}
