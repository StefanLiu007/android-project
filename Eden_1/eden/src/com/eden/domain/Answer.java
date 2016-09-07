package com.eden.domain;

import java.io.Serializable;

public class Answer implements Serializable{
	private String problemId;
	private String answerAccout;
	private String content;
	private String time;
	private String answerReplyAccount;
	private int replyId;
	private int Id;
	private int type;
	
	

	public Answer() {
		super();
	}

	public Answer(String problemId, String answerAccout, String content,
			String time, String answerReplyAccount, int replyId, int id,
			int type) {
		super();
		this.problemId = problemId;
		this.answerAccout = answerAccout;
		this.content = content;
		this.time = time;
		this.answerReplyAccount = answerReplyAccount;
		this.replyId = replyId;
		Id = id;
		this.type = type;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public Answer(String problemId, String answerAccout, String content, String time, String answerReplyAccount, int id,
			int type) {
		super();
		this.problemId = problemId;
		this.answerAccout = answerAccout;
		this.content = content;
		this.time = time;
		this.answerReplyAccount = answerReplyAccount;
		Id = id;
		this.type = type;
	}

	public Answer(String answerAccout, String content, String time, int id, int type) {
		super();
		this.answerAccout = answerAccout;
		this.content = content;
		this.time = time;
		Id = id;
		this.type = type;
	}
	
	public String getAnswerReplyAccount() {
		return answerReplyAccount;
	}

	public Answer(String answerAccout, String content, String time, String answerReplyAccount, int type) {
		super();
		this.answerAccout = answerAccout;
		this.content = content;
		this.time = time;
		this.answerReplyAccount = answerReplyAccount;
		this.type = type;
	}

	public void setAnswerReplyAccount(String answerReplyAccount) {
		this.answerReplyAccount = answerReplyAccount;
	}

	public Answer(String problemId, String answerAccout, String content, String time, String answerReplyAccount,
			int type) {
		super();
		this.problemId = problemId;
		this.answerAccout = answerAccout;
		this.content = content;
		this.time = time;
		this.answerReplyAccount = answerReplyAccount;
		this.type = type;
	}

	public Answer(String problemId, String answerAccout, String content, String time, int id, int type) {
		super();
		this.problemId = problemId;
		this.answerAccout = answerAccout;
		this.content = content;
		this.time = time;
		Id = id;
		this.type = type;
	}
	public String getProblemId() {
		return problemId;
	}
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	public String getAnswerAccout() {
		return answerAccout;
	}
	public void setAnswerAccout(String answerAccout) {
		this.answerAccout = answerAccout;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	

}
