package com.eden.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.dao.ManagerDao;
import com.eden.dao.impl.ManagerDaoImpl;
import com.eden.domain.Manager;
import com.eden.service.ManagerService;
import com.eden.service.impl.ManagerServiceImpl;



@WebServlet("/ManagerLoginServlet")
public class ManagerLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ManagerLoginServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		ManagerService service = new ManagerServiceImpl();
		String account = request.getParameter("txtid");
		String pwd = request.getParameter("txtpass");
		String checkcode = request.getParameter("txtrad");
		PrintWriter pWriter = response.getWriter();
		String radstr = (String) request.getSession().getAttribute("randomCode");
		if (checkcode.equalsIgnoreCase(radstr)) {
			if (account != null && pwd != null) {
				try {
					
					Manager resuser = service.findByAccountAndPwd(account, pwd);
					System.out.println(resuser);
					request.getSession().setAttribute("manager", resuser);
					if (resuser != null) {
						pWriter.write("success");
					} else {
						pWriter.write("fail");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else {
			pWriter.write("验证码错误");
		}

		pWriter.close();
	}

	}


