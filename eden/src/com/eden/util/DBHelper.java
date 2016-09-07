package com.eden.util;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class DBHelper {
	public static Connection getConnection() throws Exception{
		InputStream in = DBHelper.class.getResourceAsStream("/db.properties");
		Properties pro = new Properties();
		pro.load(in);
		String drivename = pro.getProperty("drivername");
		String name = pro.getProperty("name");
		String url = pro.getProperty("url");
		String password = pro.getProperty("pass");
		Class.forName(drivename);
		Connection con = (Connection) DriverManager.getConnection(url,name,password);
		return con;
		
	}
	public static void closeAll(Connection con,Statement sta,ResultSet res){
		if(res != null){
			try {
				res.close();
				res = null;
			} catch (SQLException e) {
				System.out.println("can not close");
			}
		}
		if(con !=null){
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(sta !=null){
			try {
				sta.close();
				sta = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
