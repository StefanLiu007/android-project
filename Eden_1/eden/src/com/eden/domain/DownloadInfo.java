package com.eden.domain;

public class DownloadInfo {
	private int id;
	
	private String picture;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DownloadInfo(int id, String picture, String name) {
		super();
		this.id = id;
		this.picture = picture;
		this.name = name;
	}
	public DownloadInfo() {
		super();
	}
	@Override
	public String toString() {
		return "DownloadInfo [id=" + id + ", picture=" + picture + ", name="
				+ name + "]";
	}
	
	
	
	
}
