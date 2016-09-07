package com.eden.domain;

import java.io.Serializable;

public class Content implements Serializable{
	private String userAccount;
	private String contentId;
	private String contentTitle;
	private String contentVideo;
	private String contentPicture;
	private String contentText;
	private int thumbUpNum;
	private String contentLastTime;
	public Content() {
		super();
	}
	
	public Content(String contentTitle, String contentPicture,
			String contentText, int thumbUpNum, String contentLastTime) {
		super();
		this.contentTitle = contentTitle;
		this.contentPicture = contentPicture;
		this.contentText = contentText;
		this.thumbUpNum = thumbUpNum;
		this.contentLastTime = contentLastTime;
	}

	public Content(String userAccount, String contentId, String contentTitle, String contentVideo,
			String contentPicture, String contentText, int thumbUpNum, String contentLastTime) {
		super();
		this.userAccount = userAccount;
		this.contentId = contentId;
		this.contentTitle = contentTitle;
		this.contentVideo = contentVideo;
		this.contentPicture = contentPicture;
		this.contentText = contentText;
		this.thumbUpNum = thumbUpNum;
		this.contentLastTime = contentLastTime;
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
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getContentVideo() {
		return contentVideo;
	}
	public void setContentVideo(String contentVideo) {
		this.contentVideo = contentVideo;
	}
	public String getContentPicture() {
		return contentPicture;
	}
	public void setContentPicture(String contentPicture) {
		this.contentPicture = contentPicture;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public int getThumbUpNum() {
		return thumbUpNum;
	}
	public void setThumbUpNum(int thumbUpNum) {
		this.thumbUpNum = thumbUpNum;
	}
	public String getContentLastTime() {
		return contentLastTime;
	}
	public void setContentLastime(String contentLastTime) {
		this.contentLastTime = contentLastTime;
	}

}
