package com.eden.domain;

import java.io.Serializable;

public class ContentEden implements Serializable {
	private Content content;
	private String name;
	
	public ContentEden(Content content) {
		super();
		this.content = content;
	}
	public ContentEden(Content content, String name) {
		super();
		this.content = content;
		this.name = name;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
