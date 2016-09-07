package com.eden.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper1 extends SQLiteOpenHelper{
	private static final String DB_NAME = "download1.db";
	private static final int VERSION =1;
	
    private static final String SQL_CREATE1 = "create table download_info(_id integer primary key autoincrement," +
    		"down_id integer,picture text,name text)";
    private static final String SQL_DROP1 = "drop table if exists download_info";
	public DBhelper1(Context context ) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(SQL_DROP1);
		db.execSQL(SQL_CREATE1);
		
	}

}
