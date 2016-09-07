package com.eden.service;

import java.util.List;

import com.eden.domain.Expert;
import com.eden.domain.ExpertPage;



public interface ExpertService {
	public List<Expert> ShowAllExpert();
	public Expert getExpertByAcco(String acco) ;
	public ExpertPage getExpertByPage(int currentPage,int pageSize,String searchContent);
	public boolean deleteExpertByAcco(String acco);
	public boolean updateExpert(Expert stud) ;
	public boolean addExpert(Expert expert);
}
