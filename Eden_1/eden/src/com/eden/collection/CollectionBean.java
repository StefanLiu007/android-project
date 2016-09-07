package com.eden.collection;

import java.util.List;

import com.eden.domain.Content;
import com.eden.domain.Problem;

public class CollectionBean {
	private List<CollectionContent> contents;
	private List<CollectionProblem> problemEdens;
	public CollectionBean() {
		super();
	}
	public List<CollectionContent> getContents() {
		return contents;
	}
	public void setContents(List<CollectionContent> contents) {
		this.contents = contents;
	}
	public List<CollectionProblem> getProblemEdens() {
		return problemEdens;
	}
	public void setProblemEdens(List<CollectionProblem> problemEdens) {
		this.problemEdens = problemEdens;
	}
	public CollectionBean(List<CollectionContent> contents, List<CollectionProblem> problemEdens) {
		super();
		this.contents = contents;
		this.problemEdens = problemEdens;
	}
	
	
}
