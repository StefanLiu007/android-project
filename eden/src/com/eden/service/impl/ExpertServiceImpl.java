package com.eden.service.impl;

import java.util.List;

import com.eden.dao.ExpertDao;
import com.eden.dao.impl.ExpertDaoImpl;
import com.eden.domain.Expert;
import com.eden.domain.ExpertPage;
import com.eden.service.ExpertService;

public class ExpertServiceImpl implements ExpertService {
	private ExpertDao dao=new ExpertDaoImpl();
	@Override
	public List<Expert> ShowAllExpert() {
		// TODO Auto-generated method stub
		return dao.ShowAllExpert();
	}
	@Override
	public Expert getExpertByAcco(String acco) {
		// TODO Auto-generated method stub
		return dao.getExpertByAcco(acco);
	}
	@Override
	public ExpertPage getExpertByPage(int currentPage, int pageSize, String searchContent) {
		// TODO Auto-generated method stub
		return dao.getExpertByPage(currentPage, pageSize, searchContent);
	}
	@Override
	public boolean deleteExpertByAcco(String acco) {
		// TODO Auto-generated method stub
		return dao.deleteExpertByAcco(acco);
	}
	@Override
	public boolean updateExpert(Expert expert) {
		// TODO Auto-generated method stub
		return dao.updateExpert(expert);
	}
	@Override
	public boolean addExpert(Expert expert) {
		// TODO Auto-generated method stub
		return dao.addExpert(expert);
	}

}
