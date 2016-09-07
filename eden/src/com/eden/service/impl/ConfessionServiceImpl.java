package com.eden.service.impl;

import com.eden.dao.ConfessionDao;
import com.eden.dao.impl.ConfessionDaoImpl;
import com.eden.domain.Confession;
import com.eden.domain.PageBean;
import com.eden.service.ConfessionService;

public class ConfessionServiceImpl implements ConfessionService{
	private ConfessionDao dao;
	 public ConfessionServiceImpl() {
		dao = new ConfessionDaoImpl();
	}

	@Override
	public int deleteById(int id) throws Exception {
		
		return dao.deleteById(id);
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

	@Override
	public PageBean<Confession> findByPage(int currentPage, int pageSize,String searchContent) throws Exception {
		
		return dao.findByPage(currentPage, pageSize,searchContent);
	}

}
