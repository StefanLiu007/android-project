package com.eden.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.dao.ManagerDao;
import com.eden.dao.impl.ManagerDaoImpl;
import com.eden.domain.Manager;
import com.eden.domain.PageBean;
import com.eden.service.ManagerService;
import com.eden.service.impl.ManagerServiceImpl;
import com.sun.xml.internal.ws.wsdl.writer.document.Service;


@WebServlet("/ManagerQueryAllServlet")
public class ManagerQueryAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ManagerQueryAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ManagerService service = new ManagerServiceImpl();
		int CurrentPage = Integer.parseInt(request.getParameter("page"));
		System.out.println(CurrentPage);
		String pagesize = request.getParameter("pagesize");
		int pageSizes = Integer.parseInt(pagesize==null?"2":pagesize);
		PageBean<Manager> pb = new PageBean<Manager>();
		try {
			pb=service.findByPage(CurrentPage, pageSizes);
			
			request.setAttribute("pb", pb);
			request.getRequestDispatcher("/managerQueryAll.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
