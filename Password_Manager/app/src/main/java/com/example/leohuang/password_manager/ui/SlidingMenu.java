package com.example.leohuang.password_manager.ui;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.leohuang.password_manager.R;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView {
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽�?
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;
	private int mviewwith;
	public static boolean isOpen;
	private ListView listView; 
	private boolean once;
	private View mview,topView,bottomView,bg;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	public static List<View> ignoredViews,ignoreViewCopy;
	private LinearLayout ss;
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScreenWidth = ScreenUtils.getScreenWidth(context);
		ignoredViews = new ArrayList<View>();
		ignoreViewCopy = new ArrayList<View>();
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				// 默认50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 20f,
								getResources().getDisplayMetrics()));// 默认�?10DP
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context) {
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 显示的设置一个宽�?
		 */
		if (!once) {
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(0);
			mview = wrapper.getChildAt(1);
			mContent = (LinearLayout) wrapper.getChildAt(2);
			topView = mContent.getChildAt(0);
			bottomView = mContent.getChildAt(2);
			mMenuWidth = mScreenWidth/3*2;
			mHalfMenuWidth = mMenuWidth / 3;
			mMenu.getLayoutParams().width = mMenuWidth;
			mContent.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 将菜单隐�?
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX  = ev.getX();
			break;

		default:
			break;
		}

		if (isInIgnoredView(ev)) {
			return false;
		}
		
		return super.onInterceptTouchEvent(ev);
	}
	private boolean isInIgnoredView(MotionEvent ev) {
		Rect rect = new Rect();
		for (View v : ignoredViews) {
			v.getGlobalVisibleRect(rect);
			if (rect.contains((int) ev.getX(), (int) ev.getY()))
				return true;
		}
		return false;
	}
	float startX = 0;
	private VelocityTracker mVelocityTracker = null;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
		switch (action) {
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐�?
		case MotionEvent.ACTION_UP:
			mVelocityTracker.computeCurrentVelocity(1000);
			int xVelocity = (int) mVelocityTracker.getXVelocity();
			if(ev.getX()-startX<0){
				if(ev.getX()-startX<-mScreenWidth/3 || xVelocity<-200){
					mview.setVisibility(View.GONE);
					topView.setVisibility(View.GONE);
					bottomView.setVisibility(View.GONE);
					this.smoothScrollTo(mMenuWidth, 0);
					isOpen = false;
				} else {
					mview.setVisibility(View.VISIBLE);
					topView.setVisibility(View.VISIBLE);
					bottomView.setVisibility(View.VISIBLE);
					this.smoothScrollTo(0, 0);
					isOpen = true;
				}
			}
			if(ev.getX()-startX>0){
				if(ev.getX()-startX>mScreenWidth/2 ||xVelocity>200){
					mview.setVisibility(View.VISIBLE);
					topView.setVisibility(View.VISIBLE);
					bottomView.setVisibility(View.VISIBLE);
					this.smoothScrollTo(0, 0);
					isOpen = true;
				}else {
					mview.setVisibility(View.GONE);
					topView.setVisibility(View.GONE);
					bottomView.setVisibility(View.GONE);
					this.smoothScrollTo(mMenuWidth, 0);
					isOpen = false;
				}
			}
			 if (mVelocityTracker != null) {
                 mVelocityTracker.recycle();
                 mVelocityTracker = null;
             }
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		mview.setVisibility(View.VISIBLE);
		topView.setVisibility(View.VISIBLE);
		bottomView.setVisibility(View.VISIBLE);
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (isOpen) {
			mview.setVisibility(View.GONE);
			topView.setVisibility(View.GONE);
			bottomView.setVisibility(View.GONE);
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;

		}
	}

	/**
	 * 切换菜单状�??
	 */
	public void toggle() {
		if (isOpen) {

			closeMenu();

		} else {

			openMenu();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		float leftScale = 1 - 0.3f * scale;
		float rightScale = 0.8f + scale * 0.2f;
		
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

		ViewHelper.setPivotX(mview, 0);
		ViewHelper.setPivotY(mview, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mview, rightScale);
		ViewHelper.setScaleY(mview, rightScale);
		
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
		
	}
	public void addIgnoredView(ViewParent viewParent) {
		if(!ignoredViews.contains(viewParent)){
			ignoredViews.add((View) viewParent);
		}
		if(!ignoreViewCopy.contains(viewParent)){
			ignoreViewCopy.add((View) viewParent);
		}
		
	}

	/**
	 * remove the view from ignored view list;
	 * 
	 * @param v
	 */
	public void removeIgnoredView(View v) {
		ignoredViews.remove(v);
		ignoreViewCopy.remove(v);
	}
	/**
	 * 移除�?有的view从ingoredview
	 */
	public static void removeAllIgnoredView() {
		ignoredViews.clear();
	}
	 public ListView getListView() {  
	        return listView;  
	    }  
	  
	    public void setListView(ListView listView) {  
	        this.listView = listView;  
	    }
}
