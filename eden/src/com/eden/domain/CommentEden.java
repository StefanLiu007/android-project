package com.eden.domain;

import java.io.Serializable;

public class CommentEden implements Serializable{
	private Comment comment;
	private String name;
	private String picture;
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public CommentEden(Comment comment, String name, String picture) {
		super();
		this.comment = comment;
		this.name = name;
		this.picture = picture;
	}
	

}
