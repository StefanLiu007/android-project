package com.eden.domain;

import java.io.Serializable;

public class Problem implements Serializable{
	private String problemID;
	private String userAccou;
	private String problemContent;
	private String problemImage;
	private String problemLastTime;
	private int problemAnswerNum;
	public String getProblemID() {
		return problemID;
	}
	public void setProblemID(String problemID) {
		this.problemID = problemID;
	}
	public String getUserAccou() {
		return userAccou;
	}
	public void setUserAccou(String userAccou) {
		this.userAccou = userAccou;
	}
	public String getProblemContent() {
		return problemContent;
	}
	public void setProblemContent(String problemContent) {
		this.problemContent = problemContent;
	}
	public String getProblemImage() {
		return problemImage;
	}
	public void setProblemImage(String problemImage) {
		this.problemImage = problemImage;
	}
	public String getProblemLastTime() {
		return problemLastTime;
	}
	public void setProblemLastTime(String problemLastTime) {
		this.problemLastTime = problemLastTime;
	}
	public int getProblemAnswerNum() {
		return problemAnswerNum;
	}
	public void setProblemAnswerNum(int problemAnswerNum) {
		this.problemAnswerNum = problemAnswerNum;
	}
	public Problem(String problemID, String userAccou, String problemContent, String problemImage,
			String problemLastTime, int problemAnswerNum) {
		super();
		this.problemID = problemID;
		this.userAccou = userAccou;
		this.problemContent = problemContent;
		this.problemImage = problemImage;
		this.problemLastTime = problemLastTime;
		this.problemAnswerNum = problemAnswerNum;
	}
	public Problem(String problemID, String userAccou, String problemContent,
			String problemLastTime, int problemAnswerNum) {
		super();
		this.problemID = problemID;
		this.userAccou = userAccou;
		this.problemContent = problemContent;
		this.problemLastTime = problemLastTime;
		this.problemAnswerNum = problemAnswerNum;
	}
	
}
