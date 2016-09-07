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


@WebServlet("/EditManagerServlet")
public class EditManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public EditManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int acode = Integer.parseInt(request.getParameter("acode"));
		ManagerService service = new ManagerServiceImpl();
		if(acode == 3){
			int id = Integer.parseInt(request.getParameter("mid"));
			try {
				Manager m =service.findManagerById(id);
				if(m !=null){
					request.setAttribute("editManager", m);
					request.getRequestDispatcher("managerRegister.jsp").forward(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
