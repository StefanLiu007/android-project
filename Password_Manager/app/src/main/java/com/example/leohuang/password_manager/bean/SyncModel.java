package com.example.leohuang.password_manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by leo.huang on 16/4/5.
 */
public class SyncModel implements Parcelable {
    public String ipAddress;
    public String name;

    public SyncModel(String ipAddress, String name) {
        this.ipAddress = ipAddress;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ipAddress);
        dest.writeString(this.name);
    }

    protected SyncModel(Parcel in) {
        this.ipAddress = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<SyncModel> CREATOR = new Parcelable.Creator<SyncModel>() {
        @Override
        public SyncModel createFromParcel(Parcel source) {
            return new SyncModel(source);
        }

        @Override
        public SyncModel[] newArray(int size) {
            return new SyncModel[size];
        }
    };
}
