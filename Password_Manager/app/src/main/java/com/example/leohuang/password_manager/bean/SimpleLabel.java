package com.example.leohuang.password_manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by leo.huang on 16/4/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleLabel implements Parcelable {
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;

    @JsonCreator
    public SimpleLabel() {

    }

    @JsonCreator
    public SimpleLabel(@JsonProperty("id") String id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SimpleLabel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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
    }

    protected SimpleLabel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

}
