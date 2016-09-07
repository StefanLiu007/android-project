package com.eden.dao.impl;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eden.dao.ExpertDao;
import com.eden.domain.Expert;
import com.eden.domain.ExpertPage;
import com.eden.util.DBHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ExpertDaoImpl implements ExpertDao {

	@Override
	public List<Expert> ShowAllExpert() {
		List<Expert> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			conn=DBHelper.getConnection();
			pst=(PreparedStatement) conn.prepareStatement("select * from expert");
			rs=pst.executeQuery();
			while (rs.next()) {
				String account=rs.getString("EXPERT_ACCOUNT");
				String name=rs.getString("EXPERT_NAME");
				String sex=rs.getString("EXPERT_SEX");
				String state=rs.getString("EXPERT_STATE");
				String introduce=rs.getString("EXPERT_INTRODUCE");
				int pv=rs.getInt("EXPERT_PV");
				String address=rs.getString("EXPERT_ADDRESS");
				String icon=rs.getString("ERTERT_ICON");
				String email=rs.getString("ERTERT_EMAIL");
				Expert e=new Expert(account, name, sex, state, introduce, pv, address, icon, email);
				list.add(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			DBHelper.closeAll(conn, pst, rs);
		}
		return list;
	}

	@Override
	public Expert getExpertByAcco(String acco) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("select * from expert where EXPERT_ACCOUNT=?");
			pst.setString(1, acco);
			rs = pst.executeQuery();
			if(rs.next()){
				String EXPERT_ACCOUNT=rs.getString("EXPERT_ACCOUNT");
				String EXPERT_NAME = rs.getString("EXPERT_NAME");
				String EXPERT_SEX = rs.getString("EXPERT_SEX");
				String EXPERT_STATE = rs.getString("EXPERT_STATE");
				String EXPERT_INTRODUCE = rs.getString("EXPERT_INTRODUCE");
				int EXPERT_PV = rs.getInt("EXPERT_PV");
				String EXPERT_ADDRESS = rs.getString("EXPERT_ADDRESS");
				String ERTERT_ICON = rs.getString("ERTERT_ICON");
				String ERTERT_EMAIL = rs.getString("ERTERT_EMAIL");
				Expert s=new Expert(EXPERT_ACCOUNT, EXPERT_NAME, EXPERT_SEX, EXPERT_STATE, EXPERT_INTRODUCE, EXPERT_PV, EXPERT_ADDRESS, ERTERT_ICON, ERTERT_EMAIL);
				return s;
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
				pst = (PreparedStatement) conn.prepareStatement("select count(*) from expert");
			}else{
				pst = (PreparedStatement) conn.prepareStatement("select count(*) from expert where EXPERT_NAME like ?");
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
	public ExpertPage getExpertByPage(int currentPage, int pageSize, String searchContent) {
		List<Expert> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			conn=DBHelper.getConnection();
		int totalPage=getPage(pageSize, searchContent);
		int start=(currentPage-1)*pageSize;
		if (searchContent==null) {
				pst=(PreparedStatement) conn.prepareStatement("select * from expert limit ?,?");
				pst.setInt(1, start);
				pst.setInt(2, pageSize);
			}else {
				pst = (PreparedStatement) conn.prepareStatement("select * from expert where EXPERT_ACCOUNT like ? limit ?,?");
				pst.setString(1, "%"+searchContent+"%");
				pst.setInt(2, start);
				pst.setInt(3, pageSize);
			}
			rs=pst.executeQuery();
			while (rs.next()) {
				String account=rs.getString("EXPERT_ACCOUNT");
				String name=rs.getString("EXPERT_NAME");
				String sex=rs.getString("EXPERT_SEX");
				String state=rs.getString("EXPERT_STATE");
				String introduce=rs.getString("EXPERT_INTRODUCE");
				int pv=rs.getInt("EXPERT_PV");
				String address=rs.getString("EXPERT_ADDRESS");
				String icon=rs.getString("ERTERT_ICON");
				String email=rs.getString("ERTERT_EMAIL");
				Expert e=new Expert(account, name, sex, state, introduce, pv, address, icon, email);
				list.add(e);
			}
			ExpertPage page =new ExpertPage(list, totalPage, currentPage);
			return page;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				DBHelper.closeAll(conn, pst, rs);
			}
			return null;
	}

	@Override
	public boolean deleteExpertByAcco(String acco) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("delete from expert where EXPERT_ACCOUNT=?");
			pst.setString(1, acco);
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

	@Override
	public boolean updateExpert(Expert expert) {
		Connection conn=null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("update expert set EXPERT_NAME=?,EXPERT_SEX=?,EXPERT_STATE=?,EXPERT_INTRODUCE=?,EXPERT_PV=?,EXPERT_ADDRESS=?,ERTERT_ICON=?,ERTERT_EMAIL=? where EXPERT_ACCOUNT=?");
			pst.setString(1, expert.getExpertName());
			pst.setString(2, expert.getExpertSex());
			pst.setString(3, expert.getExpertState());
			pst.setString(4, expert.getExpertIntroduce());
			pst.setInt(5, expert.getExpertPv());
			pst.setString(6, expert.getExpertAddress());
			pst.setString(7, expert.getExpertIcon());
			pst.setString(8, expert.getExpertEmail());
			pst.setString(9, expert.getExpertAccount());
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

	@Override
	public boolean addExpert(Expert expert) {
		List<Expert> list =new ArrayList<>();
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("insert into expert(EXPERT_ACCOUNT,EXPERT_NAME,EXPERT_SEX,EXPERT_STATE,EXPERT_INTRODUCE,EXPERT_PV,EXPERT_ADDRESS,ERTERT_ICON,ERTERT_EMAIL) value(?,?,?,?,?,?,?,?,?)");
			pst.setString(1, expert.getExpertAccount());
			pst.setString(2, expert.getExpertName());
			pst.setString(3, expert.getExpertSex());
			pst.setString(4, expert.getExpertState());
			pst.setString(5, expert.getExpertIntroduce());
			pst.setInt(6, expert.getExpertPv());
			pst.setString(7, expert.getExpertAddress());
			pst.setString(8, expert.getExpertIcon());
			pst.setString(9, expert.getExpertEmail());
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
