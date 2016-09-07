package com.eden.dao;

import com.eden.domain.Manager;
import com.eden.domain.PageBean;

public interface ManagerDao {
	 public Manager findByAccountAndPwd(String account,String pwd) throws Exception;
		//分页查询
		 public PageBean<Manager> findByPage(int currentPage,int pageSize) throws Exception;
		 //删除管理员
		 public int deleteById(int id) throws Exception;
		 public int addManager(Manager manager) throws Exception;
		 public Manager findManagerById(int id) throws Exception;
		 public int updateManager(Manager manager) throws Exception;
}
