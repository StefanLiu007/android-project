package com.eden.dao;

import com.eden.domain.Confession;
import com.eden.domain.PageBean;

public interface ConfessionDao {
	public int deleteById(int id) throws Exception;
	public ConfessionDao searchByTime(String time) throws Exception;
	public ConfessionDao searchByUserAccount(String account)throws Exception;
	public PageBean<Confession> findByPage(int currentPage,int pageSize,String searchContent) throws Exception;
	
}
