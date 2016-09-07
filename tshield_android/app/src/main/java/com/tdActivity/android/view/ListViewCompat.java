package com.tdActivity.android.view;

import com.tdActivity.android.entity.UserInfo;

import android.content.Context;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class ListViewCompat extends ListView {

	@SuppressWarnings("unused")
	private static final String TAG = "ListViewCompat";
	// 滑动距离及坐标
	private int xDistance, yDistance, xLast, yLast;
	private SlideView mFocusedItemView;
	private int lastpostion = -1;

	public ListViewCompat(Context context) {
		super(context);
	}

	public ListViewCompat(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewCompat(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void shrinkListItem(int position) {
		View item = getChildAt(position);

		if (item != null) {
			try {
				((SlideView) item).shrink();

			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onInterceptHoverEvent(MotionEvent event) {
		return super.onInterceptHoverEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0;
			xLast = (int) event.getX();
			yLast = (int) event.getY();
			int position = pointToPosition(xLast, yLast);

			if (position != INVALID_POSITION) {
				UserInfo data = (UserInfo) getItemAtPosition(position);
				mFocusedItemView = data.slideView;
				if (lastpostion != position) {

					if (lastpostion != -1) {
						shrinkListItem(lastpostion);

					}

					lastpostion = position;
				} else {
					shrinkListItem(lastpostion);
				}

			}

			break;
		case MotionEvent.ACTION_MOVE:
			final int curX = (int) event.getX();
			final int curY = (int) event.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;
			position = pointToPosition(xLast, yLast);
			if (position != INVALID_POSITION) {
				if (xDistance > yDistance) {
					if (mFocusedItemView != null) {
						mFocusedItemView.onRequireTouchEvent(event);

					}
					return false;
				}
			}

		}

		return super.onTouchEvent(event);
	}

}
