package com.tdActivity.android.util;

import java.util.ArrayList;
import java.util.List;

import com.tdActivity.android.entity.UserInfo;

public class ListUtils {
	/**
	 * 集合备份
	 * @param list
	 * @return
	 */
	public static List<UserInfo> copyToAnother(List<UserInfo> list){
		List<UserInfo> another=new ArrayList<UserInfo>();
		int len=list.size();
		for(int i=0;i<len;i++){
			another.add(list.get(i));
		}
		return another;
	}
}
