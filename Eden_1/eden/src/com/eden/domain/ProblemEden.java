package com.eden.domain;

import java.io.Serializable;

public class ProblemEden implements Serializable{
	private Problem problem;
	private User use;
	
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public User getUse() {
		return use;
	}
	public void setUse(User use) {
		this.use = use;
	}
	public ProblemEden(Problem problem, User use) {
		super();
		this.problem = problem;
		this.use = use;
	}
	
	

}
