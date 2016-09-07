package com.eden.util;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.StrictMode;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.eden.util.Constants.Config;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {
	private static List<Activity> activities;
	public static RequestQueue queue;
	private HttpStack stack;
	String userAgent = "volley/0";
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		super.onCreate();
		if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
				"io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

			/**
			 * IMKit SDK调用第一步 初始化
			 */
			RongIM.init(this);

		}
		if (stack == null) {//一般我们都不需要传这个参数进来，而volley则在这里会根据SDK的版本号来判断   
			if (Build.VERSION.SDK_INT >= 9) {  
				stack = new HurlStack();//SDK如果大于等于9，也就是Android 2.3以后，因为引进了HttpUrlConnection，所以会用一个HurlStack  
			} else {//如果小于9,则是用HttpClient来实现  
				// Prior to Gingerbread, HttpUrlConnection was unreliable.  
				// See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html  
				stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));  
			}  
		} 

		if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}
		initImageLoader(getApplicationContext());
		queue = Volley.newRequestQueue(getApplicationContext());
		DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 16 * 1024 * 1024);
		Network network = new BasicNetwork(stack);
		queue = new RequestQueue(cache, network);
		queue.start();
		activities=new ArrayList<Activity>();
	}
	public static RequestQueue getHttpQueue(){

		return queue;
	}

	public static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}
	public static void AddActivity(Activity activity){
		activities.add(activity);
	}
	public static List<Activity> getActivities() {
		return activities;
	}
	public static void setActivities(List<Activity> activities) {
		MyApplication.activities = activities;
	}

	public static void finishAllActivity(){
		for (Activity activity : activities) {
			activity.finish();
		}
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.writeDebugLogs() // Remove for release app
		.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
