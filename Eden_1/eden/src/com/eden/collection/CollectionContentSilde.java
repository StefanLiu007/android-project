package com.eden.collection;



public class CollectionContentSilde {
	private CollectionContent collectionContent;
	private SlideView slideView;
	public CollectionContentSilde() {
		super();
	}
	public CollectionContent getCollectionContent() {
		return collectionContent;
	}
	public void setCollectionContent(CollectionContent collectionContent) {
		this.collectionContent = collectionContent;
	}
	public SlideView getSlideView() {
		return slideView;
	}
	public void setSlideView(SlideView slideView) {
		this.slideView = slideView;
	}
	public CollectionContentSilde(CollectionContent collectionContent,
			SlideView slideView) {
		super();
		this.collectionContent = collectionContent;
		this.slideView = slideView;
	}
	public CollectionContentSilde(CollectionContent collectionContent) {
		super();
		this.collectionContent = collectionContent;
	}
	

}
