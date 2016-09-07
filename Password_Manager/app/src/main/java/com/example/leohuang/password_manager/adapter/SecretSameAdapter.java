package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.SecretSameContainer;

import java.util.List;
import java.util.Map;

/**
 * 相同密码的适配器
 * Created by leo.huang on 16/4/21.
 */
public class SecretSameAdapter extends BaseExpandableListAdapter {

    private List<SecretSameContainer> containers;
    private LayoutInflater mInflater;
    private OnParentImageClickListener mListener;
    private Map<String, Integer> icons;

    public SecretSameAdapter(Context context, List<SecretSameContainer> containers, Map<String, Integer> icons) {
        mInflater = LayoutInflater.from(context);
        this.containers = containers;
        this.icons = icons;
    }


    public void setOnImageClickListener(OnParentImageClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getGroupCount() {//父元素的个数
        if (containers != null) {
            return containers.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {//每个父元素的子元素的个数
        if (containers != null) {
            return containers.get(groupPosition).items.size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (containers != null) {
            return containers.get(groupPosition);
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (containers != null) {
            return containers.get(groupPosition).items.get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        if (containers != null) {
            return groupPosition;
        }
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (containers != null) {
            return childPosition;
        }
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {//父的布局

//        if (containers != null) {
//            View parentView = mInflater.inflate(R.layout.display_item_password, null, false);
//            final ImageView mImage = (ImageView) parentView.findViewById(R.id.iv_icon);
//            TextView tvDes = (TextView) parentView.findViewById(R.id.tv_description);
//            tvDes.setText("密码");
//            final TextView tvContent = (TextView) parentView.findViewById(R.id.tv_content);
//            tvContent.setText(containers.get(groupPosition).secret);
//            mImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mListener.onClick(mImage, tvContent, false);
//                }
//            });
//            return parentView;
//        }
//        return null;
        if (containers != null) {
            ParentViewHolder holder = null;
            if (convertView == null) {
                holder = new ParentViewHolder();
                convertView = mInflater.inflate(R.layout.display_item_password, null, false);
                holder.mImage = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tvDes = (TextView) convertView.findViewById(R.id.tv_description);
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(holder);
            } else {
                holder = (ParentViewHolder) convertView.getTag();
            }
            final ParentViewHolder finalHolder = holder;
            holder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//设置点击事件
                    finalHolder.flag = !finalHolder.flag;
                    mListener.onClick(finalHolder.mImage, finalHolder.tvContent, finalHolder.flag);
                }
            });
            finalHolder.tvDes.setText("密码");
            finalHolder.tvContent.setText(containers.get(groupPosition).secret);

            return convertView;
        }

        return null;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {//子的布局
//        if (containers != null) {
//            View childView = mInflater.inflate(R.layout.check_secret_strength_nornal, null, false);
//            ImageView ivIcon = (ImageView) childView.findViewById(R.id.iv_icon);
//            TextView tvItemName = (TextView) childView.findViewById(R.id.tv_item_name);
//            if (icons.get(containers.get(groupPosition).items.get(childPosition).icon) == null) {
//                ivIcon.setImageResource(R.mipmap.ic_launcher);
//            } else {
//                ivIcon.setImageResource(icons.get(containers.get(groupPosition).items.get(childPosition).icon));
//            }
//            tvItemName.setText(containers.get(groupPosition).items.get(childPosition).name);
//            return childView;
//        }
//        return null;
        if (containers != null) {
            ChildViewHolder holder = null;
            if (convertView == null) {
                holder = new ChildViewHolder();
                convertView = mInflater.inflate(R.layout.check_secret_strength_nornal, null, false);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tvItemName = (TextView) convertView.findViewById(R.id.tv_item_name);
                holder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_item);
                convertView.setTag(holder);
            } else {
                holder = (ChildViewHolder) convertView.getTag();
            }
            if (icons.get(containers.get(groupPosition).items.get(childPosition).icon) == null) {
                holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
            } else {
                holder.ivIcon.setImageResource(icons.get(containers.get(groupPosition).items.get(childPosition).icon));
            }
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(groupPosition, childPosition);
                    }
                }
            });
            holder.tvItemName.setText(containers.get(groupPosition).items.get(childPosition).name);
            return convertView;
        }
        return null;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class ParentViewHolder {
        boolean flag = false;
        TextView tvDes, tvContent;
        ImageView mImage;
    }

    public class ChildViewHolder {
        LinearLayout llItem;
        ImageView ivIcon;
        TextView tvItemName;
    }

    public interface OnParentImageClickListener {
        void onClick(ImageView ivicon, TextView tvPassword, boolean close);

        void onItemClick(int groupPosition, int childPosition);
    }

}
