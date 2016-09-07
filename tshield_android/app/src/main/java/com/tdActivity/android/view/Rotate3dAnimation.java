/**
 * @Title: Rotate3dAnimation.java
 * @Package com.sloop.animation
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015骞�3鏈�10鏃� 涓婂�?11:20:10
 * @version V1.0
 */

package com.tdActivity.android.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import android.view.animation.Transformation;


/**
 * 3D翻转特效
 * @ClassName: Rotate3dAnimation
 * @author sloop
 * @date 2015�?3�?10�? 上午11:20:10
 */

public class Rotate3dAnimation extends Animation {
	
	 private InterpolatedTimeListener listener; 

	// �?始角�?
	private final float mFromDegrees;
	// 结束角度
	private final float mToDegrees;
	// 中心�?
	private final float mCenterX;
	private final float mCenterY;
	private final float mDepthZ;	//深度
	// 是否�?要扭�?
	private final boolean mReverse;
	// 摄像�?
	private Camera mCamera;
	ContextThemeWrapper context;
	//新增--像素比例（默认�?�为1�?
	float scale = 1;


	/**
	 * 创建�?个新的实�? Rotate3dAnimation. 
	 * @param fromDegrees	�?始角�?
	 * @param toDegrees		结束角度
	 * @param centerX		中心点x坐标
	 * @param centerY		中心点y坐标
	 * @param depthZ		深度
	 * @param reverse		是否扭曲
	 */
	public Rotate3dAnimation(ContextThemeWrapper context, float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
		this.context = context;
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;
		mCenterX = centerX;
		mCenterY = centerY;
		mDepthZ = depthZ;
		mReverse = reverse;
		//获取手机像素�? （即dp与px的比例）
		scale = context.getResources().getDisplayMetrics().density;
		Log.e("scale",""+scale);
	}
	
	 public void setInterpolatedTimeListener(InterpolatedTimeListener listener) {  
	        this.listener = listener;  
	    }

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	// 生成Transformation
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		
		if (listener != null) {  
            listener.interpolatedTime(interpolatedTime);  
        } 
		
		final float fromDegrees = mFromDegrees;
		// 生成中间角度
		float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

		
		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Camera camera = mCamera;

		final Matrix matrix = t.getMatrix();

		camera.save();
		if (mReverse) {
			camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
		} else {
			camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
		}
		 boolean overHalf = (interpolatedTime > 0.5f);  
	     if (overHalf) {  
	            // 翻转过半的情况下，为保证数字仍为可读的文字�?�非镜面效果的文字，�?翻转180度�??  
	        	degrees = degrees - 180;  
	        } 
		camera.rotateY(degrees);
		// 取得变换后的矩阵
		camera.getMatrix(matrix);
		camera.restore();

//----------------------------------------------------------------------------
		/** 
		 * 修复打脸问题		(￣�?(#�?)☆╰�?(￣▽�?///)
		 * �?要介绍：
		 * 原来�?3D翻转会由于屏幕像素密度问题�?�出现效果相差很�?
		 * 例如在屏幕像素比�?1,5的手机上显示效果基本正常�?
		 * 而在像素�?3,0的手机上面感觉翻转感觉要超出屏幕边缘�?
		 * 有种迎面打脸的感觉�??
		 * 
		 * 解决方案
		 * 利用屏幕像素密度对变换矩阵进行校正，
		 * 保证了在�?有清晰度的手机上显示的效果基本相同�??
		 *  
		 */
		float[] mValues = {0,0,0,0,0,0,0,0,0};
		matrix.getValues(mValues);			//获取数�??
		mValues[6] = mValues[6]/scale;			//数�?�修�?
		matrix.setValues(mValues);			//重新赋�??
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
	/** 动画进度监听器�?? */  
    public static interface InterpolatedTimeListener {  
        public void interpolatedTime(float interpolatedTime);  
    }


}
