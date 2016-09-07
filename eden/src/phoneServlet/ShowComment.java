package phoneServlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.CommentEden;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ShowComment
 */
@WebServlet("/ShowComment")
public class ShowComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ShowComment() {
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len;
		while((len = in.read(b)) != -1){
			baos.write(b, 0, len);
		}
		
		Gson g = new Gson();
		String json = new String(baos.toByteArray(),"utf-8");
		List<String> list = g.fromJson(json, List.class);
		PhoneService service = new PhoneServiceImpl();
		List<CommentEden> comments = service.ShowComment(list.get(0));
		if(comments != null){
			System.out.println(comments.toString());
			String h = g.toJson(comments);
			response.getWriter().print(h);
		}
	}

}
