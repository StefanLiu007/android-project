package phoneServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Content;
import com.eden.domain.ContentEden;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class Phone
 */
@WebServlet("/ContentServlet")
public class ContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentServlet() {
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
		
		PhoneService service = new PhoneServiceImpl();
		List<ContentEden> content = service.loadContent();
		
		if(content != null){
			Gson g = new Gson();
			String content1 = g.toJson(content);
			response.getWriter().print(content1);
		}
		
		
	}

}
