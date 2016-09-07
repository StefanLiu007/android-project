package com.eden.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.eden.dao.ProblemDao;
import com.eden.domain.Problem;
import com.eden.domain.ProblemPage;
import com.eden.util.DBHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ProblemDaoImpl implements ProblemDao {

	@Override
	public List<Problem> ShowAllProblem() {
			List<Problem> list=new ArrayList<>();
			Connection conn = null;
			PreparedStatement pst=null;
			ResultSet rs=null;
			try {
				conn=DBHelper.getConnection();
				pst=(PreparedStatement) conn.prepareStatement("select * from problem");
				rs=pst.executeQuery();
				while (rs.next()) {
					String id=rs.getString("PROBLEM_ID");
					String account=rs.getString("USER_ACCOUNT");
					String content=rs.getString("PROBLEM_CONTENT");
					String image=rs.getString("PROBLEM_IMAGE");
					String lasttime=rs.getString("PROBLEM_LASTTIME");
					int num=rs.getInt("PROBLEM_ANSWERNUM");
					Problem p=new Problem(id, account, content, image, lasttime, num);
					list.add(p);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBHelper.closeAll(conn, pst, rs);
			}
			return list;
		}
	public int getPage(int pageSize,String searchContent) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			if(searchContent == null){
				pst = (PreparedStatement) conn.prepareStatement("select count(*) from problem");
			}else{
				pst = (PreparedStatement) conn.prepareStatement("select count(*) from problem where PROBLEM_ID like ?");
				pst.setString(1, "%"+searchContent+"%");
			}
			rs = pst.executeQuery();
			if(rs.next()){
				int total = rs.getInt(1);
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

	@Override
	public ProblemPage getProblemByPage(int currentPage, int pageSize, String searchContent) {
		List<Problem> list=new ArrayList<>();
		Connection conn = null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			conn=DBHelper.getConnection();
		int totalPage=getPage(pageSize, searchContent);
		int start=(currentPage-1)*pageSize;
		if (searchContent==null) {
				pst=(PreparedStatement) conn.prepareStatement("select * from problem limit ?,?");
				pst.setInt(1, start);
				pst.setInt(2, pageSize);
			}else {
				pst = (PreparedStatement) conn.prepareStatement("select * from problem where PROBLEM_ID like ? limit ?,?");
				pst.setString(1, "%"+searchContent+"%");
				pst.setInt(2, start);
				pst.setInt(3, pageSize);
			}
			rs=pst.executeQuery();
			while (rs.next()) {
				String PROBLEM_ID=rs.getString("PROBLEM_ID");
				String USER_ACCOUNT=rs.getString("USER_ACCOUNT");
				String PROBLEM_CONTENT=rs.getString("PROBLEM_CONTENT");
				String PROBLEM_IMAGE=rs.getString("PROBLEM_IMAGE");
				String PROBLEM_LASTTIME=rs.getString("PROBLEM_LASTTIME");
				int PROBLEM_ANSWERNUM=rs.getInt("PROBLEM_ANSWERNUM");
				Problem p=new Problem(PROBLEM_ID, USER_ACCOUNT, PROBLEM_CONTENT,PROBLEM_IMAGE, PROBLEM_LASTTIME, PROBLEM_ANSWERNUM);
				list.add(p);
			}
			ProblemPage page =new ProblemPage(list, totalPage, currentPage);
			return page;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBHelper.closeAll(conn, pst, rs);
			}
			return null;
	}



	@Override
	public Problem getProblemByID(String id) {
			Connection conn = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			try {
				conn = DBHelper.getConnection();
				pst = (PreparedStatement) conn.prepareStatement("select * from problem where PROBLEM_ID=?");
				pst.setString(1, id	);
				rs = pst.executeQuery();
				if(rs.next()){
					String PROBLEM_ID=rs.getString("PROBLEM_ID");
					String USER_ACCOUNT=rs.getString("USER_ACCOUNT");
					String PROBLEM_CONTENT=rs.getString("PROBLEM_CONTENT");
					String PROBLEM_IMAGE=rs.getString("PROBLEM_IMAGE");
					String PROBLEM_LASTTIME=rs.getString("PROBLEM_LASTTIME");
					int PROBLEM_ANSWERNUM=rs.getInt("PROBLEM_ANSWERNUM");
					Problem p=new Problem(PROBLEM_ID, USER_ACCOUNT,  PROBLEM_CONTENT, PROBLEM_IMAGE,PROBLEM_LASTTIME, PROBLEM_ANSWERNUM);
					return p;
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

	@Override
	public boolean deleteProblemByID(String id) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("delete from problem where PROBLEM_ID=?");
			pst.setString(1, id);
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
}
