package com.example.leohuang.password_manager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.Field;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.bean.Template;
import com.example.leohuang.password_manager.interfaces.OnTagBtnClickListener;
import com.example.leohuang.password_manager.utils.DialogUtils;
import com.example.leohuang.password_manager.utils.GuidBuilder;
import com.example.leohuang.password_manager.utils.Setting;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leo.huang on 16/3/29.
 */
public class DataLoggingAdapter extends RecyclerView.Adapter<DataLoggingAdapter.ViewHolder> {
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


    private Model mModel;//模板
    private Context context;
    private Item mItem;//项目
    private List<Field> fields;//所有的字段
    private Map<String, Integer> icons;
    //    private Field remark;
    private OnTagBtnClickListener mListener;
    private MyFlowAdapter myFlowAdapter;
    private List<Template> customTemplate;

    public DataLoggingAdapter(Context context, Model mModel, Item mItem, Map<String, Integer> icons, List<Template> customTemplate) {
        this.context = context;
        this.mModel = mModel;
        this.mItem = mItem;
        this.icons = icons;
        this.customTemplate = customTemplate;
        fields = new ArrayList<>();
        int len = mModel.templates.size() + customTemplate.size();
        int modelTempLen = mModel.templates.size();
        int customLen = customTemplate.size();
        if (mItem.fields == null) {//是新创建的
            for (int i = 0; i < len; i++) {//实例化出所有字段
                Field field = new Field();
                field.guid = GuidBuilder.guidGenerator();
                field.index = i + 1;
                fields.add(field);
            }
        } else {//判断固定模板是否数据,已经有数据
            for (int i = 0; i < modelTempLen; i++) {
                Template template = null;
                int size = fields.size();
                template = mModel.templates.get(i);//从模板中拿到
                //先添加内置模板
                for (int j = 0; j < mItem.fields.size(); j++) {//得到item中的所有字段
                    Field field = mItem.fields.get(j);
                    if (template.name.equals(field.name) && field.custom == Setting.NOT_CUSTOM) {
                        fields.add(field);
                    }
                }
                //后添加自动模板
                if (size == fields.size()) {
                    Field field = new Field();
                    field.index = i + 1;
                    field.guid = GuidBuilder.guidGenerator();
                    fields.add(field);
                }//判断是否添加了
            }
            for (int j = 0; j < mItem.fields.size(); j++) {
                Field field = mItem.fields.get(j);
                if (field.custom == Setting.CUSTOM) {//添加自定义字段
                    fields.add(field);
                }
            }
//            if (mItem.fields.get(mItem.fields.size() - 1).name.equals("备注")) {
//                fields.add(mItem.fields.get(mItem.fields.size() - 1));
//            }
        }

        mItem.fields = fields;
        Log.i(TAG, "fields.size()" + fields.size());
    }

    public void setListener(OnTagBtnClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        //如果是头
        int size = holder.getAdapterPosition();
        if (size == 0 && holder.viewType == IS_HEADER) {
//            final Field f = fields.get(0);
            if (icons.get(mModel.icon) != null) {
                holder.ivIcon.setImageResource(icons.get(mModel.icon));
            } else {
                holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
            }
            holder.tvDes.setText(mModel.name);
            holder.etContent.requestFocus();
            holder.etContent.requestFocusFromTouch();

            if (mItem.name == null) {
                mItem.name = mModel.name;
            }
            holder.etContent.setText(mItem.name);
            holder.etContent.selectAll();
            holder.etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mItem.name = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } else if (size == mModel.templates.size() + customTemplate.size() + 1 && holder.viewType == IS_FOOTER) {//如果是尾部
            if (mItem.remark != null) {
                holder.tvRemark.setText("编辑备注");
                holder.tvRemarkShow.setText(mItem.remark.value);//填写数据;
                holder.llRemark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Field field = fields.get(size);

                        // TODO: 16/3/29 进行备注填写
                        DialogUtils.showRemark(context, mItem, holder.tvRemarkShow);

                    }
                });
            } else {
                holder.llRemark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // TODO: 16/3/29 进行备注填写
                        DialogUtils.showRemark(context, mItem, holder.tvRemarkShow);

                    }
                });
            }
            myFlowAdapter = new MyFlowAdapter(context, mItem.labels);
            if (mItem.labels != null)
                Log.i(TAG, mItem.labels.toString());
            holder.tagFlow.setAdapter(myFlowAdapter);
            holder.llTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 16/3/30 进行标签添加
                    mListener.onTagBtnClicl(context, myFlowAdapter, holder.tagFlow);
                }
            });
        } else if (size > 0 && size < mModel.templates.size() + 1) {
            final Template template = mModel.templates.get(holder.getAdapterPosition() - 1);
            final Field field = fields.get(size - 1);
            if (field.value == null) {
                field.name = template.name;
                field.type = template.type;
                field.custom = template.custom;
                field.item_id = mItem.guid;
            }
            switch (holder.viewType) {
                case IS_PASSWORD://密码
                    holder.ivIcon.setImageResource(R.drawable.image_password_selector);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 跳转到密码生成器上
                            DialogUtils.secretGeneratorTwo(context, holder.etContent, true, null);
                        }
                    });
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    holder.etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    holder.etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                holder.etContent.setTransformationMethod(null);
                            } else {
                                holder.etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                        }
                    });
                    getInfo(holder.etContent, field);
                    break;
                case IS_TEXT://文本
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.etContent.setHint(template.name);
                    holder.tvDes.setText(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_FILE://文件
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_URL://url
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint("http://www.example.com");
                    getInfo(holder.etContent, field);
                    break;
                case IS_RADIO://单选框
                    if (field.value != null) {
                        holder.tvResult.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.item_icon_more);
                    holder.tvDes.setText(template.name);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 多选 ，选择完成后进行填写
                            DialogUtils.sexChoose(context, holder.tvResult);
                        }
                    });
                    getInfo(holder.tvResult, field);
                    break;
                case IS_CHECKBOX://多选框
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.item_icon_more);
                    holder.tvDes.setText(template.name);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 多选 ，选择完成后进行填写
                        }
                    });
                    getInfo(holder.etContent, field);
                    break;
                case IS_SELECT://下拉
                    if (field.value != null) {
                        holder.tvResult.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.item_icon_more);
                    holder.tvDes.setText(template.name);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 多选 ，选择完成后进行填写
                            choose(holder.tvResult, template);
                        }
                    });
                    getInfo(holder.tvResult,field);
                    break;
                case IS_TEXTAREA://文本域
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_EMAIL://电子邮箱
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_NUMBER://数字
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    holder.etContent.setImeOptions(InputType.TYPE_CLASS_NUMBER);
                    getInfo(holder.etContent, field);
                    break;
                case IS_DATE://日期
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.image_calendar_selector);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.showAndGetDataTime(context, holder.etContent);
                        }
                    });
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_COLOR://颜色
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_POSTCODE://邮编
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    holder.etContent.setImeOptions(InputType.TYPE_CLASS_NUMBER);
                    getInfo(holder.etContent, field);
                    break;
                default:
                    break;
            }
        } else if (size >= mModel.templates.size() + 1 && size < getItemCount()) {//拿到自定义的属性 在自定义属性中出错
            final Template template = customTemplate.get(size - mModel.templates.size() - 1);
            final Field field = fields.get(size - 1);
            if (field.value == null) {
                field.name = template.name;
                field.type = template.type;
                field.custom = template.custom;
                field.item_id = mItem.guid;
            }
            switch (holder.viewType) {
                case IS_PASSWORD://密码
                    holder.ivIcon.setImageResource(R.drawable.image_password_selector);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 跳转到密码生成器上
                            DialogUtils.secretGeneratorTwo(context, holder.etContent, true, null);
                        }
                    });
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    holder.etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    holder.etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                holder.etContent.setTransformationMethod(null);
                            } else {
                                holder.etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                        }
                    });
                    getInfo(holder.etContent, field);
                    break;
                case IS_TEXT://文本
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.etContent.setHint(template.name);
                    holder.tvDes.setText(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_FILE://文件
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_URL://url
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint("http://www.example.com");
                    getInfo(holder.etContent, field);
                    break;
                case IS_RADIO://单选框
                    if (field.value != null) {
                        holder.tvResult.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.item_icon_more);
                    holder.tvDes.setText(template.name);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 多选 ，选择完成后进行填写
                            DialogUtils.sexChoose(context, holder.tvResult);
                        }
                    });
                    break;
                case IS_CHECKBOX://多选框
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.item_icon_more);
                    holder.tvDes.setText(template.name);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 多选 ，选择完成后进行填写
                        }
                    });
                    break;
                case IS_SELECT://下拉
                    if (field.value != null) {
                        holder.tvResult.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.item_icon_more);
                    holder.tvDes.setText(template.name);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 16/3/29 多选 ，选择完成后进行填写
                            choose(holder.tvResult, template);

                        }
                    });
                    getInfo(holder.tvResult, field);
                    break;
                case IS_TEXTAREA://文本域
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_EMAIL://电子邮箱
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_NUMBER://数字
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    holder.etContent.setImeOptions(InputType.TYPE_CLASS_NUMBER);
                    getInfo(holder.etContent, field);
                    break;
                case IS_DATE://日期
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.ivIcon.setImageResource(R.drawable.image_calendar_selector);
                    holder.ivIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.showAndGetDataTime(context, holder.etContent);
                        }
                    });
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_COLOR://颜色
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    getInfo(holder.etContent, field);
                    break;
                case IS_POSTCODE://邮编
                    if (field.value != null) {
                        holder.etContent.setText(new String(field.value).toString());
                    }
                    holder.tvDes.setText(template.name);
                    holder.etContent.setHint(template.name);
                    holder.etContent.setImeOptions(InputType.TYPE_CLASS_NUMBER);
                    getInfo(holder.etContent, field);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {

        return mModel.templates.size() + customTemplate.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: 16/3/29 进行类型判断
        if (position == 0) {
            return IS_HEADER;
        } else if (position == mModel.templates.size() + customTemplate.size() + 1) {
            return IS_FOOTER;
        } else if (position > 0 && position < mModel.templates.size() + 1) {
            if (mModel.templates.get(position - 1).type.equals(Setting.IS_PASSWORD)) {
                return IS_PASSWORD;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_TEXT)) {
                return IS_TEXT;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_FILE)) {
                return IS_FILE;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_URL)) {
                return IS_URL;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_RADIO)) {
                return IS_RADIO;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_CHECKBOX)) {
                return IS_CHECKBOX;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_SELECT)) {
                return IS_SELECT;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_TEXTAREA)) {
                return IS_TEXTAREA;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_EMAIL)) {
                return IS_EMAIL;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_NUMBER)) {
                return IS_NUMBER;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_DATE)) {
                return IS_DATE;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_COLOR)) {
                return IS_COLOR;
            } else if (mModel.templates.get(position - 1).type.equals(Setting.IS_POSTCODE)) {
                return IS_POSTCODE;
            }
        } else if (position >= mModel.templates.size() + 1 && position < mModel.templates.size() + customTemplate.size() + 1) {
            if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_PASSWORD)) {
                return IS_PASSWORD;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_TEXT)) {
                return IS_TEXT;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_FILE)) {
                return IS_FILE;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_URL)) {
                return IS_URL;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_RADIO)) {
                return IS_RADIO;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_CHECKBOX)) {
                return IS_CHECKBOX;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_SELECT)) {
                return IS_SELECT;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_TEXTAREA)) {
                return IS_TEXTAREA;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_EMAIL)) {
                return IS_EMAIL;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_NUMBER)) {
                return IS_NUMBER;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_DATE)) {
                return IS_DATE;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_COLOR)) {
                return IS_COLOR;
            } else if (customTemplate.get(position - mModel.templates.size() - 1).type.equals(Setting.IS_POSTCODE)) {
                return IS_POSTCODE;
            }
        }
        return super.getItemViewType(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;

        switch (viewType) {
            case IS_HEADER:
                View headerView = LayoutInflater.from(context).inflate(R.layout.title_view_layout, parent, false);
                holder = new ViewHolder(headerView, viewType);
                break;
            case IS_PASSWORD:
            case IS_DATE:
                View imageItem = LayoutInflater.from(context).inflate(R.layout.item_view_image_layout, parent, false);
                holder = new ViewHolder(imageItem, viewType);
                break;

            case IS_TEXT:
            case IS_FILE:
            case IS_URL:
            case IS_TEXTAREA:
            case IS_EMAIL:
            case IS_NUMBER:
            case IS_COLOR:
            case IS_POSTCODE:
                View textItem = LayoutInflater.from(context).inflate(R.layout.item_view_layout, parent, false);
                holder = new ViewHolder(textItem, viewType);
                break;
            case IS_RADIO:
            case IS_CHECKBOX:
            case IS_SELECT:
                View chooseItem = LayoutInflater.from(context).inflate(R.layout.item_view_choose_layout, parent, false);
                holder = new ViewHolder(chooseItem, viewType);
                break;

            case IS_FOOTER:
                View tagRemarkItem = LayoutInflater.from(context).inflate(R.layout.remark_tag_layout, parent, false);
                holder = new ViewHolder(tagRemarkItem, viewType);
                break;

            default:
                break;
        }
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        EditText etContent,tvResult;
        TextView tvDes, tvTag, tvRemark, tvRemarkShow;
        View view;

        LinearLayout llTag, llRemark;
        TagFlowLayout tagFlow;

        int viewType;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            switch (viewType) {
                case IS_HEADER:
                case IS_PASSWORD:
                case IS_DATE:
                    ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
                    tvDes = (TextView) itemView.findViewById(R.id.tv_description);
                    etContent = (EditText) itemView.findViewById(R.id.et_content);
                    break;

                case IS_TEXT:
                case IS_FILE:
                case IS_URL:
                case IS_TEXTAREA:
                case IS_EMAIL:
                case IS_NUMBER:
                case IS_COLOR:
                case IS_POSTCODE:
                    tvDes = (TextView) itemView.findViewById(R.id.tv_description);
                    etContent = (EditText) itemView.findViewById(R.id.et_content);
                    break;


                case IS_RADIO:
                case IS_CHECKBOX:
                case IS_SELECT:
                    tvDes = (TextView) itemView.findViewById(R.id.tv_description);
                    tvResult = (EditText) itemView.findViewById(R.id.tv_result);
                    ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
                    //view = itemView.findViewById(R.id.view);
                    break;

                case IS_FOOTER:
                    tvTag = (TextView) itemView.findViewById(R.id.tv_tag);
                    tvRemark = (TextView) itemView.findViewById(R.id.tv_remark);

                    llRemark = (LinearLayout) itemView.findViewById(R.id.ll_remark);
                    llTag = (LinearLayout) itemView.findViewById(R.id.ll_tag);

                    tvRemarkShow = (TextView) itemView.findViewById(R.id.tv_remark_show);
                    tagFlow = (TagFlowLayout) itemView.findViewById(R.id.flowLayout_tag);
                    break;

                default:
                    break;
            }
        }
    }

    private void getInfo(EditText editText, final Field field) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                field.value = s.toString().getBytes();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void choose(EditText textView, Template template) {
        String[] datas = null;
        switch (template.id) {
            case 195:
                datas = context.getResources().getStringArray(R.array.Verification195);
                break;
            case 189:
                datas = context.getResources().getStringArray(R.array.Verification189);
                break;
            case 188:
                datas = context.getResources().getStringArray(R.array.ssl188);
                break;
            case 194:
                datas = context.getResources().getStringArray(R.array.ssl194);
                break;
            case 183:
                datas = context.getResources().getStringArray(R.array.type183);
                break;
            case 121:
                datas = context.getResources().getStringArray(R.array.wireless121);
                break;
            case 97:
                datas = context.getResources().getStringArray(R.array.data97);
                break;
            case 6:
                datas = context.getResources().getStringArray(R.array.bank6);
                break;
            case 15:
                datas = context.getResources().getStringArray(R.array.type15);
                break;
            default:
                datas = null;
                break;
        }
        DialogUtils.showChoose(context, textView, datas, "选择" + template.name);

    }
}