package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;

import java.util.Map;


/**
 * Created by leo.huang on 16/3/25.
 */
public class TimerChooseAdapter extends BaseAdapter {

    private String[] times;
    private LayoutInflater mInflater;
    private Map<Integer,Boolean> chooses;
    public TimerChooseAdapter(Context context, String[] times,Map<Integer,Boolean> chooses) {
        mInflater = LayoutInflater.from(context);
        this.times = times;
        this.chooses=chooses;
    }

    @Override
    public int getCount() {
        if (times != null) {
            return times.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (times != null) {
            return times[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(times!=null){
            return  position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(times!=null){
            ViewHolder holder;
            if(convertView==null){
                holder=new ViewHolder();
                convertView=mInflater.inflate(R.layout.time_choose_item,null,false);
                holder.rb= (RadioButton) convertView.findViewById(R.id.rb);
                holder.tvTime= (TextView) convertView.findViewById(R.id.tv_time_show);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tvTime.setText(times[position]);
            holder.rb.setChecked(chooses.get(position)==null?false:true);
            return convertView;
        }
        return null;
    }

    private class ViewHolder{
        TextView tvTime;
        RadioButton rb;
    }
}
