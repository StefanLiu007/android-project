package com.example.leohuang.password_manager.bean;

import android.os.Parcel;

import java.util.List;

/**
 * Created by Jack on 16/4/15.
 */
public class LaberAccount extends SimpleLabel{
    public int count;
    public List<String> itemId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.count);
        dest.writeStringList(this.itemId);
    }

    public LaberAccount() {
    }

    protected LaberAccount(Parcel in) {
        super(in);
        this.count = in.readInt();
        this.itemId = in.createStringArrayList();
    }

    public static final Creator<LaberAccount> CREATOR = new Creator<LaberAccount>() {
        @Override
        public LaberAccount createFromParcel(Parcel source) {
            return new LaberAccount(source);
        }

        @Override
        public LaberAccount[] newArray(int size) {
            return new LaberAccount[size];
        }
    };
}
