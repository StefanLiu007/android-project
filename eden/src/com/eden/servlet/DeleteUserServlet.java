package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.service.UserService;
import com.eden.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		String userAccount = request.getParameter("userAccount");
		System.out.println(userAccount);
		UserService service = new UserServiceImpl();
		boolean flag = service.deleteUserByUSER_ACCOUNT(userAccount);
		if(flag){
			//ɾ���ɹ�
			request.getRequestDispatcher("/ShowUserServlet").forward(request, response);
		}else{
			//����ʧ��
			response.getWriter().println("<script type=\"text/javascript\">alert(\"ɾ��ʧ��\");location.href = \""+request.getContextPath()+"/ShowUserServlet.do\";</script>");
		}
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
