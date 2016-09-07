package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.User;
import com.eden.service.UserService;
import com.eden.service.impl.UserServiceImpl;
import com.eden.util.StringUtil;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
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
		//获得请求参数并做非空处理
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String USER_ACCOUNT = request.getParameter("huserAccount");
		USER_ACCOUNT = StringUtil.processNull(USER_ACCOUNT);
		String USER_NICKNAME = request.getParameter("USER_NICKNAME");
		USER_NICKNAME = StringUtil.processNull(USER_NICKNAME);
		String USER_PASSWORD = request.getParameter("USER_PASSWORD");
		USER_PASSWORD = StringUtil.processNull(USER_PASSWORD);
		String USER_NAME = request.getParameter("USER_NAME");
		USER_NAME = StringUtil.processNull(USER_NAME);
		String USER_SIGNATURE = request.getParameter("USER_SIGNATURE");
		USER_SIGNATURE = StringUtil.processNull(USER_SIGNATURE);
		String USER_BIRTHDATE = request.getParameter("USER_BIRTHDATE");
		USER_BIRTHDATE = StringUtil.processNull(USER_BIRTHDATE);
		String USER_SEX = request.getParameter("USER_SEX");
		USER_SEX = StringUtil.processNull(USER_SEX);
		String USER_SCHOOL = request.getParameter("USER_SCHOOL");
		USER_SCHOOL = StringUtil.processNull(USER_SCHOOL);
		String USER_MOBILE = request.getParameter("USER_MOBILE");
		USER_MOBILE = StringUtil.processNull(USER_MOBILE);
		String USER_EMAIL = request.getParameter("USER_EMAIL");
		USER_EMAIL = StringUtil.processNull(USER_EMAIL);
		String USER_ICON = request.getParameter("USER_ICON");
		USER_ICON = StringUtil.processNull(USER_ICON);
		User user = new User(USER_ACCOUNT,USER_NICKNAME,USER_PASSWORD,USER_NAME,USER_SIGNATURE,USER_BIRTHDATE,USER_SEX,USER_SCHOOL,USER_MOBILE,USER_EMAIL,USER_ICON);
		UserService service = new UserServiceImpl();
		boolean flag = service.updateUser(user);
		if(flag){
			response.getWriter().println("<script type=\"text/javascript\">alert(\"修改成功\");location.href = \""+request.getContextPath()+"/ShowUserServlet.do\";</script>");
		}else{
			response.getWriter().println("<script type=\"text/javascript\">alert(\"修改失败\");location.href = \""+request.getContextPath()+"/ToUpdateUserServlet.do?USER_ACCOUNT=+\""+USER_ACCOUNT+";</script>");
		}
	}

}
