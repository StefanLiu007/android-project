package com.tdActivity.android.util;

import com.tdActivity.android.view.CustomProgressDialog;

import android.content.Context;

public class ProgressShowUtils {
	public static final int MESSAGE=0x01;
	public static final int DATABASE=0x02;
	public static final int CAPTURE=0x03;
	
	public static final int SYNCH_LOCAL=0x04;
	/**
	 * 取消加载对话框
	 */
	public static void stopProgressDialog(CustomProgressDialog progressDialog) {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	/**
	 * 显示加载对话框
	 */
	public static CustomProgressDialog startProgressDialog(CustomProgressDialog progressDialog, Context context,int type) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
			if(type==MESSAGE){
			progressDialog.setMessage("正在玩命加载...");}
			else if(type==DATABASE){
				progressDialog.setMessage("读取加密数据库...");
			}else if(type==CAPTURE){
				progressDialog.setMessage("和数据库交互中...");
			}else if(type==SYNCH_LOCAL){
				progressDialog.setMessage("进行恢复中...");
			}
		}
		progressDialog.show();
		return progressDialog;
	}
}
