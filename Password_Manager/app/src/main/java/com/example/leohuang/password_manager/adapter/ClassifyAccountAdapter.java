package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.application.MyApplication;
import com.example.leohuang.password_manager.bean.AllOrSingle;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.interfaces.Back;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 16/3/29.
 */
public class ClassifyAccountAdapter extends BaseAdapter {
    List<Item> list = new ArrayList<>();
    List<Classfy> classfies1 = new ArrayList<>();
    List<Integer> l = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    ViewHolder holder;
    Back back;
    public ClassifyAccountAdapter(Context context,Back back) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (MyApplication.items != null){
            this.classfies1 = sort(MyApplication.items);
        }
        this.back = back;
    }
    public void setItems(){
        if (MyApplication.items != null){
            this.classfies1 = sort(MyApplication.items);
            notifyDataSetChanged();
        }
    }

    public List<Classfy> sort(List<Item> items) {
        List<Classfy> classfies = new ArrayList<>();
        l.clear();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (!l.contains(item.type_id)) {
                l.add(item.type_id);
            }
        }
        for (int k = 0; k < l.size(); k++) {
            int count = 0;
            Classfy classfy = new Classfy();
            ArrayList<Item> it = new ArrayList<>();
            for (int j = 0; j < items.size(); j++) {
                Item item = items.get(j);
                if (l.get(k) == item.type_id) {
                    count++;
                    it.add(item);
                    classfy.icon = item.icon;
                }

            }
            classfy.count = count;
            classfy.items = it;
            classfy.name = l.get(k);
            classfies.add(classfy);
        }
        return classfies;

    }

    @Override
    public int getCount() {
        if (classfies1 != null){
            return classfies1.size();
        }else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        if (classfies1 != null){
            return classfies1.get(position);
        }else {
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final Classfy c = classfies1.get(position);
        if (convertView==null){
            convertView=inflater.inflate(R.layout.classify_list_item,parent,false);
            holder=new ViewHolder();
            holder.account_picture= (ImageView) convertView.findViewById(R.id.account_picture);
            holder.classify_name= (TextView) convertView.findViewById(R.id.classify_name);
            holder.classify_count= (TextView) convertView.findViewById(R.id.classify_count);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.classify_name.setText(MyApplication.modelName.get(c.name));
        holder.classify_count.setText(c.count + "");
        if(MyApplication.icons.get(c.icon) != null){
            holder.account_picture.setBackgroundResource(MyApplication.icons.get(c.icon));
        }else {
            holder.account_picture.setBackgroundResource(R.mipmap.ic_launcher);
        }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = c.items.get(0).type_id;
                    back.back(type);
                    AllOrSingle allOrSingle = new AllOrSingle(type);
                    EventBus.getDefault().post(allOrSingle);

                }
            });



        return convertView;
    }

    class ViewHolder {
        ImageView account_picture;
        TextView classify_name,classify_count;
    }
    class Classfy{
        int count;
        int name;
        String icon;
        ArrayList<Item> items;
    }

}
