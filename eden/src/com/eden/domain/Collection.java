package com.eden.domain;

public class Collection {
	private String userAccount;
	private String contentId;
	private String content;
	private String time;
	private String problemId;
	
	public Collection(String userAccount, String contentId) {
		super();
		this.userAccount = userAccount;
		this.contentId = contentId;
	}
	

	public Collection(String userAccount, String contentId, String time, String problemId) {
		super();
		this.userAccount = userAccount;
		this.contentId = contentId;
		this.time = time;
		this.problemId = problemId;
	}
	public String getProblemId() {
		return problemId;
	}
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Collection(String userAccount, String contentId, String content) {
		super();
		this.userAccount = userAccount;
		this.contentId = contentId;
		this.content = content;
	}
	
	

}
