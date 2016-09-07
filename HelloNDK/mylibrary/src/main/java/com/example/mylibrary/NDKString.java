package com.example.mylibrary;

/**
 * Created by Stefan on 2016/7/25.
 */
public class NDKString {

    static {
        System.loadLibrary("mylibrary");
    }
    public static native String NDKFromC();
}
