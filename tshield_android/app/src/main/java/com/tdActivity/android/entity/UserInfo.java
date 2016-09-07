package com.tdActivity.android.entity;

import java.io.Serializable;

import com.tdActivity.android.view.SlideView;

public class UserInfo implements Serializable {
	public String yy;//账号
	public String name;//用户名
	public String password;//秘钥
	public SlideView slideView;
	@Override
	public String toString() {
		return "UserInfo [yy=" + yy + ", name=" + name + ", password=" + password + ", slideView=" + slideView
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((yy == null) ? 0 : yy.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (yy == null) {
			if (other.yy != null)
				return false;
		} else if (!yy.equals(other.yy))
			return false;
		return true;
	}

	


}
