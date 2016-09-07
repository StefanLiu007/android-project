package com.eden.domain;

public class AskedExpert {
	private String expertAccount;
	private String userAccount;
	public String getExpertAccount() {
		return expertAccount;
	}
	public void setExpertAccount(String expertAccount) {
		this.expertAccount = expertAccount;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public AskedExpert(String expertAccount, String userAccount) {
		super();
		this.expertAccount = expertAccount;
		this.userAccount = userAccount;
	}
	public AskedExpert() {
		super();
	}
	

}
