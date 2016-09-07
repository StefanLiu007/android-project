package com.eden.util;

import java.io.File;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

public class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
	private ImageView imageView,imageView2;
	private Context context;
	private   BitmapUtils bitmapUtils = null;
	private BitmapDisplayConfig bigPicDisplayConfig;
	private String url="";

	public MyAsyncTask(ImageView imageView,Context context) {
		this.imageView=imageView;
		this.context=context;
		imageView2=new ImageView(context);
	}

	@Override
	protected 	Bitmap doInBackground(String... params) {
		File mCacheDir=null;
		if (android.os.Environment.getExternalStorageState().
				equals(android.os.Environment.MEDIA_MOUNTED)){
			mCacheDir = new File("/imagecache");
		}else{
			mCacheDir =context.getCacheDir();
		}// 如何获取系统内置的缓存存储路径
		if(!mCacheDir.exists())
			mCacheDir.mkdirs();
		bitmapUtils = new BitmapUtils(context, mCacheDir.getPath());

		bigPicDisplayConfig = new BitmapDisplayConfig();
		//		bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用, 图片太大时容易OOM。
		bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
		bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(context));
		Bitmap bitmap=bitmapUtils.getBitmapFromMemCache(params[0], bigPicDisplayConfig);
		url=params[0];
		return bitmap;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		if(result==null){
			bitmapUtils.display(imageView2,url,bigPicDisplayConfig,new BitmapLoadCallBack<View>() {

				@Override
				public void onLoadCompleted(View container, String uri,
						Bitmap bitmap, BitmapDisplayConfig config,
						BitmapLoadFrom from) {
					imageView.setImageBitmap(bitmap);
					System.out.println("从网络中取");
				}

				@Override
				public void onLoadFailed(View container, String uri,
						Drawable drawable) {
					// TODO Auto-generated method stub

				}
			});	

		}else{
			imageView.setImageBitmap(result);
			System.out.println("从缓存中取");
		}
		super.onPostExecute(result);
	}
}
