package com.eden.dao;

import java.util.List;

import com.eden.domain.Expert;
import com.eden.domain.ExpertPage;



public interface ExpertDao {
	public List<Expert> ShowAllExpert();
	public Expert getExpertByAcco(String acco) ;
	public ExpertPage getExpertByPage(int currentPage,int pageSize,String searchContent);
	public boolean deleteExpertByAcco(String acco);
	public boolean updateExpert(Expert expert) ;
	public boolean addExpert(Expert expert);
}
