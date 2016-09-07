package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.domain.ExpertPage;
import com.eden.service.ExpertService;
import com.eden.service.impl.ExpertServiceImpl;

/**
 * Servlet implementation class ShowExpert
 */
@WebServlet("/ShowExpertServlet")
public class ShowExpertServlet extends HttpServlet {
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
		ExpertService service=new ExpertServiceImpl();
		String sCurrentPage=request.getParameter("currentPage");
		if (sCurrentPage==null) {
			sCurrentPage="1";
		}
		String searchContent=request.getParameter("searchContent");
		ExpertPage page=service.getExpertByPage(Integer.parseInt(sCurrentPage), 10, searchContent);
		request.setAttribute("page", page);
		if (searchContent!=null) {
			request.setAttribute("searchContent", searchContent);
		}
		request.getRequestDispatcher("/WEB-INF/views/admin/showExpert1.jsp").forward(request, response);
	}

}
