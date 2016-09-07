package com.eden.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eden.dao.ManagerDao;
import com.eden.domain.Manager;
import com.eden.domain.PageBean;
import com.eden.util.DBHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class ManagerDaoImpl implements ManagerDao{

	@Override
	public Manager findByAccountAndPwd(String account, String pwd) throws Exception {
		String sql = "select * from manager where ACCOUNT=? and PWD=? ";
        Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        st =  (PreparedStatement) con.prepareStatement(sql);
        st.setString(1, account);
        st.setString(2, pwd);
        rs = st.executeQuery();
        if(rs.next()){
        	int id = rs.getInt("ID");
        	String maccount = rs.getString("ACCOUNT");
        	String name = rs.getString("MNAME");
        	String mpwd = rs.getString("PWD");
        	Manager m = new Manager(id,maccount, name, mpwd);
        	DBHelper.closeAll(con, st, rs);
        	return m;
        }
		return null;
	}

	@Override
	public PageBean<Manager> findByPage(int currentPage, int pageSize) throws Exception {
		PageBean<Manager> pb = new PageBean<Manager>();
		List<Manager> data = new ArrayList<Manager>();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "select count(*) from manager";
        st =  (PreparedStatement) con.prepareStatement(sql);
		rs = st.executeQuery();
		if(rs.next()){
			int total=rs.getInt(1);
			pb.setTotalRows(total);
		}
		//数据集合
		String dataSQL = "select * from manager limit ?,?";
		st = (PreparedStatement) con.prepareStatement(dataSQL);
		st.setInt(1, (currentPage-1)*pageSize);
		st.setInt(2, pageSize);
		rs = st.executeQuery();
		while(rs.next()){
			int id = rs.getInt("ID");
			System.out.println(id+".....");
			String name = rs.getString("MNAME");
			String account = rs.getString("ACCOUNT");
			Manager m = new Manager(id,account,name);
			data.add(m);
		}
		pb.setData(data);
		DBHelper.closeAll(con, st, rs);
		return pb;
	}

	@Override
	public int deleteById(int id) throws Exception {
		String sql = "delete  from manager where id=?";
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        st = (PreparedStatement) con.prepareStatement(sql);
        st.setInt(1, id);
        int a = st.executeUpdate();
        DBHelper.closeAll(con, st, rs);
		return a;
	}

	@Override
	public int addManager(Manager manager) throws Exception {
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "insert into manager(ACCOUNT,MNAME,PWD) values(?,?,?)";
        st = (PreparedStatement) con.prepareStatement(sql);
        st.setString(1, manager.getAccount());
        st.setString(2, manager.getName());
        st.setString(3, manager.getPwd());
        int a  = st.executeUpdate();
        DBHelper.closeAll(con, st, rs);
		return a;
	}

	@Override
	public Manager findManagerById(int id) throws Exception {
		Manager m=null;
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "select * from manager where ID=?";
        st = (PreparedStatement) con.prepareStatement(sql);
        st.setInt(1, id);
        rs = st.executeQuery();
        if(rs.next()){
        	int mid = rs.getInt("ID");
        	String account = rs.getString("ACCOUNT");
        	String name = rs.getString("MNAME");
        	String pwd = rs.getString("PWD");
        	m = new Manager(mid,account, name, pwd);
        }
        DBHelper.closeAll(con, st, rs);
		return m;
	}

	@Override
	public int updateManager(Manager manager) throws Exception {
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "update manager set ACCOUNT=?,MNAME=?,PWD=? where ID=?";
        st = (PreparedStatement) con.prepareStatement(sql);
        st.setString(1, manager.getAccount());
        st.setString(2, manager.getName());
        st.setString(3, manager.getPwd());
        st.setInt(4, manager.getId());
        int a = st.executeUpdate();
		return a;
	}

}
