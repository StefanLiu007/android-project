package com.eden.domain;

import java.io.Serializable;

public class User implements Serializable{
	private String userAccount;
	private String userNickname;
	private String userPassword;
	private String userName;
	private String userSignature;
	private String userBirthday;
	private String userSex;
	private String userSchool;
	private String userMobile;
	private String userMail;
	private String userIcon;
	private String userState;

	
	public User() {
		super();
	}
	public User(String userAccount, String userName, String userIcon) {
		super();
		this.userAccount = userAccount;
		this.userName = userName;
		this.userIcon = userIcon;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserNickname() {
		return userNickname;
	}
	
	public User(String userAccount, String userPassword) {
		super();
		this.userAccount = userAccount;
		this.userPassword = userPassword;
	}
	public User(String userAccount, String userNickname, String userPassword, String userName, String userSignature,
			String userBirthday, String userSex, String userSchool, String userMobile, String userMail,
			String userIcon) {
		super();
		this.userAccount = userAccount;
		this.userNickname = userNickname;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userSignature = userSignature;
		this.userBirthday = userBirthday;
		this.userSex = userSex;
		this.userSchool = userSchool;
		this.userMobile = userMobile;
		this.userMail = userMail;
		this.userIcon = userIcon;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSignature() {
		return userSignature;
	}
	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserSchool() {
		return userSchool;
	}
	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public User(String userNickname, String userName, String userSignature, String userBirthday, String userSex,
			String userSchool, String userMobile, String userMail) {
		super();
		this.userNickname = userNickname;
		this.userName = userName;
		this.userSignature = userSignature;
		this.userBirthday = userBirthday;
		this.userSex = userSex;
		this.userSchool = userSchool;
		this.userMobile = userMobile;
		this.userMail = userMail;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public User(String userAccount, String userNickname, String userPassword, String userName, String userSignature,
			String userBirthday, String userSex, String userSchool, String userMobile, String userMail, String userIcon,
			String userState) {
		super();
		this.userAccount = userAccount;
		this.userNickname = userNickname;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userSignature = userSignature;
		this.userBirthday = userBirthday;
		this.userSex = userSex;
		this.userSchool = userSchool;
		this.userMobile = userMobile;
		this.userMail = userMail;
		this.userIcon = userIcon;
		this.userState = userState;
	}
	public User(String userAccount, String userNickname, String userSignature, String userBirthday, String userSex,
			String userSchool, String userMobile, String userMail, String userIcon, String userState) {
		super();
		this.userAccount = userAccount;
		this.userNickname = userNickname;
		this.userSignature = userSignature;
		this.userBirthday = userBirthday;
		this.userSex = userSex;
		this.userSchool = userSchool;
		this.userMobile = userMobile;
		this.userMail = userMail;
		this.userIcon = userIcon;
		this.userState = userState;
	}
	
}
