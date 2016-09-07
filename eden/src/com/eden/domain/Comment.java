package com.eden.domain;

import java.io.Serializable;

public class Comment implements Serializable{
	private String commentId;
	private String contentId;
	private String userAccount;
	private String commentContent;
	private String commentTitle;
	private String expertAccount;
	private int commentReply;
	private String time;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	

	
	
	public Comment(String contentId, String userAccount, String commentContent, int commentReply, String time) {
		super();
		this.contentId = contentId;
		this.userAccount = userAccount;
		this.commentContent = commentContent;
		this.commentReply = commentReply;
		this.time = time;
	}

	public String getExpertAccount() {
		return expertAccount;
	}

	public void setExpertAccount(String expertAccount) {
		this.expertAccount = expertAccount;
	}

	public Comment(String account,String commentContent, int commentReply, String time) {
		super();
		this.userAccount = account;
		this.commentContent = commentContent;
		this.commentReply = commentReply;
		this.time = time;
	}

	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentTitle() {
		return commentTitle;
	}
	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}

	public int getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(int commentReply) {
		this.commentReply = commentReply;
	}

	public Comment(String contentId, String userAccount, String commentContent, String commentTitle, int commentReply
			) {
		super();
		this.contentId = contentId;
		this.userAccount = userAccount;
		this.commentContent = commentContent;
		this.commentTitle = commentTitle;
		this.commentReply = commentReply;
		
	}

	public Comment(String commentId, String contentId, String userAccount, String commentContent, String commentTitle,
			int commentReply, String time) {
		super();
		this.commentId = commentId;
		this.contentId = contentId;
		this.userAccount = userAccount;
		this.commentContent = commentContent;
		this.commentTitle = commentTitle;
		this.commentReply = commentReply;
		this.time = time;
	}

	public Comment(String userAccount, String commentContent, String commentTitle, String expertAccount,
			int commentReply, String time) {
		super();
		this.userAccount = userAccount;
		this.commentContent = commentContent;
		this.commentTitle = commentTitle;
		this.expertAccount = expertAccount;
		this.commentReply = commentReply;
		this.time = time;
	}
	

	
	
}
