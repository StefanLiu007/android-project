package com.eden.domain;

public class Content {
	private String userAccount;
	private String contentId;
	private String contentTitle;
	private String contentVideo;
	private String contentPicture;
	private String contentText;
	private int thumbUpNum;
	private String contentLastTime;
	private String contentAccount;
	public Content() {
		super();
	}
	
	public Content(String userAccount, String contentId, String contentTitle, String contentPicture, String contentText,
			int thumbUpNum, String contentLastTime) {
		super();
		this.userAccount = userAccount;
		this.contentId = contentId;
		this.contentTitle = contentTitle;
		this.contentPicture = contentPicture;
		this.contentText = contentText;
		this.thumbUpNum = thumbUpNum;
		this.contentLastTime = contentLastTime;
	}

	public Content(String userAccount, String contentId, String contentTitle, String contentVideo,
			String contentPicture, String contentText, int thumbUpNum, String contentLastTime, String contentAccount) {
		super();
		this.userAccount = userAccount;
		this.contentId = contentId;
		this.contentTitle = contentTitle;
		this.contentVideo = contentVideo;
		this.contentPicture = contentPicture;
		this.contentText = contentText;
		this.thumbUpNum = thumbUpNum;
		this.contentLastTime = contentLastTime;
		this.contentAccount = contentAccount;
	}

	public String getContentAccount() {
		return contentAccount;
	}

	public void setContentAccount(String contentAccount) {
		this.contentAccount = contentAccount;
	}

	public Content(String userAccount ,String contentTitle, String contentPicture, String contentText, int thumbUpNum,
			String contentLastTime) {
		super();
		this.contentTitle = contentTitle;
		this.contentPicture = contentPicture;
		this.contentText = contentText;
		this.thumbUpNum = thumbUpNum;
		this.contentLastTime = contentLastTime;
		this.userAccount = userAccount;
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

	@Override
	public String toString() {
		return "Content [userAccount=" + userAccount + ", contentId=" + contentId + ", contentTitle=" + contentTitle
				+ ", contentVideo=" + contentVideo + ", contentPicture=" + contentPicture + ", contentText="
				+ contentText + ", thumbUpNum=" + thumbUpNum + ", contentLastTime=" + contentLastTime + "]";
	}

	public Content(String contentAccount,String userAccount, String contentId, String contentLastTime) {
		super();
		this.contentAccount = contentAccount;
		this.userAccount = userAccount;
		this.contentId = contentId;
		this.contentLastTime = contentLastTime;
	}

	
	

}
