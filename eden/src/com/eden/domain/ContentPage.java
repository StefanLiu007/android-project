package com.eden.domain;

import java.util.List;

public class ContentPage {
	private List<Content> contents;
	private int currentPage;
	private int totalPage;

	public ContentPage(List<Content> contents, int currentPage, int totalPage) {
		super();
		this.contents = contents;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


}
