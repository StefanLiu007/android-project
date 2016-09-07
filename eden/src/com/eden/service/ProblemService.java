package com.eden.service;

import java.util.List;

import com.eden.domain.Problem;
import com.eden.domain.ProblemPage;

public interface ProblemService {
	public List<Problem> ShowAllExpert();
	public Problem getProblemByID(String id) ;
	public ProblemPage getProblemByPage(int currentPage,int pageSize,String searchContent);
	public boolean deleteProblemByID(String id);
}
