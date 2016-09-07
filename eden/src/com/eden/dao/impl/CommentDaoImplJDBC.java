package com.eden.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eden.dao.CommentDao;
import com.eden.domain.Comment;
import com.eden.domain.CommentPage;
import com.eden.util.DBHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


//d=data  a=access o=object
public class CommentDaoImplJDBC implements CommentDao{

	public List<Comment> getAllComments() {
		List<Comment> list = new ArrayList<Comment>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			st = (PreparedStatement) conn.prepareStatement("select * from comment");
			rs = st.executeQuery();
			while(rs.next()){
				
				String CONTENT_ID = rs.getString("CONTENT_ID");
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
				String COMMENT_CONTENT = rs.getString("COMMENT_CONTENT");
				String COMMENT_TITLE = rs.getString("COMMENT_TITLE");
				int COMMENT_REPLY = rs.getInt("COMMENT_REPLY");
				Comment c = new Comment(CONTENT_ID, USER_ACCOUNT, COMMENT_CONTENT, COMMENT_TITLE, COMMENT_REPLY);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(conn, st, rs);
		}
		return list;
	}

	public boolean deleteCommentBycommentId(String commentId) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("delete from comment where COMMENT_ID=?");
			pst.setString(1, commentId);
			int count = pst.executeUpdate();
			if(count > 0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(conn, pst, null);
		}
		return false;
	}

	public Comment getCommentBycommentId(String commentId) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("select * from comment where COMMENT_ID=?");
			pst.setString(1, commentId);
			rs = pst.executeQuery();
			if(rs.next()){
				String COMMENT_ID = rs.getString("COMMENT_ID");
				String CONTENT_ID = rs.getString("CONTENT_ID");
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
				String COMMENT_CONTENT = rs.getString("COMMENT_CONTENT");
				String COMMENT_TITLE = rs.getString("COMMENT_TITLE");
				int COMMENT_REPLY = rs.getInt("COMMENT_REPLY");
				Comment c = new Comment(CONTENT_ID, USER_ACCOUNT, COMMENT_CONTENT, COMMENT_TITLE, COMMENT_REPLY);
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(conn, pst, rs);
		}
		return null;
	}
	
	public int getPage(int pageSize,String searchContent) {
		Connection conn =null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			if(searchContent == null){
				pst = (PreparedStatement) conn.prepareStatement("select count(*) from comment");
			}else{
				pst = (PreparedStatement) conn.prepareStatement("select count(*) from comment where COMMENT_ID like ?");
				pst.setString(1, "%"+searchContent+"%");
			}
			rs = pst.executeQuery();
			if(rs.next()){
				int total = rs.getInt(1);
				//total=9  pageSize=3
				return (total + pageSize - 1) / pageSize;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(conn, pst, rs);
		}
		return 0;
	}
	public CommentPage getCommentByPage(int currentPage,int pageSize,String searchContent) {
		List<Comment> list = new ArrayList<Comment>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int totalPage = getPage(pageSize,searchContent);
		try {
			conn = DBHelper.getConnection();
			int start = (currentPage - 1) * pageSize;
			if(searchContent == null){
				pst = (PreparedStatement) conn.prepareStatement("select * from comment limit ?,?");
				pst.setInt(1, start);
				pst.setInt(2, pageSize);
			}else{
				pst = (PreparedStatement) conn.prepareStatement("select * from comment where COMMENT_ID like ? limit ?,?");
				pst.setString(1, "%"+searchContent+"%");
				pst.setInt(2, start);
				pst.setInt(3, pageSize);
			}
			rs = pst.executeQuery();
			while(rs.next()){
				String COMMENT_ID = rs.getString("COMMENT_ID");
				String CONTENT_ID = rs.getString("CONTENT_ID");
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
				String COMMENT_CONTENT = rs.getString("COMMENT_CONTENT");
				String COMMENT_TITLE = rs.getString("COMMENT_TITLE");
				int COMMENT_REPLY = rs.getInt("COMMENT_REPLY");
				Comment c = new Comment(CONTENT_ID, USER_ACCOUNT, COMMENT_CONTENT, COMMENT_TITLE, COMMENT_REPLY);
				list.add(c);
			}
			CommentPage page = new CommentPage(list, totalPage, currentPage);
			return page;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(conn, pst, rs);
		}
		return null;
	}

	
}
