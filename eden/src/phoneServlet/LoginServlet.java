package phoneServlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eden.domain.User;
import com.eden.service.UserService;
import com.eden.service.impl.UserServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class SearchUserServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		InputStream in = request.getInputStream();
		
			byte[] b = new byte[1024];
			int len;
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while((len=in.read(b))!=-1){
				baos.write(b,0,len);
			}
			
			String json = new String(baos.toByteArray(),"utf-8");
			Gson g = new Gson();
			User use = g.fromJson(json, User.class);
			UserService service = new UserServiceImpl();
			if(use !=null){
				User b1 = service.loginSuccess(use.getUserAccount(),use.getUserPassword());
				if(b1 != null){
					String newUse = g.toJson(b1);
					response.getWriter().print(newUse);
				}else{
					response.getWriter().print("false");
				}
			}
			
		}
		
	

}
