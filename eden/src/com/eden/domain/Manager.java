package com.eden.domain;

public class Manager {
	private int id;
	private String account;
	private String name;
	private String pwd;
	
	
	
	public Manager(int id, String account, String name) {
		super();
		this.id = id;
		this.account = account;
		this.name = name;
	}
	public Manager(String account, String name, String pwd) {
		super();
		this.account = account;
		this.name = name;
		this.pwd = pwd;
	}
	public Manager(int id, String account, String name, String pwd) {
		super();
		this.id = id;
		this.account = account;
		this.name = name;
		this.pwd = pwd;
	}
	public Manager() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPsd(String pwd) {
		this.pwd = pwd;
	}
	

}
