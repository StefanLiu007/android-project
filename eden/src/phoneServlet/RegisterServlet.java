package phoneServlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.log.UserDataHelper;

import com.eden.domain.User;
import com.eden.service.PhoneService;
import com.eden.service.UserService;
import com.eden.service.impl.PhoneServiceImpl;
import com.eden.service.impl.UserServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class Register
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		InputStream is = request.getInputStream();
		int len;
		byte[]b = new byte[1024]; 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while((len = is.read(b))!=-1){
			baos.write(b,0,len);
		}
		System.out.println("register");
		String json = new String(baos.toByteArray(), "utf-8");
		Gson g = new Gson();
		User user = g.fromJson(json, User.class);
		PhoneService userService = new PhoneServiceImpl();
		if (user!=null) {
			boolean ap = userService.registerSuccess(user);
			if (ap) {
				String newuser = g.toJson(user);
				response.getWriter().print(newuser);
			}else{
				response.getWriter().print("false");
			}
		}
	}

}
