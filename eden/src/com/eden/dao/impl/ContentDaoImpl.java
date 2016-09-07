package com.eden.dao.impl;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eden.dao.ContentDao;
import com.eden.domain.Content;
import com.eden.domain.ContentPage;
import com.eden.util.DBHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ContentDaoImpl implements ContentDao{
	@Override
	public ContentPage getContents(int currentPage, int pageSize, String searchContent) {
		List<Content> contents=new ArrayList<Content>();
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet res=null;
		try {
			con=DBHelper.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int totalPage=(getContentCount(pageSize, searchContent)+pageSize-1)/pageSize;
		try {
			int start=(currentPage-1)*pageSize;
			if (searchContent==null) {
				String sql ="select * from content limit ?,?";
				pst=(PreparedStatement) con.prepareStatement(sql);
				pst.setInt(1, start);
				pst.setInt(2, pageSize);
			}else {
				String sql = "select * from content where CONTENT_TITLE like ? limit ?,?";
				pst=(PreparedStatement) con.prepareStatement(sql);
				pst.setString(1, "%"+searchContent+"%");
				pst.setInt(2, start);
				pst.setInt(3, pageSize);
			}
			res=pst.executeQuery();
			while (res.next()) {
				String userAccount=res.getString("USER_ACCOUNT");
				String contentId=res.getString("CONTENT_ID");
				String contentTitle=res.getString("CONTENT_TITLE");
				String contentVideo=res.getString("CONTENT_VIDEO");
				String contentPicture=res.getString("CONTENT_PICTURE");
				String contentText=res.getString("CONTENT_TEXT");
				int thumbUpNum=res.getInt("THUMBUP_NUM");
				String contentLastTime=res.getString("CONTENT_LASTTIME");
				Content content= new Content(userAccount, contentId, contentTitle, contentVideo, contentPicture, contentText, thumbUpNum, contentLastTime);
				contents.add(content);
			}
			ContentPage page= new ContentPage(contents, currentPage, totalPage);
			System.out.println(page);
			return page;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(con, pst, res);
		}
		return null;
	}

	@Override
	public boolean addContent(Content content) {
		Connection con=null;
		PreparedStatement pst =null;
		try {
			con=DBHelper.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql="INSERT INTO content(USER_ACCOUNT,CONTENT_ID,CONTENT_TITLE,CONTENT_VIDEO,CONTENT_PICTURE,CONTENT_TEXT,THUMBUP_NUM,CONTENT_LASTTIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pst=(PreparedStatement) con.prepareStatement(sql);
			pst.setString(1, content.getUserAccount());
			pst.setString(2, content.getContentId());
			pst.setString(3, content.getContentTitle());
			pst.setString(4, content.getContentVideo());
			pst.setString(5, content.getContentPicture());
			pst.setString(6, content.getContentText());
			pst.setInt(7, content.getThumbUpNum());
			pst.setString(8, content.getContentLastTime());
			int i=pst.executeUpdate();
			if (i>0) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(con, pst, null);
		}
		return false;
	}

	@Override
	public boolean updateContent(Content content) {
		Connection con=null;
		PreparedStatement pst=null;
		try {
			con=DBHelper.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql= "update content set USER_ACCOUNT=?,CONTENT_TITLE=?,CONTENT_VIDEO=?,CONTENT_PICTURE=?,CONTENT_TEXT=?,THUMBUP_NUM=?,CONTENT_LASTTIME=? where CONTENT_ID=?";
		try {
			pst=(PreparedStatement) con.prepareStatement(sql);
			pst.setString(1, content.getUserAccount());
			pst.setString(2, content.getContentTitle());
			pst.setString(3, content.getContentVideo());
			pst.setString(4, content.getContentPicture());
			pst.setString(5, content.getContentText());
			pst.setInt(6, content.getThumbUpNum());
			pst.setString(7, content.getContentLastTime());
			pst.setString(8, content.getContentId());
			int i= pst.executeUpdate();
			if (i>0) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Aut o-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(con, pst, null);
		}
		return false;
	}

	@Override
	public boolean deleteContent(String contentId) {
		Connection con=null;
		CallableStatement pst=null;
		try {
			con=DBHelper.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			pst=con.prepareCall("CALL pro_content(?)");
			pst.setString(1, contentId);
			int i= pst.executeUpdate();
			if (i>0) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Content getContent(String contentId) {
		Connection con= null;
		PreparedStatement pst=null;
		ResultSet res =null;
		try {
			con=DBHelper.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql="select * from content where CONTENT_ID=?";
		try {
			pst=(PreparedStatement) con.prepareStatement(sql);
			pst.setString(1, contentId);
			res=pst.executeQuery();
			if (res.next()) {
				String userAccount=res.getString("USER_ACCOUNT");
				String contentTitle=res.getString("CONTENT_TITLE");
				String contentVideo=res.getString("CONTENT_VIDEO");
				String contentPicture=res.getString("CONTENT_PICTURE");
				String contentText=res.getString("CONTENT_TEXT");
				int thumbUpNum=res.getInt("THUMBUP_NUM");
				String contentLastTime=res.getString("CONTENT_LASTTIME");
				Content content= new Content(userAccount, contentId, contentTitle, contentVideo, contentPicture, contentText, thumbUpNum, contentLastTime);
				return content;
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getContentCount(int pageSize, String searchContent) {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet res=null;
		try {
			con=DBHelper.getConnection();
			String sql="";
			if (searchContent==null) {
				sql="select count(*) from content";
				pst=(PreparedStatement) con.prepareStatement(sql);
			}else {
				sql="select count(*) from content where CONTENT_TITLE like ? ";
				pst = (PreparedStatement) con.prepareStatement(sql);
				pst.setString(1, "%"+searchContent+"%");
			}
			res=pst.executeQuery();
			if (res.next()) {
				return (res.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(con, pst, res);
		}
		return 0;
	}

}
