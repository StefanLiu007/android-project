package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Confession;
import com.eden.domain.Manager;
import com.eden.domain.PageBean;
import com.eden.service.ConfessionService;
import com.eden.service.ManagerService;
import com.eden.service.impl.ConfessionServiceImpl;
import com.eden.service.impl.ManagerServiceImpl;

@WebServlet("/ShowConfessionServlet")
public class ShowConfessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ShowConfessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
		ConfessionService service = new ConfessionServiceImpl();
		int CurrentPage = Integer.parseInt(request.getParameter("page"));
		String pagesize = request.getParameter("pagesize");
		int pageSizes = Integer.parseInt(pagesize==null?"1":pagesize);
		PageBean<Confession> pb = new PageBean<Confession>();
		try {
			String searchContent = request.getParameter("searchContent");
			System.out.println(searchContent+"w");
			pb=service.findByPage(CurrentPage,pageSizes,searchContent);
			request.setAttribute("pb", pb);
			if(searchContent !=null){
				String s = new String(searchContent.getBytes("ISO-8859-1"), "utf-8");
				request.setAttribute("s",s);
				System.out.println(s+"ww");
			}
			request.getRequestDispatcher("/confession.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
