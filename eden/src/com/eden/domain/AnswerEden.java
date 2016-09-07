package com.eden.domain;

import java.io.Serializable;

public class AnswerEden implements Serializable{
	private Answer answer;
	private User use;
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	public User getUse() {
		return use;
	}
	public void setUse(User use) {
		this.use = use;
	}
	public AnswerEden(Answer answer, User use) {
		super();
		this.answer = answer;
		this.use = use;
	}
	

}
