package com.example.leohuang.password_manager.ui;

import com.example.leohuang.password_manager.bean.Item;

public class SortModel {

	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	public String icon;
	private Item item;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public void setItem(Item item){
		this.item = item;
	}
	public Item getItem(){
		return item;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
