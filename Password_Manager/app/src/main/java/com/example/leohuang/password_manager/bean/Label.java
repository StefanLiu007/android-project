package com.example.leohuang.password_manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 标签
 * Created by Jack on 16/3/24.
 */
public class Label implements Parcelable {
    public String id;//标签的id
    public String name;//标签的名字
    public String type_id;//标签所属项目的guid
    public String relation_id;
    private int nameLength;//属性名称的长度
    private boolean nameIsSelect;//判断标签是否被选中

    @Override
    public String toString() {
        return "Label{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type_id='" + type_id + '\'' +
                ", relation_id='" + relation_id + '\'' +
                ", nameLength=" + nameLength +
                ", nameIsSelect=" + nameIsSelect +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.type_id);
        dest.writeString(this.relation_id);
        dest.writeInt(this.nameLength);
        dest.writeByte(nameIsSelect ? (byte) 1 : (byte) 0);
    }

    public Label() {
    }

    protected Label(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.type_id = in.readString();
        this.relation_id = in.readString();
        this.nameLength = in.readInt();
        this.nameIsSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Label> CREATOR = new Parcelable.Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel source) {
            return new Label(source);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };
}
