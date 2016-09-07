package phoneServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

/**
 * Servlet implementation class ChattingServlet
 */
@WebServlet("/ChattingServlet")
public class ChattingServlet extends HttpServlet {
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
		String key = "0vnjpoadnwo6z";
		String secret = "c6LZe0BUGxvXi";
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String userId=request.getParameter("userId");
		String userName=request.getParameter("userName");
		String portraitUri=request.getParameter("portraitUri");
		PhoneService phoneService= new PhoneServiceImpl();
		boolean isSuccess= phoneService.setUserState("1", userId);
		if(isSuccess){
			try {

				String token=phoneService.getToken(userId);
				if(token==null){
					SdkHttpResult result= ApiHttpClient.getToken(key, secret, userId, userName, portraitUri, FormatType.json);
					token=result.toString();
					phoneService.addToken(userId, token);
					System.out.println(token);
					response.getWriter().print(token);
				}else{
					System.out.println(token);
					response.getWriter().print(token);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{

		}
	}

}
