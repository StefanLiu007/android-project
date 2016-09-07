package com.eden.service.impl;

import java.util.List;
import com.eden.dao.CommentDao;
import com.eden.dao.impl.CommentDaoImplJDBC;
import com.eden.domain.Comment;
import com.eden.domain.CommentPage;
import com.eden.service.CommentService;


//������ʵ�֣����ܻ���Ƶ�һЩ���ݵĲ�����Ҳ����һЩҵ���߼�����
public class CommentServiceImpl implements CommentService {
	private CommentDao dao;
	
	public CommentServiceImpl(){
		dao = new CommentDaoImplJDBC();
	}
	
	@Override
	public List<Comment> getAllComments() {
		return dao.getAllComments();
	}

	@Override
	public Comment getCommentBycommentId(String commentId) {
		return dao.getCommentBycommentId(commentId);
	}

	@Override
	public CommentPage getCommentByPage(int currentPage, int pageSize,String searchContent) {
		// TODO Auto-generated method stub
		return dao.getCommentByPage(currentPage, pageSize,searchContent);
	}

	@Override
	public boolean deleteCommentBycommentId(String commentId) {
		// TODO Auto-generated method stub
		return dao.deleteCommentBycommentId(commentId);
	}


}
