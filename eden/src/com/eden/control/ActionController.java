package com.eden.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Content;
import com.eden.domain.Expert;
import com.eden.domain.User;
import com.eden.service.ContentService;
import com.eden.service.ExpertService;
import com.eden.service.UserService;
import com.eden.service.impl.ContentServiceImpl;
import com.eden.service.impl.ExpertServiceImpl;
import com.eden.service.impl.UserServiceImpl;

@WebServlet("*.do")
@MultipartConfig
public class ActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ActionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
		String path = request.getServletPath();
		int end = path.indexOf(".");
		int start = path.indexOf("/");
		String pathInfo = path.substring(start+1,end);
		if("ManagerLoginServlet".equals(pathInfo)){
			request.getRequestDispatcher("/ManagerLoginServlet").forward(request, response);
		}else if ("ManagerQueryAllServlet".equals(pathInfo)) {
			System.out.println(pathInfo);
			request.getRequestDispatcher("/ManagerQueryAllServlet").forward(request, response);
		}else if ("DeleteManagerServlet".equals(pathInfo)) {
			request.getRequestDispatcher("/DeleteManagerServlet").forward(request, response);
		}else if ("AddManagerServlet".equals(pathInfo)) {
			request.getRequestDispatcher("/AddManagerServlet").forward(request, response);
		}else if("EditManagerServlet".equals(pathInfo)){
			request.getRequestDispatcher("/EditManagerServlet").forward(request, response);
		}else if ("ShowConfessionServlet".equals(pathInfo)) {
			request.getRequestDispatcher("/ShowConfessionServlet").forward(request, response);
		}else if("DeleteConfessionServlet".equals(pathInfo)){
			request.getRequestDispatcher("/DeleteConfessionServlet").forward(request, response);
		}else if ("ShowUserServlet".equals(pathInfo)) {
			request.getRequestDispatcher("/ShowUserServlet").forward(request, response);
		}else if ("ToUpdateUserServlet".equals(pathInfo)) {
			//注备数据
			String userAccount = request.getParameter("userAccount");
			UserService service = new UserServiceImpl();
			User user = service.getUserByUSER_ACCOUNT(userAccount);
			request.setAttribute("user", user);
			//跳转到updateStudent.jsp
			request.getRequestDispatcher("/updateUser.jsp").forward(request, response);

		}else if("DeleteUserServlet".equals(pathInfo)){
			request.getRequestDispatcher("/DeleteUserServlet").forward(request, response);
		}else if("UpdateUserServlet".equals(pathInfo)){
			request.getRequestDispatcher("/UpdateUserServlet").forward(request, response);
		}else if("SearchServlet".equals(pathInfo)){
			request.getRequestDispatcher("/SearchServlet").forward(request, response);
		}else if("ToAddUserServlet".equals(pathInfo)){
			request.getRequestDispatcher("/addUser.jsp").forward(request, response);
		}else if("AddUserServlet".equals(pathInfo)){
			request.getRequestDispatcher("/AddUserServlet").forward(request, response);
		}else if ("ShowExpertServlet".equals(pathInfo)) {
			request.getRequestDispatcher("/ShowExpertServlet").forward(request, response);
		}else if("ToUpdateExpertServlet".equals(pathInfo)){
			//注备数据
			String acco = request.getParameter("acco");
			ExpertService service = new ExpertServiceImpl();
			Expert expert = service.getExpertByAcco(acco);
			request.setAttribute("expert", expert);
			//跳转到updateStudent.jsp
			request.getRequestDispatcher("/UpdateExpert.jsp").forward(request, response);

		}else if("DeleteExpertServlet".equals(pathInfo)){
			request.getRequestDispatcher("/DeleteExpertServlet").forward(request, response);
		}else if("UpdateExpertServlet".equals(pathInfo)){
			request.getRequestDispatcher("/UpdateExpertServlet").forward(request, response);
		}else if("SearchServlet".equals(pathInfo)){
			request.getRequestDispatcher("/SearchServlet").forward(request, response);
		}else if("ToAddExpert".equals(pathInfo)){
			request.getRequestDispatcher("/WEB-INF/views/admin/AddExpert.jsp").forward(request, response);
		}else if("AddExpertServlet".equals(pathInfo)){
			request.getRequestDispatcher("/AddExpertServlet").forward(request, response);
		}else if("ShowProblemServlet".equals(pathInfo)){
			request.getRequestDispatcher("/ShowProblemServlet").forward(request, response);
	    }else if("DeleteProblemServlet".equals(pathInfo)){
			request.getRequestDispatcher("/DeleteProblemServlet").forward(request, response);
	    }else if ("ShowCommentServlet".equals(pathInfo)) {
	    	request.getRequestDispatcher("/ShowCommentServlet").forward(request, response);
		}else if("DeleteCommentServlet".equals(pathInfo)){
			request.getRequestDispatcher("/DeleteCommentServlet").forward(request, response);
		}else if("SearchCommentServlet".equals(pathInfo)){
			request.getRequestDispatcher("/SearchCommentServlet").forward(request, response);
		}else if("ShowContentServlet".equals(pathInfo)){
			request.getRequestDispatcher("/ShowContentServlet").forward(request, response);
		}else if ("AddContentServlet".equals(pathInfo)){
			request.getRequestDispatcher("/AddContentServlet").forward(request, response);
		}else if ("ToUpdateContentServlet".equals(pathInfo)) {
			String contentId=request.getParameter("contentId");
			ContentService contentService= new ContentServiceImpl();
			System.out.println(contentId);
			Content content=contentService.getContent(contentId);
			request.setAttribute("updateContent", content);
			request.getRequestDispatcher("/UpdateContent.jsp").forward(request, response);	
		}else if ("UpdateContentServlet".equals(pathInfo)) {
			request.getRequestDispatcher("/UpdateContentServlet").forward(request, response);
		}else if ("DeleteContentServlet".equals(pathInfo)) {
			System.out.println(request.getParameter("checkId"));
			request.getRequestDispatcher("/DeleteContentServlet").forward(request, response);
		}
	}

}
