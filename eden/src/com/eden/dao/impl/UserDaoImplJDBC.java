package com.eden.dao.impl;




import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.eden.dao.UserDao;
import com.eden.domain.Confession;
import com.eden.domain.PageBean;
import com.eden.domain.User;
import com.eden.util.DBHelper;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class UserDaoImplJDBC implements UserDao {
	public List<User> getAllUsers(){
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.createStatement();
			rs = pst.executeQuery("select * from user");
			while (rs.next()) {
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
				String USER_NICKNAME = rs.getString("USER_NICKNAME");
				String USER_PASSWORD = rs.getString("USER_PASSWORD");
				String USER_NAME = rs.getString("USER_NAME");
				String USER_SIGNATURE = rs.getString("USER_SIGNATURE");
				String USER_BIRTHDATE = rs.getString("USER_BIRTHDATE");
				String USER_SEX = rs.getString("USER_SEX");
				String USER_SCHOOL = rs.getString("USER_SCHOOL");
				String USER_MOBILE = rs.getString("USER_MOBILE");
				String USER_EMAIL = rs.getString("USER_EMAIL");
				String USER_ICON = rs.getString("USER_ICON");
				User u = new User(USER_ACCOUNT,USER_NICKNAME,USER_PASSWORD,USER_NAME,USER_SIGNATURE,
						USER_BIRTHDATE,USER_SEX,USER_SCHOOL,USER_MOBILE,USER_EMAIL,USER_ICON);
				list.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, pst, rs);
		}
		return list;
	}

	@Override
	public User getUserByUSER_ACCOUNT(String userAccount) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("select * from user where USER_ACCOUNT=?");
			pst.setString(1, userAccount);
			rs = pst.executeQuery();
			if(rs.next()){
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
				String USER_NICKNAME = rs.getString("USER_NICKNAME");
				String USER_PASSWORD = rs.getString("USER_PASSWORD");
				String USER_NAME = rs.getString("USER_NAME");
				String USER_SIGNATURE = rs.getString("USER_SIGNATURE");
				String USER_BIRTHDATE = rs.getString("USER_BIRTHDATE");
				String USER_SEX = rs.getString("USER_SEX");
				String USER_SCHOOL = rs.getString("USER_SCHOOL");
				String USER_MOBILE = rs.getString("USER_MOBILE");
				String USER_EMAIL = rs.getString("USER_EMAIL");
				String USER_ICON = rs.getString("USER_ICON");
				User u = new User(USER_ACCOUNT,USER_NICKNAME,USER_PASSWORD,USER_NAME,USER_SIGNATURE,
						USER_BIRTHDATE,USER_SEX,USER_SCHOOL,USER_MOBILE,USER_EMAIL,USER_ICON);	
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBHelper.closeAll(conn, pst, rs);;
		}
		return null;
	}

	@SuppressWarnings("resource")
	@Override
	public PageBean<User> getUserByPage(int currentPage, int pageSize, String s) throws Exception{
		PageBean<User> pb = new PageBean<User>();
		List<User> data = new ArrayList<User>();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		Connection con = DBHelper.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        if(s == null){
        	  String sql = "select count(*) from user";
        	  st =  (PreparedStatement) con.prepareStatement(sql);
        }else{
        	String searchContent = new String(s.getBytes("ISO-8859-1"), "utf-8");
        	st = (PreparedStatement) con.prepareStatement("select count(*) from user where USER_ACCOUNT like ?");
        	st.setString(1, "%"+searchContent+"%");
        }
		rs = st.executeQuery();
		if(rs.next()){
			int total=rs.getInt(1);
			pb.setTotalRows(total);
		}
		//数据集合
		if(s==null){
			String dataSQL = "SELECT * FROM user limit ?,?";
			st = (PreparedStatement) con.prepareStatement(dataSQL);
			st.setInt(1, (currentPage-1)*pageSize);
			st.setInt(2, pageSize);
		}else{
			String searchContent = new String(s.getBytes("ISO-8859-1"), "utf-8");
			String dataSQL = "select * from user where USER_ACCOUNT like ? limit ?,?";
			st = (PreparedStatement) con.prepareStatement(dataSQL);
			st.setString(1, "%"+searchContent+"%");
			st.setInt(2, (currentPage-1)*pageSize);
			st.setInt(3, pageSize);
		}
		rs = st.executeQuery();
		while(rs.next()){
			String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
			String USER_NICKNAME = rs.getString("USER_NICKNAME");
			String USER_PASSWORD = rs.getString("USER_PASSWORD");
			String USER_NAME = rs.getString("USER_NAME");
			String USER_SIGNATURE = rs.getString("USER_SIGNATURE");
			String USER_BIRTHDATE = rs.getString("USER_BIRTHDATE");
			String USER_SEX = rs.getString("USER_SEX");
			String USER_SCHOOL = rs.getString("USER_SCHOOL");
			String USER_MOBILE = rs.getString("USER_MOBILE");
			String USER_EMAIL = rs.getString("USER_EMAIL");
			String USER_ICON = rs.getString("USER_ICON");
			User u = new User(USER_ACCOUNT,USER_NICKNAME,USER_PASSWORD,USER_NAME,USER_SIGNATURE,
					USER_BIRTHDATE,USER_SEX,USER_SCHOOL,USER_MOBILE,USER_EMAIL,USER_ICON);
			data.add(u);
		}
		pb.setData(data);
		DBHelper.closeAll(con, st, rs);
		return pb;
	}



	
	@Override
	public boolean deleteUserByUSER_ACCOUNT(String userAccount) {
		// TODO Auto-generated method stub
		Connection conn=null;
		CallableStatement call = null;
		
		try {
			conn = DBHelper.getConnection();
			call = (CallableStatement) conn.prepareCall("CALL pro_user(?)");
			call.setString(1, userAccount);
			int a = call.executeUpdate();
			if(a>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBHelper.closeAll(conn, call, null);
		}
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		Connection conn =null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			System.out.println("-----"+user.getUserAccount());
			pst = (PreparedStatement) conn.prepareStatement("update user set USER_NICKNAME=?,USER_PASSWORD=?,USER_NAME=?,USER_SIGNATURE=?,USER_BIRTHDATE=? ,USER_SEX=?,USER_SCHOOL=?,USER_MOBILE=?,USER_EMAIL=?,USER_ICON=? where USER_ACCOUNT=?");
			pst.setString(1, user.getUserNickname());
			pst.setString(2, user.getUserPassword());
			pst.setString(3, user.getUserName());
			pst.setString(4, user.getUserSignature());
			pst.setString(5, user.getUserBirthday());
			pst.setString(6, user.getUserSex());
			pst.setString(7, user.getUserSchool());
			pst.setString(8, user.getUserMobile());
			pst.setString(9, user.getUserMail());
			pst.setString(10, user.getUserIcon());
			pst.setString(11, user.getUserAccount());
			int count = pst.executeUpdate();
			System.out.println("count="+count);
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
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			pst = (PreparedStatement) conn.prepareStatement("insert into user(USER_ACCOUNT,USER_NICKNAME,USER_PASSWORD,USER_NAME,USER_SIGNATURE,USER_BIRTHDATE,USER_SEX,USER_SCHOOL,USER_MOBILE,USER_EMAIL,USER_ICON)values(?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, user.getUserAccount());
			pst.setString(2, user.getUserNickname());
			pst.setString(3, user.getUserPassword());
			pst.setString(4, user.getUserName());
			pst.setString(5, user.getUserSignature());
			pst.setString(6, user.getUserBirthday());
			pst.setString(7, user.getUserSex());
			pst.setString(8, user.getUserSchool());
			pst.setString(9, user.getUserMobile());
			pst.setString(10, user.getUserMail());
			pst.setString(11, user.getUserIcon());
			int count = pst.executeUpdate();
			if (count>0) {
				return true;
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
	public User loginSuccess(String account,String password) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs;
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from user where USER_ACCOUNT=? and USER_PASSWORD=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()){
				String USER_ACCOUNT = rs.getString("USER_ACCOUNT");
				String USER_NICKNAME = rs.getString("USER_NICKNAME");
				String USER_PASSWORD = rs.getString("USER_PASSWORD");
				String USER_NAME = rs.getString("USER_NAME");
				String USER_SIGNATURE = rs.getString("USER_SIGNATURE");
				String USER_BIRTHDATE = rs.getString("USER_BIRTHDATE");
				String USER_SEX = rs.getString("USER_SEX");
				String USER_SCHOOL = rs.getString("USER_SCHOOL");
				String USER_MOBILE = rs.getString("USER_MOBILE");
				String USER_EMAIL = rs.getString("USER_EMAIL");
				String USER_ICON = rs.getString("USER_ICON");
				User u = new User(USER_ACCOUNT,USER_NICKNAME,USER_PASSWORD,USER_NAME,USER_SIGNATURE,
						USER_BIRTHDATE,USER_SEX,USER_SCHOOL,USER_MOBILE,USER_EMAIL,USER_ICON);
				return u;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean UpdateImage(String account, String url) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pst = null;
		int rs;
		try {
			conn = DBHelper.getConnection();
			String sql = "update user set USER_ICON=? where USER_ACCOUNT=?";
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(2, account);
			pst.setString(1, url);
			rs = pst.executeUpdate();
			if (rs>0) {
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}

}
