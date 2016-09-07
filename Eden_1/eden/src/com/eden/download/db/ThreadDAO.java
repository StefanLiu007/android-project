package com.eden.download.db;

import java.util.List;

import com.eden.domain.ThreadInfo;



public interface ThreadDAO {
	public void insertThread(ThreadInfo threadInfo);
	
	public void deleteThread(String url,int thread_id);
	
	public void updateThread(String url, int thread_id,int finished);
	
	public List<ThreadInfo> getThreads(String url);
	
	public boolean isExits(String url,int thread_id);

}
