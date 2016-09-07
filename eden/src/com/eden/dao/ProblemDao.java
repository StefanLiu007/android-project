package com.eden.dao;

import java.util.List;
import com.eden.domain.Problem;
import com.eden.domain.ProblemPage;
public interface ProblemDao {
	public List<Problem> ShowAllProblem();
	public Problem getProblemByID(String id) ;
	public ProblemPage getProblemByPage(int currentPage,int pageSize,String searchContent);
	public boolean deleteProblemByID(String id);
}
