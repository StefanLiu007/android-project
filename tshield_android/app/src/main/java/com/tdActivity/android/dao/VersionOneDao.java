package com.tdActivity.android.dao;

import java.util.ArrayList;
import java.util.List;
import com.tdActivity.android.entity.UserInfo;
import com.tdActivity.android.util.AppUtil;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class VersionOneDao {
	// 绑定成功
	public static int BIND_SUCCESS = 0x01;
	// 没有绑定
	public static int NOT_BIND = 0x02;

	// �?要手�?
	public static int NEED_GESTURE = 0x01;
	// 不需要手�?
	public static int NOT_NEED_GESTURE = 0x02;

	private SQLiteHelper helper = null;
	private String uuid;
	private SQLiteDatabase mDatabase;

	public VersionOneDao(Context context) {
		SQLiteDatabase.loadLibs(context);
		helper = SQLiteHelper.getHelper(context);
		uuid = AppUtil.getuuid(context);
	}

	/* 手势数据�? */

	/**
	 * 获取可写的数据库
	 * @return
	 */
	public synchronized SQLiteDatabase getWritableDatabase(){
		if(mDatabase!=null){
			if(!mDatabase.isOpen()){
				mDatabase=null;
			}else if(!mDatabase.isReadOnly()){
				return mDatabase;
			}
		}
		mDatabase=helper.getWritableDatabase(uuid);
		return mDatabase;
	}
	/**
	 * 获取只读的数据库
	 * @return
	 */
	public synchronized SQLiteDatabase getReadableDatabase(){
		if(mDatabase!=null){
			if(!mDatabase.isOpen()){
				mDatabase=null;
			}else{
				return mDatabase;
			}
		}
		mDatabase=helper.getReadableDatabase(uuid);
		return mDatabase;
	}
	/**
	 * 手势数据库中是否有数�?
	 * 
	 * @return
	 */
	public synchronized boolean gestureHasData() {
		boolean flag = false;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_GESTURE, null, null, null, null, null, null);
			if (cursor.moveToNext()) {
				flag = true;
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return flag;
	}

	/**
	 * 更新错误次数信息
	 * 
	 * @param count
	 */
	public synchronized void errorCountUpdate(int count) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("errorGestureCount", count);
			db.update(SQLiteHelper.TABLE_GESTURE, values, null, null);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 获取错误次数
	 * 
	 * @return
	 */
	public synchronized int errorCountGet() {
		int count = 0;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_GESTURE, new String[] { "errorGestureCount" }, null, null, null,
					null, null);
			if (cursor.moveToNext()) {
				count = cursor.getInt(cursor.getColumnIndex("errorGestureCount"));
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return count;
	}

	/**
	 * 添加�?条手势记�?
	 * 
	 * @param gesture
	 * @param bind
	 */
	public synchronized void gestureInsert(String gesture, int bind) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("getsurePassword", gesture);
			values.put("hasBindPhone", bind);
			values.put("needGestureWithOutTime", VersionOneDao.NEED_GESTURE);
			db.insert(SQLiteHelper.TABLE_GESTURE, null, values);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 更新手势密码是否启动
	 * 
	 * @param need
	 */
	public synchronized void gestureNeedUpdate(int need) {
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("needGestureWithOutTime", need);
			db.update(SQLiteHelper.TABLE_GESTURE, values, null, null);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 获取是否�?�?要手势密�?
	 * 
	 * @return
	 */
	public synchronized int gestureNeedGet() {
		int need = -1;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_GESTURE, new String[] { "needGestureWithOutTime" }, null, null,
					null, null, null);
			if (cursor.moveToNext()) {
				need = cursor.getInt(cursor.getColumnIndex("needGestureWithOutTime"));
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return need;
	}

	/**
	 * 删除手势
	 */
	public synchronized void gestureDelete() {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			db.delete(SQLiteHelper.TABLE_GESTURE, null, null);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 更新
	 */
	public synchronized void gestureUpdate(String gesture) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("getsurePassword", gesture);
			db.update(SQLiteHelper.TABLE_GESTURE, values, null, null);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 获取数据库中的手势密?
	 * 
	 * @return
	 */
	public synchronized String gestureGet() {
		String gesture = "";
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_GESTURE, new String[] { "getsurePassword" }, null, null, null,
					null, null);
			if (cursor.moveToNext()) {
				gesture = cursor.getString(cursor.getColumnIndex("getsurePassword"));
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return gesture;
	}

	/* 用户信息数据�? */

	/**
	 * 是否有数�?
	 * 
	 * @return
	 */
	public synchronized boolean userInfoHasDate() {
		boolean flag = false;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_NAME_V1, new String[] { "platformName" }, null, null, null,
					null, null);
			if (cursor.moveToNext()) {
				flag = true;
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return flag;
	}

	/**
	 * 是否有指定数�?
	 * 
	 * @return
	 */
	public synchronized boolean userInfoHasDate(UserInfo userInfo) {
		boolean flag = false;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_NAME_V1, new String[] { "platformName", "accountName" },
					"platformName=? and accountName=?", new String[] { userInfo.yy, userInfo.name }, null, null, null);
			if (cursor.moveToNext()) {
				flag = true;
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return flag;
	}

	/**
	 * 插入数据
	 * 
	 * @param userInfo
	 */
	public  synchronized void userInfoInsert(UserInfo userInfo, int index) {
		boolean isFind = false;
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_NAME_V1, new String[] { "secret" }, null, null, null, null,
					"secret");
			while (cursor.moveToNext()) {
				if (cursor.getString(cursor.getColumnIndex("secret")).equals(userInfo.password)) {
					isFind = true;
					break;
				}
			}
			if (cursor != null) {
				cursor.close();
			}
			if (!isFind) {
				ContentValues values = new ContentValues();
				values.put("secret", userInfo.password);
				values.put("platformName", userInfo.yy);
				values.put("accountName", userInfo.name);
				values.put("orderNumber", index);
				db.insert(SQLiteHelper.TABLE_NAME_V1, null, values);

			}
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 更新顺序数据
	 * 
	 * @param userInfo
	 */
	public synchronized void userInfoUpdate(UserInfo userInfo, int index) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("orderNumber", index);
			db.update(SQLiteHelper.TABLE_NAME_V1, values, "secret=?", new String[] { userInfo.password });
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 编辑账户
	 * 
	 * @param userInfo
	 */
	public synchronized void userAccountUpdate(UserInfo userInfo) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("platformName", userInfo.name);
			values.put("accountName", userInfo.yy);
			db.update(SQLiteHelper.TABLE_NAME_V1, values, "secret=?", new String[] { userInfo.password });
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 删除�?条数�?
	 * 
	 * @param userInfo
	 */
	public synchronized void userInfoDeleteItem(UserInfo userInfo) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			db.delete(SQLiteHelper.TABLE_NAME_V1, "secret=? and platformName=? and accountName=?",
					new String[] { userInfo.password, userInfo.yy, userInfo.name });
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 更新�?条数�?
	 * 
	 * @param userInfo
	 */
	public synchronized void userInfoUpdateItem(UserInfo userInfo) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("platformName", userInfo.name);
			values.put("accountName", userInfo.yy);
			db.update(SQLiteHelper.TABLE_NAME_V1, values, "secret=?", new String[] { userInfo.password });
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 每条数据进行排序
	 * 
	 * @param userInfo
	 * @param index
	 */
	public synchronized void userInfosSort(UserInfo userInfo, int index) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("orderNumber", index);
			db.update(SQLiteHelper.TABLE_NAME_V1, values, "secret=? and platformName=? and accountName=?",
					new String[] { userInfo.password, userInfo.yy, userInfo.name });
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 获取�?有的数据
	 * 
	 * @return
	 */
	public synchronized List<UserInfo> userInfosFrom() {
		List<UserInfo> userInfos = new ArrayList();
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_NAME_V1,
					new String[] { "secret", "platformName", "accountName" }, null, null, null, null, "orderNumber");
			while (cursor.moveToNext()) {
				UserInfo userInfo = new UserInfo();
				userInfo.name = cursor.getString(cursor.getColumnIndex("accountName"));
				userInfo.password = cursor.getString(cursor.getColumnIndex("secret"));
				userInfo.yy = cursor.getString(cursor.getColumnIndex("platformName"));
				userInfos.add(userInfo);
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return userInfos;
	}

	/**
	 * 获取数据库中的数据数�?
	 * 
	 * @return
	 */
	public synchronized int userInfoGetCount() {
		int count = -1;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_NAME_V1, new String[] { "platformName" }, null, null, null,
					null, null);
			count = cursor.getCount();
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return count;
	}
	/* 版本数据�? */

	/**
	 * 版本数据库中是否有数�?
	 * 
	 * @return
	 */
	public synchronized boolean versionHasData() {
		boolean flag = false;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_VERSION, new String[] { "version" }, null, null, null, null,
					null);
			if (cursor.moveToNext()) {
				flag = true;
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return flag;
	}

	/**
	 * 版本数据库中的版本信�?
	 * 
	 * @return
	 */
	public synchronized int versionGetVersion() {
		int version = 0;
		SQLiteDatabase db=getReadableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			Cursor cursor = db.query(SQLiteHelper.TABLE_VERSION, new String[] { "version" }, null, null, null, null,
					null);
			while (cursor.moveToNext()) {
				version = cursor.getInt(cursor.getColumnIndex("version"));
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		if (db != null) {
			db.close();
		}
		return version;
	}

	/**
	 * 更新版本信息
	 * 
	 * @param version
	 */
	public synchronized void versionUpdate(int oldVersion, int newVersion) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("version", newVersion);
			db.update(SQLiteHelper.TABLE_VERSION, values, "version=?", new String[] { oldVersion + "" });
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 版本信息添加
	 * 
	 * @param version
	 */
	public synchronized void versionInsert(int version) {
		SQLiteDatabase db=getWritableDatabase();
		if (db.isOpen() && !db.isDbLockedByOtherThreads()) {
			ContentValues values = new ContentValues();
			values.put("version", version);
			db.insert(SQLiteHelper.TABLE_VERSION, null, values);
		}
		if (db != null) {
			db.close();
		}
	}
	
	/**
	 * 覆盖
	 * @param accounts
	 */
	public synchronized  void cover(ArrayList<UserInfo> accounts){
		SQLiteDatabase db=getWritableDatabase();
		db.delete(SQLiteHelper.TABLE_NAME_V1, null, null);
		int len=accounts.size();
		for(int i=0;i<len;i++){
			UserInfo account =accounts.get(i);
			ContentValues values=new ContentValues();
			values.put("secret", account.password);
			values.put("platformName", account.yy);
			values.put("accountName", account.name);
			values.put("orderNumber", i);
			db.insert(SQLiteHelper.TABLE_NAME_V1, null, values);
		}
	}
	
	/**
	 * 合并
	 * @param accounts
	 */
	public synchronized void merger(ArrayList<UserInfo> accounts){
		SQLiteDatabase db=getWritableDatabase();
		Cursor cursorNum=db.rawQuery("select count(*) from "+SQLiteHelper.TABLE_NAME_V1, null);
		cursorNum.moveToFirst();
		int size=cursorNum.getInt(0);
		cursorNum.close();
		int len=accounts.size();
		for(int i=0;i<len;i++){
			UserInfo account=accounts.get(i);
			boolean exist=isExist(account.password,db);
			//TODO 是存现有的还是备份de
			if(!exist){//
				ContentValues values=new ContentValues();
				values.put("secret", account.password);
				values.put("platformName", account.yy);
				values.put("accountName", account.name);
				values.put("orderNumber", size++);
				db.insert(SQLiteHelper.TABLE_NAME_V1, null, values);
			}
		}
		db.close();
		
	}
	
	
	/**
	 * 判断是否存在
	 * @param secret
	 * @return
	 */
	private boolean isExist(String secret,SQLiteDatabase db){
		boolean exist=false;
		Cursor cursor=db.query(SQLiteHelper.TABLE_NAME_V1, new String[]{"secret"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			String oldSecret=cursor.getString(0);
			if(oldSecret.equals(secret)){
				exist=true;
				break;
			}
		}
		cursor.close();
		return exist;
	}
}
