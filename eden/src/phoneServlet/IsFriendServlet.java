package phoneServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Collection;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;

/**
 * Servlet implementation class IsFriendServlet
 */
@WebServlet("/IsFriendServlet")
public class IsFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IsFriendServlet() {
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
		String userAccount = request.getParameter("userAccount");
		String friendAccount = request.getParameter("friendAccount");
		System.out.println(userAccount+friendAccount+"3333333333");
		PhoneService service = new PhoneServiceImpl();
		boolean b1 = service.isFriend(userAccount,friendAccount);
		
		
			Boolean a = new Boolean(b1);
			response.getWriter().print(a);
		
	}

}
