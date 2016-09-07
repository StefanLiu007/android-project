package com.eden.service;

import java.util.List;
import com.eden.domain.Comment;
import com.eden.domain.CommentPage;

public interface CommentService {
	public List<Comment> getAllComments();
	public Comment getCommentBycommentId(String commentId) ;
	public CommentPage getCommentByPage(int currentPage,int pageSize,String searchContent);
	public boolean deleteCommentBycommentId(String commentId);
}
