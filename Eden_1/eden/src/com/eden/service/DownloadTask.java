package com.eden.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpStatus;

import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.eden.activity.DownLoadActivity;
import com.eden.domain.FileInfo;
import com.eden.domain.ThreadInfo;
import com.eden.download.db.ThreadDAO;
import com.eden.download.db.ThreadDAOImpl;

public class DownloadTask {
	private Context mContext = null;
	private FileInfo mFileInfo = null;
	private ThreadDAO mDao = null;
	private int mFinished = 0;
	public boolean isPause = false;
	public DownloadTask(Context context,FileInfo mfileInfo){
		this.mContext  = context;
		this.mFileInfo = mfileInfo;
		mDao = new ThreadDAOImpl(mContext);
	}
	
	public void download(){
		List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
		ThreadInfo threadInfo = null;
		if(threadInfos.size() == 0){
			threadInfo = new ThreadInfo(0,mFileInfo.getUrl(),0,mFileInfo.getLength(),0);
		}else {
			threadInfo = threadInfos.get(0);
		}
		
		new DownloadThread(threadInfo).start();
	}
	
	
	
	class DownloadThread extends Thread{
		private ThreadInfo mThreadInfo = null;
		public DownloadThread(ThreadInfo mThreadInfo){
			this.mThreadInfo = mThreadInfo;
		}
		public void run(){
			if(!mDao.isExits(mThreadInfo.getUrl(), mThreadInfo.getId())){
				mDao.insertThread(mThreadInfo);
			}
			URL url = null;
			HttpURLConnection conn = null;
			InputStream input = null;
			RandomAccessFile raf = null;
			try {
				 url = new URL(mThreadInfo.getUrl());
				 conn = (HttpURLConnection) url.openConnection();
				 conn.setConnectTimeout(3000);
				 conn.setRequestMethod("GET");
				 int start = mThreadInfo.getStart()+mThreadInfo.getFinished();
				 conn.setRequestProperty("Range", "bytes="+start+"-"+mThreadInfo.getEnd());
				 File file = new File(DownloadService.DOWNLOAD_PATH,mFileInfo.getFileName());
				 raf = new RandomAccessFile(file,"rwd");
				 raf.seek(start);
				 
				 Intent intent = new Intent(DownloadService.ACTION_UPDATE);
				 mFinished += mThreadInfo.getFinished();
				 if(conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT){
					  input = conn.getInputStream();
					 byte[] buffer = new byte[1024*4];
					 int len = -1;
					 long time = System.currentTimeMillis();
					 while((len = input.read(buffer)) != -1){
						 raf.write(buffer);
						 mFinished += len;
						 if(System.currentTimeMillis()-time>500){
							 time = System.currentTimeMillis();
							 intent.putExtra("finished", mFinished*100/mFileInfo.getLength());
							 mContext.sendBroadcast(intent);
						 }
						if(isPause){
							mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinished);
							return;
						}
					 }
					 //删除
					 mDao.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());
                     DownLoadActivity.download.handler.sendEmptyMessage(0);
				 }
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				conn.disconnect();
				try {
					input.close();
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
