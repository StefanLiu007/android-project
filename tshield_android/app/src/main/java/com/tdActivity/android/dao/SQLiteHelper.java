package com.tdActivity.android.dao;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * 进行数据库创�?
 * 
 * @author leo.huang
 *
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	public static String DATABASENAME = "t_shield_1.db";
	// 账号数据�?
	public static String TABLE_NAME_V1 = "TDOtpAccount";
	// 版本数据�?
	public static String TABLE_VERSION = "VersionTalbe";
	// 手势数据�?
	public static String TABLE_GESTURE = "TDConfig";

	private static SQLiteHelper helper = null;

	private SQLiteHelper(Context context) {
		super(context, DATABASENAME, null, 1);
	}

	public synchronized static SQLiteHelper getHelper(Context context) {
		if (helper == null) {
			helper = new SQLiteHelper(context);
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql1 = "create table if not exists TDOtpAccount(secret text,platformName text,digits integer,period integer,otpType integer,algorithm text,accountName text,createDate text,`rowid` integer primary key autoincrement,orderNumber integer)";
		String sql2 = "create table if not exists VersionTalbe(`rowid` integer primary key autoincrement,version integer)";
		String sql3 = "create table if not exists TDConfig(errorGestureCount integer,needGestureWithOutTime integer,hasBindPhone integer,`rowid` integer primary key autoincrement,getsurePassword text,gestureLocked integer);";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("drop table if exists UserInfo");
		onCreate(db);
	}

	@Override
	public synchronized void close() {
		super.close();
	}
	
	

}
