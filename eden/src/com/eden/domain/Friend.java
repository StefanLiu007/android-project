package com.eden.domain;

public class Friend {
	private String friendAccount;
	private String userAccount;
	public String getFriendAccount() {
		return friendAccount;
	}
	public void setFriendAccount(String friendAccount) {
		this.friendAccount = friendAccount;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Friend(String friendAccount, String userAccount) {
		super();
		this.friendAccount = friendAccount;
		this.userAccount = userAccount;
	}
	

}
