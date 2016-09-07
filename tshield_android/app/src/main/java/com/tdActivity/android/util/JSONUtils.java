package com.tdActivity.android.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tdActivity.android.MyApplication;
import com.tdActivity.android.entity.UserInfo;

/**
 * json解析数据类
 * 
 * @author leo.huang
 *
 */
public class JSONUtils {

	/**
	 * 生成json
	 * 
	 * @param accounts
	 * @return
	 */
	public static String generateJson(List<UserInfo> userInfos) {
		JSONArray jsonArray = new JSONArray();
		int len = userInfos.size();
		for (int i = 0; i < len; i++) {
			UserInfo userInfo = userInfos.get(i);
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("platformName", userInfo.yy);
				String secret = new String(MyApplication.aes.encrypt(userInfo.password.getBytes()));			
				jsonObject.put("secret", secret);
				jsonObject.put("accountName", userInfo.name);
				jsonObject.put("orderNumber", i);
				jsonArray.put(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return jsonArray.toString();
	}

	/**
	 * 解析json
	 * 
	 * @param json
	 * @return
	 */
	public static ArrayList<UserInfo> analyseJson(String json) {
		ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				UserInfo account = new UserInfo();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				account.yy = jsonObject.getString("platformName");
				String secret = jsonObject.getString("secret");
				account.password = new String(MyApplication.aes.decrypt(secret));
				account.name = jsonObject.getString("accountName");
				userInfos.add(account);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return userInfos;
	}
}
