package com.eden.collection;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class ListViewCollectionContent extends ListView {

	private static final String TAG = "ListViewCompat";

	private SlideView mFocusedItemView;

	public ListViewCollectionContent(Context context) {
		super(context);
	}

	public ListViewCollectionContent(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewCollectionContent(Context context, AttributeSet attrs, int defStyle) {
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
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			int x = (int) event.getX();
			int y = (int) event.getY();
			int position = pointToPosition(x, y);
			Log.e(TAG, "postion=" + position);
			if (position != INVALID_POSITION) {
				CollectionContentSilde data = (CollectionContentSilde) getItemAtPosition(position);
				mFocusedItemView = data.getSlideView();
				Log.e(TAG, "FocusedItemView=" + mFocusedItemView);
			}
		}
		default:
			break;
		}

		if (mFocusedItemView != null) {
			mFocusedItemView.onRequireTouchEvent(event);
		}

		return super.onTouchEvent(event);
	}

}
