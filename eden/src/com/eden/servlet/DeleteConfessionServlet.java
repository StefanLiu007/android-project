package com.eden.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Confession;
import com.eden.service.ConfessionService;
import com.eden.service.impl.ConfessionServiceImpl;


@WebServlet("/DeleteConfessionServlet")
public class DeleteConfessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public DeleteConfessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    int currentPage = Integer.parseInt(request.getParameter("page"));
	    System.out.println(currentPage+".....");
	    int id = Integer.parseInt(request.getParameter("id"));
	    ConfessionService service = new ConfessionServiceImpl();
	    try {
			int a = service.deleteById(id);
			if(a>0){
				response.sendRedirect("ShowConfessionServlet?page="+currentPage);
			}else{
				response.getWriter().print("<script>alert('删除失败')</script>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
