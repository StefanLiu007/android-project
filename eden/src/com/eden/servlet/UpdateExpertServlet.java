package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.domain.Expert;
import com.eden.service.ExpertService;
import com.eden.service.impl.ExpertServiceImpl;
import com.eden.util.StringUtil;


/**
 * Servlet implementation class UpdateStudentServlet
 */
@WebServlet("/UpdateExpertServlet")
public class UpdateExpertServlet extends HttpServlet {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String EXPERT_ACCOUNT = request.getParameter("hexpertAccount");
		EXPERT_ACCOUNT = StringUtil.processNull(EXPERT_ACCOUNT);
		String EXPERT_NAME = request.getParameter("EXPERT_NAME");
		EXPERT_NAME = StringUtil.processNull(EXPERT_NAME);
		String EXPERT_SEX = request.getParameter("EXPERT_SEX");
		EXPERT_SEX = StringUtil.processNull(EXPERT_SEX);
		String EXPERT_STATE = request.getParameter("EXPERT_STATE");
		EXPERT_STATE = StringUtil.processNull(EXPERT_STATE);
		String EXPERT_INTRODUCE = request.getParameter("EXPERT_INTRODUCE");
		EXPERT_INTRODUCE = StringUtil.processNull(EXPERT_INTRODUCE);
		String EXPERT_PV = request.getParameter("EXPERT_PV");
		EXPERT_PV = StringUtil.processNull(EXPERT_PV);
		String EXPERT_ADDRESS = request.getParameter("EXPERT_ADDRESS");
		EXPERT_ADDRESS = StringUtil.processNull(EXPERT_ADDRESS);
		String ERTERT_ICON = request.getParameter("ERTERT_ICON");
		ERTERT_ICON = StringUtil.processNull(ERTERT_ICON);
		String ERTERT_EMAIL = request.getParameter("ERTERT_EMAIL");
		ERTERT_EMAIL = StringUtil.processNull(ERTERT_EMAIL);
		Expert expert=new Expert(EXPERT_ACCOUNT, EXPERT_NAME, EXPERT_SEX, EXPERT_STATE, EXPERT_INTRODUCE, Integer.parseInt(EXPERT_PV), EXPERT_ADDRESS, ERTERT_ICON, ERTERT_EMAIL);
		ExpertService service = new ExpertServiceImpl();
		boolean flag = service.updateExpert(expert);
		if(flag){
			response.getWriter().println("<script type=\"text/javascript\">alert(\"修改成功\");location.href = \""+request.getContextPath()+"/ShowExpertServlet.do\";</script>");
		}else{
			response.getWriter().println("<script type=\"text/javascript\">alert(\"修改失败\");location.href = \""+request.getContextPath()+"/ToUpdateExpertServlet.do?EXPERT_ACCOUNT=+\""+EXPERT_ACCOUNT+";</script>");
		}
	}

}
