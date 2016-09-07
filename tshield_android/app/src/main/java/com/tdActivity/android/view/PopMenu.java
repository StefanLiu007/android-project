package com.tdActivity.android.view;


import com.tdActivity.android.activity.R;
import com.tdActivity.android.util.FontManager;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopMenu {
	private Activity context;
	private PopupWindow popupWindow;
	//private ListView listView;
	private CornerListView listView;
	private TextView tv_single2;
	// private OnItemClickListener listener;
	//适配�??
	private BaseAdapter adapter;
	public PopMenu(Activity context) {
		this.context = context;
		View view = LayoutInflater.from(context)
				.inflate(R.layout.popmenu, null);
		
		listView = (CornerListView) view.findViewById(R.id.listView);
		tv_single2=(TextView) view.findViewById(R.id.tv_single2);
		tv_single2.setText(view.getResources().getString(R.string.bbb));
		tv_single2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
		//设置适配�??
		listView.setFocusableInTouchMode(true);
		listView.setFocusable(true);
		popupWindow = new PopupWindow(view, context.getResources()
				.getDimensionPixelSize(R.dimen.popmenu_width),
				LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.Animtop);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	//对外提供设置适配器方�??
	public void setAdapter(BaseAdapter adapter){
		this.adapter=adapter;
		listView.setAdapter(adapter);
	}
	
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		// this.listener = listener;
		listView.setOnItemClickListener(listener);
	}

//	public void addItems(String[] items) {
//		for (String s : items)
//			itemList.add(s);
//	}
//
//	public void addItem(String item) {
//		itemList.add(item);
//	}
//	public void clearItem(){
//		itemList.clear();
//	}

	public void showAsDropDown(View parent) {
		
		backgroundAlpha(0.5f);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
			popupWindow.showAsDropDown(parent,
					10,
					context.getResources().getDimensionPixelSize(
							R.dimen.popmenu_yoff));
			
			popupWindow.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					backgroundAlpha(1.0f);
					
				}
			});
			popupWindow.update();
			 
		
	}
	
	/**
	 * 设置添加屏幕的背景�?�明�?
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		context.getWindow().setAttributes(lp);
	}
	public void dismiss() {
		 backgroundAlpha(1.0f);
		popupWindow.dismiss();
		
	}
}
