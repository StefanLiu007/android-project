package com.eden.domain;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {

	private int currentPage;
	private int pageSize;
	private int totalRows;
	private int totalPages;
	private List<T> data = new ArrayList<T>();
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalRows%pageSize==0 ? totalRows/pageSize : totalRows/pageSize +1;
	}
	/*public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}*/
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows){
		this.totalRows = totalRows;
	}
	
}
