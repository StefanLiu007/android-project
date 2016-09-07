package com.eden.collection;



public class CollectionProblemSilde {
	private CollectionProblem collectionProblem;
	private SlideView slideView;
	public CollectionProblemSilde() {
		super();
	}
	public CollectionProblemSilde(CollectionProblem collectionProblem,
			SlideView slideView) {
		super();
		this.collectionProblem = collectionProblem;
		this.slideView = slideView;
	}
	public CollectionProblemSilde(CollectionProblem collectionProblem) {
		super();
		this.collectionProblem = collectionProblem;
	}
	public CollectionProblem getCollectionProblem() {
		return collectionProblem;
	}
	public void setCollectionProblem(CollectionProblem collectionProblem) {
		this.collectionProblem = collectionProblem;
	}
	public SlideView getSlideView() {
		return slideView;
	}
	public void setSlideView(SlideView slideView) {
		this.slideView = slideView;
	}
	
}
