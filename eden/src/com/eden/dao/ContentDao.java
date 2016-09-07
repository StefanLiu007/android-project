package com.eden.dao;

import com.eden.domain.Content;
import com.eden.domain.ContentPage;

public interface ContentDao {
	public ContentPage getContents(int currentPage,int pageSize,String searchContent);
	public boolean addContent(Content content);
	public boolean updateContent(Content content);
	public boolean deleteContent(String contentId);
    public Content getContent(String contentId);
    public int getContentCount(int pageSize ,String searchContent);
}
