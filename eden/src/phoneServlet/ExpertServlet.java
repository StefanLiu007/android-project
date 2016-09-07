package phoneServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Expert;
import com.eden.service.ExpertService;
import com.eden.service.impl.ExpertServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ExpertAndroidServlet
 */
@WebServlet("/ExpertServlet")
public class ExpertServlet extends HttpServlet {
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
		response.setContentType("application/json;charset=utf-8");
		Gson gson= new Gson();
		ExpertService expertService=new ExpertServiceImpl();
		List<Expert> experts=expertService.ShowAllExpert();
		String jsonExpers=gson.toJson(experts);
		System.out.println(jsonExpers);
		response.getWriter().println(jsonExpers);
	}

}
