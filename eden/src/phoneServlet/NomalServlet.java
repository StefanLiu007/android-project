package phoneServlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.AskedExpert;
import com.eden.domain.Expert;
import com.eden.domain.Problem;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;
import com.google.gson.Gson;

import io.rong.util.GsonUtil;

/**
 * Servlet implementation class NomalServlet
 */
@WebServlet("/NomalServlet")
public class NomalServlet extends HttpServlet {
	 Gson g = new Gson();
	  PhoneService service = new PhoneServiceImpl();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NomalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
	    String j = request.getParameter("acode");
	    switch (j) {
		case "0":insertAskedExpert(request,response);
			
			break;
		case "1":searchNewProblem(request,response);
		break;
		case "2":searchAskedExpert(request,response);
		default:
			break;
		}
	}

	private void searchAskedExpert(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = request.getParameter("account");
		LinkedList<Expert> expert = service.searchAskedExpert(account);
		if(expert != null){
			response.getWriter().print(GsonUtil.toJson(expert));
		}
		
	}

	private void searchNewProblem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userAccount = request.getParameter("account");
		Problem p = service.searchNewProblem(userAccount);
		if(p != null){
			String json = GsonUtil.toJson(p);
			response.getWriter().print(json);
		}
		
	}

	private void insertAskedExpert(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AskedExpert expert = (AskedExpert) GsonUtil.fromJson(request.getParameter("asked"), AskedExpert.class);
		boolean b = service.addAskedExpert(expert);
		if(b){
			String json = GsonUtil.toJson(b);
			response.getWriter().print(json);
		}
		
		
	}

}
