package phoneServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eden.domain.CollectionBean;
import com.eden.domain.CollectionContent;
import com.eden.domain.CollectionProblem;
import com.eden.service.PhoneService;
import com.eden.service.impl.PhoneServiceImpl;

import io.rong.util.GsonUtil;

/**
 * Servlet implementation class CollectionServlet
 */
@WebServlet("/CollectionServlet")
public class CollectionServlet extends HttpServlet {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String userAccount=request.getParameter("collection_account");
		PhoneService phoneService=new PhoneServiceImpl();
		List<CollectionContent> collectionContents=phoneService.finCollectionContent(userAccount);
		List<CollectionProblem> collectionProblems=phoneService.finCollectionProblem(userAccount);
		CollectionBean collectionBean= new CollectionBean(collectionContents, collectionProblems);
		String json=GsonUtil.toJson(collectionBean);
		System.out.println(json);
		response.getWriter().print(json);

	}

}
