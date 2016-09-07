package com.eden.domain;

public class Confession {
	private int id;
	private String userName;
	private String confessionContent;
	private String modifyTime;
	private int zan;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getConfessionContent() {
		return confessionContent;
	}
	public void setConfessionContent(String confessionContent) {
		this.confessionContent = confessionContent;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public int getZan() {
		return zan;
	}
	public void setZan(int zan) {
		this.zan = zan;
	}
	public Confession(int id, String userName, String confessionContent, String modifyTime, int zan) {
		super();
		this.id = id;
		this.userName = userName;
		this.confessionContent = confessionContent;
		this.modifyTime = modifyTime;
		this.zan = zan;
	}
	
}
