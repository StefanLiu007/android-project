package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.Field;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.interfaces.OnTagBtnClickListener;
import com.example.leohuang.password_manager.utils.DialogUtils;
import com.example.leohuang.password_manager.utils.Setting;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Map;

/**
 * Created by leo.huang on 16/3/29.
 */
public class DisplayItemInfoAdapter extends RecyclerView.Adapter<DisplayItemInfoAdapter.ViewHolder> {
    private final String TAG = "DataLoggingAdapter";
    //头
    private static final int IS_HEADER = 1;
    //密码
    private static final int IS_PASSWORD = 2;
    //文本
    private static final int IS_TEXT = 3;
    //文件
    private static final int IS_FILE = 4;
    //网址
    private static final int IS_URL = 5;
    //单选
    private static final int IS_RADIO = 6;
    //多选
    private static final int IS_CHECKBOX = 7;
    //下拉列表
    private static final int IS_SELECT = 8;
    //文本域
    private static final int IS_TEXTAREA = 9;
    //邮箱
    private static final int IS_EMAIL = 10;
    //数字
    private static final int IS_NUMBER = 11;
    //日期
    private static final int IS_DATE = 12;
    //颜色
    private static final int IS_COLOR = 13;
    //邮编
    private static final int IS_POSTCODE = 14;
    //尾
    private static final int IS_FOOTER = 0;

    private Context context;
    private Item mItem;
    private Map<String, Integer> icons;
    private MyFlowAdapter mAdapter;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public DisplayItemInfoAdapter(Context context, Item mItem, Map<String, Integer> icons) {
        this.context = context;
        this.mItem = mItem;
        this.icons = icons;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //如果是头
        if (holder.getAdapterPosition() == 0 && holder.viewType == IS_HEADER) {
            if (icons.get(mItem.icon) != null) {
                holder.ivIcon.setImageResource(icons.get(mItem.icon));
            } else {
                holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
            }
            holder.tvTitle.setText(mItem.name);
            holder.tvDate.setText(mItem.create_time);
        } else if (position == mItem.fields.size() + 1 && holder.viewType == IS_FOOTER) {
            if (mItem.remark != null) {
                holder.tvRemarkShow.setText(mItem.remark.value);
            }
            mAdapter = new MyFlowAdapter(context, mItem.labels);
            holder.mTagFlowLaout.setAdapter(mAdapter);
        } else if (position != 0 && position != mItem.fields.size() + 1) {
            Field field = mItem.fields.get(position - 1);
            switch (holder.viewType) {
                case IS_PASSWORD://密码
                    showField(holder, field, IS_PASSWORD);
                    break;
                case IS_TEXT://文本
                    showField(holder, field, IS_TEXT);
                    break;
                case IS_FILE://文件
                    showField(holder, field, IS_FILE);
                    break;
                case IS_URL://url
                    showField(holder, field, IS_URL);
                    break;
                case IS_RADIO://单选框
                    showField(holder, field, IS_RADIO);
                    break;
                case IS_CHECKBOX://多选框
                    showField(holder, field, IS_CHECKBOX);
                    break;
                case IS_SELECT://下拉
                    showField(holder, field, IS_SELECT);
                    break;
                case IS_TEXTAREA://文本域
                    showField(holder, field, IS_TEXTAREA);
                    break;
                case IS_EMAIL://电子邮箱
                    showField(holder, field, IS_EMAIL);
                    break;
                case IS_NUMBER://数字
                    showField(holder, field, IS_NUMBER);
                    break;
                case IS_DATE://日期
                    showField(holder, field, IS_DATE);
                    break;
                case IS_COLOR://颜色
                    showField(holder, field, IS_COLOR);
                    break;
                case IS_POSTCODE://邮编
                    showField(holder, field, IS_POSTCODE);
                    break;
                default:
                    break;
            }

        }
    }

    private void showField(final ViewHolder holder, Field field, int type) {
        if (type == IS_PASSWORD) {
            if (field.value != null) {
                holder.tvDes.setText(field.name);
                holder.tvContent.setText(new String(field.value));
                holder.tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mListener != null) {
                            mListener.onLongClick(holder.tvContent.getText().toString().trim());
                        }
                        return true;
                    }
                });
                holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 16/4/14 添加单击事件;
                        holder.close = !holder.close;
                        if (mListener != null) {
                            mListener.onClick(holder.ivIcon, holder.tvContent, holder.close);
                        }
                    }
                });
            }
        } else {
            if (field.value != null) {
                holder.tvDes.setText(field.name);
                holder.tvContent.setText(new String(field.value));
                holder.tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mListener != null) {
                            mListener.onLongClick(holder.tvContent.getText().toString().trim());
                        }
                        return true;
                    }
                });
            }

        }
    }


    @Override
    public int getItemCount() {
        return mItem.fields.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: 16/3/29 进行类型判断
        if (position == 0) {
            return IS_HEADER;
        } else if (position == mItem.fields.size() + 1) {
            return IS_FOOTER;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_PASSWORD)) {
            return IS_PASSWORD;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_TEXT)) {
            return IS_TEXT;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_FILE)) {
            return IS_FILE;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_URL)) {
            return IS_URL;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_RADIO)) {
            return IS_RADIO;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_CHECKBOX)) {
            return IS_CHECKBOX;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_SELECT)) {
            return IS_SELECT;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_TEXTAREA)) {
            return IS_TEXTAREA;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_EMAIL)) {
            return IS_EMAIL;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_NUMBER)) {
            return IS_NUMBER;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_DATE)) {
            return IS_DATE;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_COLOR)) {
            return IS_COLOR;
        } else if (mItem.fields.get(position - 1).type.equals(Setting.IS_POSTCODE)) {
            return IS_POSTCODE;
        }
        return super.getItemViewType(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;

        switch (viewType) {
            case IS_HEADER:
                View headerView = LayoutInflater.from(context).inflate(R.layout.display_item_title, parent, false);
                holder = new ViewHolder(headerView, viewType);
                break;
            case IS_PASSWORD:
                View imageItem = LayoutInflater.from(context).inflate(R.layout.display_item_password, parent, false);
                holder = new ViewHolder(imageItem, viewType);
                break;
            case IS_DATE:
            case IS_TEXT:
            case IS_FILE:
            case IS_URL:
            case IS_TEXTAREA:
            case IS_EMAIL:
            case IS_NUMBER:
            case IS_COLOR:
            case IS_POSTCODE:
            case IS_RADIO:
            case IS_CHECKBOX:
            case IS_SELECT:
                View chooseItem = LayoutInflater.from(context).inflate(R.layout.display_item_nornal, parent, false);
                holder = new ViewHolder(chooseItem, viewType);//nornal
                break;
            case IS_FOOTER:
                View tagRemarkItem = LayoutInflater.from(context).inflate(R.layout.remark_tag_layout, parent, false);
                holder = new ViewHolder(tagRemarkItem, viewType);//footer
                break;
            default:
                break;
        }
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvDes, tvContent, tvTitle, tvDate, tvRemarkShow;
        TagFlowLayout mTagFlowLaout;
        View view;
        boolean close = false;
        int viewType;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            switch (viewType) {
                case IS_HEADER:

                    ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    tvDate = (TextView) itemView.findViewById(R.id.tv_date);
                    break;
                case IS_PASSWORD:
                    tvDes = (TextView) itemView.findViewById(R.id.tv_description);
                    tvContent = (TextView) itemView.findViewById(R.id.tv_content);
                    ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
                    break;
                case IS_DATE:
                case IS_TEXT:
                case IS_FILE:
                case IS_URL:
                case IS_TEXTAREA:
                case IS_EMAIL:
                case IS_NUMBER:
                case IS_COLOR:
                case IS_POSTCODE:
                case IS_RADIO:
                case IS_CHECKBOX:
                case IS_SELECT:
                    tvDes = (TextView) itemView.findViewById(R.id.tv_description);
                    tvContent = (TextView) itemView.findViewById(R.id.tv_content);
                    break;

                case IS_FOOTER:
                    tvRemarkShow = (TextView) itemView.findViewById(R.id.tv_remark_show);
                    mTagFlowLaout = (TagFlowLayout) itemView.findViewById(R.id.flowLayout_tag);

                    break;

                default:
                    break;
            }
        }
    }


    public interface OnItemClickListener {
        /**
         * 单击事件
         */
        void onClick(ImageView ivicon, TextView tvPassword, boolean close);

        /**
         * 长按事件
         */
        void onLongClick(String text);
    }
}
