package com.example.leohuang.password_manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jack on 16/4/12.
 */
public class Remark implements Parcelable {
    public String id;
    public  String value;
    public String item_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.value);
        dest.writeString(this.item_id);
    }

    public Remark() {
    }

    protected Remark(Parcel in) {
        this.id = in.readString();
        this.value = in.readString();
        this.item_id = in.readString();
    }

    public static final Parcelable.Creator<Remark> CREATOR = new Parcelable.Creator<Remark>() {
        @Override
        public Remark createFromParcel(Parcel source) {
            return new Remark(source);
        }

        @Override
        public Remark[] newArray(int size) {
            return new Remark[size];
        }
    };
}
