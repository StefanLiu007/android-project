package phoneServlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.Comment;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class Comment
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
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
		InputStream in = request.getInputStream();
		byte[] b = new byte[1024];
		System.out.println(b);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len;
		System.out.println(b);
		while(( len = in.read(b)) !=-1){
			System.out.println(len);
			baos.write(b,0,len);
		}
		String json = new String(baos.toByteArray(),"utf-8");
		Gson g = new Gson();
		Comment c = g.fromJson(json, Comment.class);
		
		PhoneService service = new PhoneServiceImpl();
		boolean b1 = service.comment(c);
		if(b1){
			String j = g.toJson(c);
			response.getWriter().print(j);
		}
		
	}

}
