package com.example.leohuang.password_manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.leohuang.password_manager.events.FinishEvent;
import com.example.leohuang.password_manager.utils.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目
 * Created by Jack on 16/3/24.
 */
public class Item implements Parcelable {
    public String guid;//唯一标识的id
    public String name;//项目名称
    public String icon;//项目图标
    public String create_time;//创建时间
    public String modify_time;//修改时间
    public String access_time;//最近使用时间
    public int access_count;//使用计数
    public int is_favorited;//是否加入收藏夹
    public int favorite_index;//收藏夹的索引
    public int type_id;//项目所属类别
    public Remark remark;//备注
    public ArrayList<Label> labels;//标签
    public List<Field> fields;//字段集合
    public String sortLetters;


    /**
     * 获取自定义的属性
     *
     * @return
     */
    public List<Template> getCustoms() {
        List<Template> customs = new ArrayList<>();
        if (fields != null) {
            int len = fields.size();
            for (int i = 0; i < len; i++) {
                Field field = fields.get(i);
                if (field.custom == Setting.CUSTOM) {
                    Template template = new Template();
                    template.name = field.name;
                    template.type = field.type;
                    template.custom = field.custom;
                    customs.add(template);
                }
            }
        }
        return customs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.guid);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeString(this.create_time);
        dest.writeString(this.modify_time);
        dest.writeString(this.access_time);
        dest.writeInt(this.access_count);
        dest.writeInt(this.is_favorited);
        dest.writeInt(this.favorite_index);
        dest.writeInt(this.type_id);
        dest.writeParcelable(this.remark, flags);
        dest.writeTypedList(labels);
        dest.writeTypedList(fields);
        dest.writeString(this.sortLetters);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.guid = in.readString();
        this.name = in.readString();
        this.icon = in.readString();
        this.create_time = in.readString();
        this.modify_time = in.readString();
        this.access_time = in.readString();
        this.access_count = in.readInt();
        this.is_favorited = in.readInt();
        this.favorite_index = in.readInt();
        this.type_id = in.readInt();
        this.remark = in.readParcelable(Remark.class.getClassLoader());
        this.labels = in.createTypedArrayList(Label.CREATOR);
        this.fields = in.createTypedArrayList(Field.CREATOR);
        this.sortLetters = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public String toString() {
        return "Item{" +
                "guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                ", access_time='" + access_time + '\'' +
                ", access_count=" + access_count +
                ", is_favorited=" + is_favorited +
                ", favorite_index=" + favorite_index +
                ", type_id=" + type_id +
                ", remark=" + remark +
                ", labels=" + labels +
                ", fields=" + fields +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }
}
