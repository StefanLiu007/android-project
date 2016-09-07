package com.eden.service;
import java.util.List;

import com.eden.domain.*;

public interface ManagerService {
	PageBean<Manager> findByPage(int currentPage,int pageSize) throws Exception;
	public Manager findByAccountAndPwd(String account,String pwd) throws Exception;
	public int deleteById(int id) throws Exception;
	public int addManager(Manager manager) throws Exception;
	public Manager findManagerById(int id) throws Exception;
	 public int updateManager(Manager manager) throws Exception;
}
