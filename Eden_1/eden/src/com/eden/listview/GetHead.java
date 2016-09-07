package com.eden.listview;


import java.util.ArrayList;
import java.util.List;
import com.eden.R;
import com.eden.activity.MainActivity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class GetHead {
	 private static final String TAG = MainActivity.class.getSimpleName();
	 private ViewPager autoChangeViewPager;
	 private LinearLayout viewIndicator;
     private Context context;
     private ListView list;
	    //包含要在ViewPager中显示的图片
	    private List<View> pagers;
	public  void getPage(Context context,ListView list){
		this.context=context;
		this.list = list;
		   
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.pagechange_main, list, false);
		 autoChangeViewPager = (ViewPager) view.findViewById(R.id.autoVP);
	        viewIndicator = (LinearLayout) view.findViewById(R.id.vpindicator);
	        initAdapter();
	         
	        //监听页面改变事件来改变viewIndicator中的指示图片
	        autoChangeViewPager.setOnPageChangeListener(new ViewPageChangeListener());
	        list.addHeaderView(view);
		
	}
	private void initAdapter() {
        //即将在viewPager中展示的图片资源
        int[] imgs = {R.drawable.image_1, R.drawable.image_4, R.drawable.image_5};
         
        //init pagers;
        pagers = new ArrayList<View>();
        LinearLayout.LayoutParams img_params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        for(int i = 0; i < imgs.length; ++i) {
            ImageView iv = new ImageView(context);
            iv.setBackgroundResource(imgs[i]);
            iv.setLayoutParams(img_params);
            final int index = i;
            switch (i) {
			case 0:
				  iv.setOnClickListener(new OnClickListener() {
		                //当viewPager中的图片被点击后，跳转到新的activity
		                public void onClick(View v) {
		                   Toast.makeText(context, "123", Toast.LENGTH_LONG).show();
		                }
		            });
				break;
            case 1:
            	  iv.setOnClickListener(new OnClickListener() {
  	                //当viewPager中的图片被点击后，跳转到新的activity
  	                public void onClick(View v) {
  	                	Toast.makeText(context, "456", Toast.LENGTH_LONG).show();
  	                }
  	            });
				break;
			default:
				  iv.setOnClickListener(new OnClickListener() {
		                //当viewPager中的图片被点击后，跳转到新的activity
		                public void onClick(View v) {
		                	Toast.makeText(context, "789", Toast.LENGTH_LONG).show();
		                }
		            });
				break;
			}
          
            pagers.add(iv);
        }
        autoChangeViewPager.setAdapter(new ViewPagerAdapter(pagers));
         
        //init indicator
        LinearLayout.LayoutParams ind_params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < imgs.length; ++i) {
            ImageView iv = new ImageView(context);
            if(i == 0)
                iv.setBackgroundResource(R.drawable.point_select);
            else
                iv.setBackgroundResource(R.drawable.point_normal);
            iv.setPadding(10, 5, 10, 5);
            iv.setLayoutParams(ind_params);
            viewIndicator.addView(iv);
        }
    }
     
  
 
    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> mData;
        public ViewPagerAdapter(List<View> mData) {
            this.mData = mData;
        }
 
        public int getCount() {
            return mData.size();
        }
 
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
         
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mData.get(position);
            container.addView(v);
            return v;
        }
         
        public void destroyItem(ViewGroup container, int position, Object object) {
//          super.destroyItem(container, position, object);
            container.removeView(mData.get(position));
        }
         
    }
     
    private class ViewPageChangeListener implements OnPageChangeListener {
 
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
 
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
 
        //监听页面改变事件来改变viewIndicator中的指示图片
        @Override
        public void onPageSelected(int arg0) {
            int len = viewIndicator.getChildCount();
            for(int i = 0; i < len; ++i)
                viewIndicator.getChildAt(i).setBackgroundResource(R.drawable.point_normal);
            viewIndicator.getChildAt(arg0).setBackgroundResource(R.drawable.point_select);
        }
         
    }
     
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch(msg.what) {
            case 1:
                int totalcount = pagers.size();//autoChangeViewPager.getChildCount();
                int currentItem = autoChangeViewPager.getCurrentItem();
                 
                int toItem = currentItem + 1 == totalcount ? 0 : currentItem + 1;
                 
                Log.i(TAG, "totalcount: " + totalcount + "   currentItem: " + currentItem + "   toItem: " + toItem);
                 
                autoChangeViewPager.setCurrentItem(toItem, true);
                 
                //每两秒钟发送一个message，用于切换viewPager中的图片
                this.sendEmptyMessageDelayed(1, 2000);
            }
        }
    };


}
