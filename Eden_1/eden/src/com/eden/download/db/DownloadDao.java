package com.eden.download.db;

import java.util.List;

import com.eden.domain.DownloadInfo;

public interface DownloadDao {
	public void insertDownLoad(DownloadInfo downInfo);

	public void deleteDownLoad(String name);

	public List<DownloadInfo> getDownLoad();
//	public boolean isExits(int thread_id);
}
