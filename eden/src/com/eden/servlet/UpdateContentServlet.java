package com.eden.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.eden.domain.Content;
import com.eden.service.ContentService;
import com.eden.service.impl.ContentServiceImpl;

/**
 * Servlet implementation class UpdateContentServlet
 */
@WebServlet("/UpdateContentServlet")
@MultipartConfig
public class UpdateContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ContentService contentService= new ContentServiceImpl();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userAccount="001";
		String contentId = request.getParameter("contentId");
		String contentTitle=request.getParameter("contentTitle");
		String contentVideo=request.getParameter("Video");
		String contentPicture=request.getParameter("Picture");
		Part part1=request.getPart("contentVideo");
		if (part1.getSize()!=0) {
			String mime1 = part1.getContentType();
			String ext1="";
			if ("image/jpeg".equals(mime1)) {
				ext1=".jpg";
			}else {
				response.getWriter().println("<script type=\"text/javascript\">window.alert(\"你上传的视频类型的不合法\");location.href=\""+request.getContextPath()+"/UpdateContentServlet.do\";</script>");
				return;
			}
			contentVideo=UUID.randomUUID().toString()+ext1;
			String path1=getServletContext().getRealPath("/upload");
			File dir1= new File(path1);
			System.out.println(dir1);
			if (!dir1.exists()) {
				dir1.mkdir();
			}
			File file1 = new File(dir1,contentVideo);
			System.out.println(file1.getPath());
			File file2 = new File("E:\\javaweb\\student\\javaweb\\Eden\\WebContent\\upload\\"+contentVideo);
			file1.createNewFile();
			file2.createNewFile();
			part1.write(file1.getPath());
			part1.write(file2.getPath());
		}

		Part part2=request.getPart("contentPicture");
		if (part2.getSize()!=0) {
			String mime2 = part2.getContentType();
			String ext2="";
			if ("image/jpeg".equals(mime2)) {
				ext2=".jpg";
			}else {
				response.getWriter().println("<script type=\"text/javascript\">window.alert(\"你上传的图片类型的不合法\");location.href=\""+request.getContextPath()+"/UpdateContentServlet.do\";</script>");
				return;
			}
			contentPicture=UUID.randomUUID().toString()+ext2;
			String path2=getServletContext().getRealPath("/upload");
			File dir2= new File(path2);
			if (!dir2.exists()) {
				dir2.mkdir();
			}
			File file3 = new File(dir2,contentPicture);
			System.out.println(file3.getPath());
			File file4 = new File("E:\\javaweb\\student\\javaweb\\Eden\\WebContent\\upload\\",contentPicture);
			file3.createNewFile();
			file4.createNewFile();
			part2.write(file3.getPath());
			part2.write(file4.getPath());
		}
		String contentText= request.getParameter("contentText");
		Date date= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String contentLastTime = dateFormat.format(date);
		Content content= new Content(userAccount, contentId, contentTitle, contentVideo, contentPicture, contentText, 0, contentLastTime);
		boolean isSuccess= contentService.updateContent(content);
		if (isSuccess) {
			response.getWriter().println("<script type=\"text/javascript\">window.alert(\"修改成功\");location.href=\""+request.getContextPath()+"/ShowContentServlet.do\";</script>");
		}else {
			response.getWriter().println("<script type=\"text/javascript\">window.alert(\"修改失败\");location.href=\""+request.getContextPath()+"/UpdateContentServlet.do\";</script>");
		}

	}

}
