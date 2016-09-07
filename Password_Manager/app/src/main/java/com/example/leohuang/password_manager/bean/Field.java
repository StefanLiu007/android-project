package com.example.leohuang.password_manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * 字段
 * Created by Jack on 16/3/24.
 */
public class Field implements Parcelable {
    /**
     * 唯一标识的id
     * Created by Jack on 16/3/24.
     */
    public String guid;
    /**
     * 字段名
     * Created by Jack on 16/3/24.
     */
    public String name;
    /**
     * 字段值
     * Created by Jack on 16/3/24.
     */
    public byte[] value;
    /**
     * 字段类型
     * Created by Jack on 16/3/24.
     */
    public String type;
    /**
     * 是否是自定义字段
     * Created by Jack on 16/3/24.
     */
    public int custom;
    /**
     * 排序的索引
     * Created by Jack on 16/3/24.
     */
    public int index;
    /**
     * 字段对应的项目id
     * Created by Jack on 16/3/24.
     */
    public String item_id;

    @Override
    public String toString() {
        return "Field{" +
                "guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                ", value=" + Arrays.toString(value) +
                ", type='" + type + '\'' +
                ", custom=" + custom +
                ", index=" + index +
                ", item_id='" + item_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.guid);
        dest.writeString(this.name);
        dest.writeByteArray(this.value);
        dest.writeString(this.type);
        dest.writeInt(this.custom);
        dest.writeInt(this.index);
        dest.writeString(this.item_id);
    }

    public Field() {
    }

    protected Field(Parcel in) {
        this.guid = in.readString();
        this.name = in.readString();
        this.value = in.createByteArray();
        this.type = in.readString();
        this.custom = in.readInt();
        this.index = in.readInt();
        this.item_id = in.readString();
    }

    public static final Parcelable.Creator<Field> CREATOR = new Parcelable.Creator<Field>() {
        @Override
        public Field createFromParcel(Parcel source) {
            return new Field(source);
        }

        @Override
        public Field[] newArray(int size) {
            return new Field[size];
        }
    };
}
