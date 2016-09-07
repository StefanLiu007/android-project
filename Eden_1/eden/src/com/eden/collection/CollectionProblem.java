package com.eden.collection;

import com.eden.domain.ProblemEden;

public class CollectionProblem {
	private ProblemEden problemEden;
	private String time;
	public ProblemEden getProblemEden() {
		return problemEden;
	}
	public void setProblemEden(ProblemEden problemEden) {
		this.problemEden = problemEden;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public CollectionProblem(ProblemEden problemEden, String time) {
		super();
		this.problemEden = problemEden;
		this.time = time;
	}

}
