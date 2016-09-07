package com.eden.service.impl;

import java.util.List;

import com.eden.dao.ProblemDao;
import com.eden.dao.impl.ProblemDaoImpl;
import com.eden.domain.Problem;
import com.eden.domain.ProblemPage;
import com.eden.service.ProblemService;

public class ProblemServiceImpl implements ProblemService {
	ProblemDao pro=new ProblemDaoImpl();

	@Override
	public List<Problem> ShowAllExpert() {
		// TODO Auto-generated method stub
		return pro.ShowAllProblem();
	}

	@Override
	public Problem getProblemByID(String id) {
		// TODO Auto-generated method stub
		return pro.getProblemByID(id);
	}

	@Override
	public ProblemPage getProblemByPage(int currentPage, int pageSize, String searchContent) {
		// TODO Auto-generated method stub
		return pro.getProblemByPage(currentPage, pageSize, searchContent);
	}

	@Override
	public boolean deleteProblemByID(String id) {
		// TODO Auto-generated method stub
		return pro.deleteProblemByID(id);
	}

}
