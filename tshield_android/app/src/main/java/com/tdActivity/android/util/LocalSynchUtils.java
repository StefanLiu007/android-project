package com.tdActivity.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tdActivity.android.MyApplication;
import com.tdActivity.android.dao.VersionOneDao;
import com.tdActivity.android.entity.UserInfo;

import android.content.Context;

/**
 * 本地同步文件帮助类
 * 
 * @author leo.huang
 *
 */
public class LocalSynchUtils {
	private final static String LOCAL = "local";

	/**
	 * 删除备份文件
	 * @param context
	 * @param fileNmae
	 */
	public static boolean DeleteFile(Context context,String fileNmae){
		File file = context.getFilesDir();
		File localSynchFile = new File(file, LOCAL);
		File targetFile=new File(localSynchFile,fileNmae);
		if(targetFile.exists()){
			return targetFile.delete();
		}
		return false;
	}
	/**
	 * 获取本地的所有数据的文件名
	 * 
	 * @param context
	 * @return
	 */
	public static String[] getSynchLocalFiles(Context context) {
		ArrayList<String> fileNames = new ArrayList<String>();
		File file = context.getFilesDir();
		File localSynchFile = new File(file, LOCAL);
		String[] filesName = localSynchFile.list();
		return filesName;
	}

	/**
	 * 缓存到临时文件中 覆盖
	 * 
	 * @param context
	 * @param json
	 */
	public static void addCacheFile(Context context, String json) {
		File cacheDir = context.getCacheDir();
		File cacheFile = new File(cacheDir, "temp");
		FileWriter fw = null;
		try {
			fw = new FileWriter(cacheFile);
			fw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyUtils.close(fw);
		}
	}

	/**
	 * 添加文件
	 * 
	 * @param context
	 * @param json
	 */
	public static void addLoaclFile(Context context, String json) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		File file = context.getFilesDir();
		File localSynchDir = new File(file, LOCAL);
		if (!localSynchDir.exists()) {
			localSynchDir.mkdir();
		}
		File dateFile = new File(localSynchDir, date);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(dateFile);
			byte[] buffer = json.getBytes();
			fos.write(buffer);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyUtils.close(fos);
		}
	}

	/**
	 * 从指定文件中获取内容
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getAppointFile(Context context, String fileName) {
		File file = context.getFilesDir();
		File localSynchDir = new File(file, LOCAL);
		if (!localSynchDir.exists()) {
			localSynchDir.mkdir();
		}
		File dateFile = new File(localSynchDir, fileName);
		byte[] result = null;
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {
			fis = new FileInputStream(dateFile);
			baos = new ByteArrayOutputStream();
			int len = 0;
			byte buf[] = new byte[1024];
			while ((len = fis.read(buf)) > -1) {
				baos.write(buf, 0, len);
			}
			result = baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyUtils.close(baos);
			MyUtils.close(fis);
		}
		return new String(result);
	}

	/**
	 * 从缓存中写文件到备份文件中
	 * 
	 * @param context
	 */
	public static void copyToFile(Context context) {
		File cacheDir = context.getCacheDir();
		File cacheFile = new File(cacheDir, "temp");
		File file = context.getFilesDir();
		File localSynchDir = new File(file, LOCAL);
		if (!localSynchDir.exists()) {
			localSynchDir.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		File dateFile = new File(localSynchDir, date);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(cacheFile);
			fos = new FileOutputStream(dateFile);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = fis.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			cacheFile.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyUtils.close(fos);
			MyUtils.close(fis);
		}
	}

	/**
	 * 线程更新
	 * 
	 * @param myApplication
	 */
	public static synchronized void threadCopyToFile(final MyApplication myApplication) {
		System.out.println("synchSetting---->" + myApplication.synchSetting);
		if (myApplication.synchSetting) {
			System.out.println("isChanged---->" + myApplication.isChanged);
			if (myApplication.isChanged
					&& (!LocalSynchUtils.isSame(myApplication.oldUserInfos, myApplication.userInfos))) {// 如果数据有改变，并且改变内容
				myApplication.isChanged = false;
				myApplication.fixedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						LocalSynchUtils.copyToFile(myApplication);

					}
				});
			}
		}
	}

	/**
	 * 判断是否相同
	 * 
	 * @param oldUserInfos
	 * @param changedUserInfo
	 * @return
	 */
	public static boolean isSame(List<UserInfo> oldUserInfos, List<UserInfo> changedUserInfo) {
		boolean same = true;
		int oldLen = oldUserInfos.size();
		int changeLen = changedUserInfo.size();
		if (oldLen != changeLen) {
			same = false;
		} else {
			for (int i = 0; i < oldLen; i++) {
				UserInfo oldUserInfo = oldUserInfos.get(i);
				UserInfo changeUserInfo = changedUserInfo.get(i);
				if (!oldUserInfo.equals(changeUserInfo)) {
					same = false;
					break;
				}
			}

		}
		return same;
	}

	/**
	 * 更新缓存
	 * 
	 * @param from
	 * @param to
	 */
	public static void copyAccountToUserInfo(ArrayList<UserInfo> from, List<UserInfo> to) {
		int len = from.size();
		if (to.size() > 0) {
			to.clear();
		}
		for (int i = 0; i < len; i++) {
			UserInfo userInfo = new UserInfo();
			UserInfo account = from.get(i);
			userInfo.name = account.name;
			userInfo.yy = account.yy;
			userInfo.password = account.password;
			to.add(userInfo);
		}
	}

	/**
	 * 更新旧缓存列表
	 * 
	 * @param oldUserInfos
	 * @param userInfos
	 */
	public static void copyNewToOld(List<UserInfo> oldUserInfos, List<UserInfo> userInfos) {
		int len = userInfos.size();
		if (oldUserInfos.size() > 0) {
			oldUserInfos.clear();
		}
		for (int i = 0; i < len; i++) {
			oldUserInfos.add(userInfos.get(i));
		}

	}

	/**
	 * 更新临时文件 
	 * 
	 * @param myApplication
	 * @param mDao
	 */
	public static synchronized void updateCacheFile(final MyApplication myApplication,final List<UserInfo> userInfos) {
		if (myApplication.synchSetting) {
			myApplication.fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					// 如果设置同步
						String json = JSONUtils.generateJson(userInfos);
						LocalSynchUtils.addCacheFile(myApplication, json);
					}
			});
		}
	}

	/**
	 * 不通过线程去更新
	 * @param myApplication
	 * @param userInfos
	 */
	public static void updateCacheFileNoThread(MyApplication myApplication,List<UserInfo> userInfos) {

		if (myApplication.synchSetting) {
				String json = JSONUtils.generateJson(userInfos);
				LocalSynchUtils.addCacheFile(myApplication, json);
		}
	}
	
	/**
	 * 判读临时文件是否存在
	 * @param context
	 * @return
	 */
	public static boolean cacheFileExist(Context context){
		
		File file=context.getCacheDir();
		File temp=new File(file,"temp");
		return temp.exists();
	}
	
	/**
	 * 删除临时文件
	 * @param context
	 */
	public static void deleteCache(Context context){
		File file=context.getCacheDir();
		File temp=new File(file,"temp");
		if(temp.exists()){
			temp.delete();
		}
	}
}
