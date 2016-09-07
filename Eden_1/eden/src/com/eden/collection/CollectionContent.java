package com.eden.collection;

import com.eden.domain.Content;

public class CollectionContent {
	private Content content;
	private String time;
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public CollectionContent(Content content, String time) {
		super();
		this.content = content;
		this.time = time;
	}
  
	
}
