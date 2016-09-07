package com.tdActivity.android.view;

import com.tdActivity.android.activity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

public class CornerListView extends ListView {

	public CornerListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
	}

	public CornerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CornerListView(Context context) {
		super(context);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		  switch (ev.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	            int x = (int) ev.getX();  
	            int y = (int) ev.getY();  
	            int itemnum = pointToPosition(x, y);  
	            if (itemnum == AdapterView.INVALID_POSITION)  
	                break;  
	            else {  
	                if (itemnum == 0) {  
	                    if (itemnum == (getAdapter().getCount() - 1)) {  
	                        //只有�?�?  
	                        setSelector(R.drawable.listview_style);  
	                    } else {  
	                        //第一�?  
	                        setSelector(R.drawable.listview_style_top);  
	                    }  
	                } else if (itemnum == (getAdapter().getCount() - 1))  
	                    //�?后一�?  
	                    setSelector(R.drawable.listview_style_bottom);  
	                else {  
	                    //中间�?  
	                    setSelector(R.drawable.listview_style_center);  
	                }  
	            }  
	            break;  
	        case MotionEvent.ACTION_UP:  
	            break;  
	        } 
		return super.onInterceptTouchEvent(ev);
	}

}
