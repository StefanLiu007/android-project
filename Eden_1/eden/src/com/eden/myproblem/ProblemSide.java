package com.eden.myproblem;

import com.eden.collection.SlideView;
import com.eden.domain.Problem;
import com.eden.domain.ProblemEden;

public class ProblemSide {
	private ProblemEden problem;
	private SlideView slideView;
	public ProblemSide() {
		super();
	}
	public ProblemSide(ProblemEden problem, SlideView slideView) {
		super();
		this.problem = problem;
		this.slideView = slideView;
	}
	public ProblemEden getProblem() {
		return problem;
	}
	public void setProblem(ProblemEden problem) {
		this.problem = problem;
	}
	public SlideView getSlideView() {
		return slideView;
	}
	public void setSlideView(SlideView slideView) {
		this.slideView = slideView;
	}


}
