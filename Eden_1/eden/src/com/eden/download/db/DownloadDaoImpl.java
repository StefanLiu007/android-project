package com.eden.download.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eden.domain.DownloadInfo;

public class DownloadDaoImpl implements DownloadDao{
	private DBhelper1 mhelper = null;
	
	public DownloadDaoImpl(Context context){
		mhelper = new DBhelper1(context);
	}
	@Override
	public void insertDownLoad(DownloadInfo downInfo) {
		SQLiteDatabase db = mhelper.getWritableDatabase();
		db.execSQL("insert into download_info(down_id,picture,name) values(?,?,?)",
				new Object[]{downInfo.getId(),downInfo.getPicture(),downInfo.getName()});
		db.close();
		
	}

	@Override
	public void deleteDownLoad(String name) {
		SQLiteDatabase db = mhelper.getWritableDatabase();
		db.execSQL("delete from download_info where name = ?",
				new Object[]{name});
		db.close();
		
	}

	@Override
	public List<DownloadInfo> getDownLoad() {
		SQLiteDatabase db = mhelper.getWritableDatabase();
		List<DownloadInfo> list = new ArrayList<>();
	   Cursor cursor =	db.rawQuery("select * from download_info",null);
		while (cursor.moveToNext()) {
			DownloadInfo down = new DownloadInfo();
			down.setId(cursor.getInt(cursor.getColumnIndex("down_id")));
			down.setName(cursor.getString(cursor.getColumnIndex("name")));
			down.setPicture(cursor.getString(cursor.getColumnIndex("picture")));
			list.add(down);
		}
		cursor.close();
	    db.close();
		return list;
	}

}
