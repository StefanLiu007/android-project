package com.eden.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.eden.domain.FileInfo;

public class DownloadService extends Service{
	public static final String DOWNLOAD_PATH=
			Environment.getExternalStorageDirectory().getAbsolutePath()+
			"/downloads/";
	public static final String ACTION_START="ACTION_START";
	public static final String ACTION_STOP="ACTION_STOP";
	public static final String ACTION_UPDATE = "ACTION_UPDATE";
	private static final int MSG_INIT = 0;
	private DownloadTask mTask = null;
   @Override
public int onStartCommand(Intent intent, int flags, int startId) {
	if(ACTION_START.equals(intent.getAction())){
	FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
	Log.i("Main", fileInfo.getFileName());
		new InitThread(fileInfo).start();
	}else if(ACTION_STOP.equals(intent.getAction())){
		FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
		if(mTask != null){
			mTask.isPause = true;
		}
	}
	return super.onStartCommand(intent, flags, startId);
}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				FileInfo fileInfo = (FileInfo) msg.obj;
				 
				mTask = new DownloadTask(DownloadService.this, fileInfo);
				mTask.download();
				break;

			default:
				break;
			}
		};
	};
	
	
	
	
	class InitThread extends Thread{
		private FileInfo mfileInfo = null;
		
		public InitThread(FileInfo mFileInfo){
			this.mfileInfo = mFileInfo;
		}
		public void run(){
			HttpURLConnection conn = null;
			RandomAccessFile raf = null;
			try{
				URL url = new URL(mfileInfo.getUrl());
				 conn = (HttpURLConnection) url.openConnection();
				 conn.setRequestMethod("GET");
				 int length = -1;
				 if(conn.getResponseCode() == HttpStatus.SC_OK){
					 length = conn.getContentLength();
					 
				 }
				 if(length <= 0){
					 return;
				 }
				File dir = new File(DOWNLOAD_PATH);
				if(!dir.exists()){
					dir.mkdir();
				}
				File file = new File(dir,mfileInfo.getFileName());
				 Log.i("Main", file.getAbsolutePath()+"File");
				 raf = new RandomAccessFile(file, "rwd");
				raf.setLength(length);
				mfileInfo.setLength(length);
				mHandler.obtainMessage(MSG_INIT, mfileInfo).sendToTarget();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(raf != null){
					try {
						raf.close();
						raf = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if(conn != null){
					conn.disconnect();conn = null;
				}
				
			}
		}
	}

}
