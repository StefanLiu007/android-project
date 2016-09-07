package com.eden.service.impl;

import java.util.List;

import com.eden.dao.ManagerDao;
import com.eden.dao.impl.ManagerDaoImpl;
import com.eden.domain.Manager;
import com.eden.domain.PageBean;
import com.eden.service.ManagerService;




public class ManagerServiceImpl implements ManagerService{
	private ManagerDao dao;
	

	public ManagerServiceImpl() {
		dao = new ManagerDaoImpl();
	}
	@Override
	public Manager findByAccountAndPwd(String account, String pwd) throws Exception {
		
		return dao.findByAccountAndPwd(account, pwd);
	}

	@Override
	public PageBean<Manager> findByPage(int currentPage, int pageSize) throws Exception {
		
		return dao.findByPage(currentPage, pageSize);
	}
	@Override
	public int deleteById(int id) throws Exception {
	
		return dao.deleteById(id);
	}
	@Override
	public int addManager(Manager manager) throws Exception {
	
		return dao.addManager(manager);
	}
	@Override
	public Manager findManagerById(int id) throws Exception {
		
		return dao.findManagerById(id);
	}
	@Override
	public int updateManager(Manager manager) throws Exception {
		
		return dao.updateManager(manager);
	}

	
}
