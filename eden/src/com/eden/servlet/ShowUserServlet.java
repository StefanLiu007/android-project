package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.PageBean;
import com.eden.domain.User;
import com.eden.service.UserService;
import com.eden.service.impl.UserServiceImpl;

/**
 * Servlet implementation class ShowUserServlet
 */
@WebServlet("/ShowUserServlet")
public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService service = new UserServiceImpl();
		//获得页码的参数
		String sCurrentPage = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		if(sCurrentPage == null){
			sCurrentPage = "1";
		}
		if(pagesize == null){
			pagesize = "1";
		}
		//获得搜索的内容
		String searchContent = request.getParameter("searchContent");
		PageBean<User> page;
		try {
			page = service.getUserByPage(Integer.parseInt(sCurrentPage),Integer.parseInt(pagesize), searchContent);
			request.setAttribute("pb", page);//servlet==>jsp
			if(searchContent != null){
				String s = new String(searchContent.getBytes("ISO-8859-1"), "utf-8");
				request.setAttribute("s", s);//servlet==>jsp
			}
			request.getRequestDispatcher("/showuse.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}

