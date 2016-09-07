package com.eden.domain;

public class Expert {
	private String expertAccount;
	private String expertName;
	private String expertSex;
	private String expertState;
	private String expertIntroduce;
	private int expertPv;
	private String expertAddress;
	private String expertIcon;
	private String expertEmail;
	public String getExpertAccount() {
		return expertAccount;
	}
	public void setExpertAccount(String expertAccount) {
		this.expertAccount = expertAccount;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getExpertSex() {
		return expertSex;
	}
	public void setExpertSex(String expertSex) {
		this.expertSex = expertSex;
	}
	public String getExpertState() {
		return expertState;
	}
	public void setExpertState(String expertState) {
		this.expertState = expertState;
	}
	public String getExpertIntroduce() {
		return expertIntroduce;
	}
	public void setExpertIntroduce(String expertIntroduce) {
		this.expertIntroduce = expertIntroduce;
	}
	public int getExpertPv() {
		return expertPv;
	}
	public void setExpertPv(int expertPv) {
		this.expertPv = expertPv;
	}
	public String getExpertAddress() {
		return expertAddress;
	}
	public void setExpertAddress(String expertAddress) {
		this.expertAddress = expertAddress;
	}
	public String getExpertIcon() {
		return expertIcon;
	}
	public void setExpertIcon(String expertIcon) {
		this.expertIcon = expertIcon;
	}
	public String getExpertEmail() {
		return expertEmail;
	}
	public void setExpertEmail(String expertEmail) {
		this.expertEmail = expertEmail;
	}
	public Expert(String expertAccount, String expertName, String expertSex, String expertState, String expertIntroduce,
			 int expertPv, String expertAddress, String expertIcon, String expertEmail) {
		super();
		this.expertAccount = expertAccount;
		this.expertName = expertName;
		this.expertSex = expertSex;
		this.expertState = expertState;
		this.expertIntroduce = expertIntroduce;
		this.expertPv = expertPv;
		this.expertAddress = expertAddress;
		this.expertIcon = expertIcon;
		this.expertEmail = expertEmail;
	}	
}
