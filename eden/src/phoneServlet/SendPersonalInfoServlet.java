package phoneServlet;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.core.ApplicationPart;

import com.eden.dao.UserDao;
import com.eden.dao.impl.UserDaoImplJDBC;
import com.eden.domain.User;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;
import com.google.gson.Gson;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * Servlet implementation class SendPersonalInfoServlet
 */
@WebServlet("/SendPersonalInfoServlet")

@MultipartConfig
public class SendPersonalInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendPersonalInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html,charset=utf-8");	
		String json = request.getParameter("json");
		System.out.println(json+"222222222222");
		Gson g = new Gson();
		User user = g.fromJson(json, User.class);
		PhoneService sendService = new PhoneServiceImpl();
		if (user!=null) {
			boolean se = sendService.sendSuccess(user);
			
			if (se) {
				String newuser = g.toJson(user);
				response.getWriter().print(newuser);
			}else{
				response.getWriter().print("false");
			}
		}
	    ApplicationPart part = (ApplicationPart) request.getPart("file");
		String url = getuploadfile(request,part);
		String newurl = url.substring(34, url.length());
		String account = user.getUserMobile();
		String aa=newurl;
		System.out.println("newurl="+newurl+"account="+account);
		UserDao dao = new UserDaoImplJDBC();
		boolean saved = dao.UpdateImage(account, aa);
		if(saved){
			response.getWriter().println("updateImage Successfully");
		}
	}
		private String getuploadfile(HttpServletRequest request,ApplicationPart part)
				throws IOException, ServletException {
			String path = request.getServletContext().getRealPath("/upload");
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdir();
			}
			
			System.out.println("part="+part);
			System.out.println(part.getName());
			String filesuf = part.getSubmittedFileName();
			System.out.println("filesuf="+filesuf);
			String index = filesuf.substring(filesuf.indexOf("."));
			String fileName = UUID.randomUUID().toString();
			File file = new File(dir, fileName+""+index);
			part.write(file.getPath());
			String url = file.getAbsolutePath();
			return url;
//		
		}
		
	

}
