package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.service.ExpertService;
import com.eden.service.impl.ExpertServiceImpl;


/**
 * Servlet implementation class DeleteStudentServlet
 */
@WebServlet("/DeleteExpertServlet")
public class DeleteExpertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String acco = request.getParameter("acco");
		ExpertService service = new ExpertServiceImpl();
		boolean flag = service.deleteExpertByAcco(acco);
		if(flag){
			request.getRequestDispatcher("/ShowExpertServlet").forward(request, response);
		}else{
			response.getWriter().println("<script type=\"text/javascript\">alert(\"删除失败\");location.href = \""+request.getContextPath()+"/ShowExpertServlet.do\";</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
