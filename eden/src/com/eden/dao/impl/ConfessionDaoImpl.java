package com.eden.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.eden.dao.ConfessionDao;
import com.eden.domain.Confession;
import com.eden.domain.Manager;
import com.eden.domain.PageBean;
import com.eden.util.DBHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ConfessionDaoImpl implements ConfessionDao{

	@Override
	public int deleteById(int id) throws Exception {
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "delete from confession where ID=?";
        st = (PreparedStatement) con.prepareStatement(sql);
        st.setInt(1, id);
        int a = st.executeUpdate();
        DBHelper.closeAll(con, st, rs);
		return a;
	}

	@Override
	public ConfessionDao searchByTime(String time) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfessionDao searchByUserAccount(String account) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("resource")
	@Override
	public PageBean<Confession> findByPage(int currentPage, int pageSize,String searchContent) throws Exception {
		PageBean<Confession> pb = new PageBean<Confession>();
		List<Confession> data = new ArrayList<Confession>();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        if(searchContent == null){
        	  String sql = "select count(*) from view_confession";
        	  st =  (PreparedStatement) con.prepareStatement(sql);
        }else{
        	String s = new String(searchContent.getBytes("ISO-8859-1"), "utf-8");
        	st = (PreparedStatement) con.prepareStatement("select count(*) from view_confession where USER_NAME like ?");
        	st.setString(1, "%"+s+"%");
        }
		rs = st.executeQuery();
		if(rs.next()){
			int total=rs.getInt(1);
			pb.setTotalRows(total);
		}
		//数据集合
		if(searchContent==null){
			String dataSQL = "SELECT * FROM view_confession limit ?,?";
			st = (PreparedStatement) con.prepareStatement(dataSQL);
			st.setInt(1, (currentPage-1)*pageSize);
			st.setInt(2, pageSize);
		}else{
			String s = new String(searchContent.getBytes("ISO-8859-1"), "utf-8");
			String dataSQL = "SELECT * FROM view_confession where USER_NAME like ? limit ?,?";
			st = (PreparedStatement) con.prepareStatement(dataSQL);
			st.setString(1, "%"+s+"%");
			st.setInt(2, (currentPage-1)*pageSize);
			st.setInt(3, pageSize);
		}
		rs = st.executeQuery();
		while(rs.next()){
			int id = rs.getInt("ID");
			String name = rs.getString("USER_NAME");
			String content = rs.getString("CONFESSION_CONTENT");
		    String time = rs.getString("CONFESSION_LASTTIME");
		    int num = rs.getInt("CONFESSION_NUM");
			Confession m = new Confession(id, name, content, time, num);
			data.add(m);
		}
		pb.setData(data);
		DBHelper.closeAll(con, st, rs);
		return pb;
	}

}
