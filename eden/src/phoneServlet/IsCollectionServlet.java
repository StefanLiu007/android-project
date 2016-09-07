package phoneServlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Collection;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class IsCollectionServlet
 */
@WebServlet("/IsCollectionServlet")
public class IsCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IsCollectionServlet() {
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
		String account = request.getParameter("userAccount");
		String id = request.getParameter("Id");
		System.out.println(account+id);
	    Collection c = new Collection(account, id);
		PhoneService service = new PhoneServiceImpl();
		boolean b1 = service.isCollection(c);
		System.out.println(b1+"22222222222222222222");
		response.getWriter().print(b1+"");
	}

}
