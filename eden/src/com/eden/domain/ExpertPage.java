package com.eden.domain;

import java.util.List;

public class ExpertPage {
	private List<Expert> data;
	private int totalPage;
	private int currentPage;
	public List<Expert> getData() {
		return data;
	}
	public void setData(List<Expert> data) {
		this.data = data;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public ExpertPage(List<Expert> data, int totalPage, int currentPage) {
		super();
		this.data = data;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
	}
}
