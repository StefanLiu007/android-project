package com.eden.service.impl;

import com.eden.dao.ContentDao;
import com.eden.dao.impl.ContentDaoImpl;
import com.eden.domain.Content;
import com.eden.domain.ContentPage;
import com.eden.service.ContentService;

public class ContentServiceImpl implements ContentService {
	private ContentDao contentdao;
	public ContentServiceImpl() {
		contentdao=new ContentDaoImpl();
	}

	@Override
	public ContentPage getContents(int currentPage, int pageSize, String searchContent) {
		// TODO Auto-generated method stub
		return contentdao.getContents(currentPage, pageSize, searchContent);
	}

	@Override
	public boolean addContent(Content content) {
		// TODO Auto-generated method stub
		return contentdao.addContent(content);
	}

	@Override
	public boolean updateContent(Content content) {
		// TODO Auto-generated method stub
		return contentdao.updateContent(content);
	}

	@Override
	public boolean deleteContent(String contentId) {
		// TODO Auto-generated method stub
		return contentdao.deleteContent(contentId);
	}

	@Override
	public Content getContent(String contentId) {
		// TODO Auto-generated method stub
		return contentdao.getContent(contentId);
	}

	@Override
	public int getContentCount(int pageSize, String searchContent) {
		// TODO Auto-generated method stub
		return contentdao.getContentCount(pageSize, searchContent);
	}

}
