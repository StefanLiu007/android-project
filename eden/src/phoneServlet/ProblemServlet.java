package phoneServlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Answer;
import com.eden.domain.AnswerEden;
import com.eden.domain.Problem;
import com.eden.domain.ProblemEden;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;
import com.google.gson.Gson;

import io.rong.util.GsonUtil;

/**
 * Servlet implementation class ProblemServlet
 */
@WebServlet("/ProblemServlet")
public class ProblemServlet extends HttpServlet {
	  Gson g = new Gson();
	  PhoneService service = new PhoneServiceImpl();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProblemServlet() {
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
		case "1":ShowProblem(request,response);
			
			break;
		case "2":showProblemComment(request,response);
		break;
		case "3":addCommentReply(request,response);
        break;
		case "4":sendProblem(request,response);
		break;
		default:
			break;
		}
	  
	    
	}

	private void sendProblem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = request.getParameter("problem");
		Problem problem = (Problem) GsonUtil.fromJson(json, Problem.class);
		boolean b = service.addProblem(problem);
		if(b){
			String j = GsonUtil.toJson(problem);
			response.getWriter().print(j);
		}
		
	}

	private void addCommentReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = request.getParameter("answer");
		
		Answer answer = (Answer) GsonUtil.fromJson(json, Answer.class);
		boolean b = service.addReply(answer);
		if(b){
			String j = GsonUtil.toJson(b);
			response.getWriter().print(j);
		}
		
	}

	private void ShowProblem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ProblemEden> list = service.showProblem();
		
		if(list != null){
			String json = g.toJson(list);
		
			response.getWriter().print(json);
		}
		
	}

	private void showProblemComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = request.getParameter("ID");
		//Answer answer = (Answer) GsonUtil.fromJson(json, Answer.class);
		List<AnswerEden> results = service.showAnswer(json);
		if(results != null){
			String j = g.toJson(results);
			
			response.getWriter().print(j);
		}
		
		
	}

}
