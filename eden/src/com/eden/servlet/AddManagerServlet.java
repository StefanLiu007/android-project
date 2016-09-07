package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Manager;
import com.eden.service.ManagerService;
import com.eden.service.impl.ManagerServiceImpl;


@WebServlet("/AddManagerServlet")
public class AddManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       response.setContentType("text/html;charset=utf-8");
	       request.setCharacterEncoding("utf-8");
	       ManagerService service = new ManagerServiceImpl();
	       int acode = Integer.parseInt(request.getParameter("acode"));
	       if(acode==1){
	    	   String account = request.getParameter("account");
	    	   String pwd = request.getParameter("password");
	    	   String name = request.getParameter("name");
	    	   System.out.println(name);
	    	   Manager m = new Manager(account,name,pwd);
	    	  
	    	   try {
				int a = service.addManager(m);
				if(a>0){
					response.getWriter().print("<script> alert('添加成功'),window.location.href='managerRegister.jsp';</script>");
				}else {
					response.getWriter().print("<script> alert('添加失败'),window.location.href='managerRegister.jsp';</script>");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       }
	       if(acode == 0){
				int id = Integer.parseInt(request.getParameter("id"));
				String name = request.getParameter("name");
				String account = request.getParameter("account");
				String pwd = request.getParameter("password");
				Manager m = new Manager(id,account, name, pwd);
				try {
					int a = service.updateManager(m);
					if(a>0){
						response.getWriter().print("<script>alert('修改成功');window.location.href='managerQueryAll.jsp';</script>");
					}else {
						response.getWriter().print("<script>alert('修改失败');window.location.href='managerQueryAll.jsp';</script>");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
