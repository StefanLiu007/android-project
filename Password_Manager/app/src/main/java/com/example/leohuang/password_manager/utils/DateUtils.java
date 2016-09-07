package com.example.leohuang.password_manager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leo.huang on 16/4/7.
 */
public class DateUtils {
    public static String getFormatDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    public static boolean timeIsAfter(String first, String second) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstDate = null;
        Date secondDate = null;
        boolean flag = false;
        try {
            firstDate = format.parse(first);
            secondDate = format.parse(second);
            if (firstDate.after(secondDate)) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
