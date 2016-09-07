package com.example.leohuang.password_manager.utils;

import java.util.regex.Pattern;

/**
 * 密码帮助类
 * Created by leo.huang on 16/4/18.
 */
public class SecretUtils {
    /**
     * 正则表达式
     */
    private final static String lowRegex = "^((?=.*[A-Z])|(?=.*[a-z])|(?=.*[0-9])|(?=.*[!-/:-@\\[-`\\{-~])){6,12}$";
    private final static String highRegex = "";

    /**
     * 判断密码强度是否过低
     *
     * @param sectet
     * @return
     */
    public static int getLevel(String sectet) {
        byte level = 1;
        int secretLevel = Setting.SECRET_LOW;
        char[] chars = sectet.toCharArray();
        int len = chars.length;
        boolean hasLargeChar = hasLargeChar(chars);
        boolean hasChar = hasChar(chars);
        boolean hasNumber = hasNumber(chars);
        boolean hasSpecial = hasSpecial(chars);

        if (hasLargeChar) {
            level <<= 1;
        }
        if (hasChar) {
            level <<= 1;
        }
        if (hasNumber) {
            level <<= 1;
        }
        if (hasSpecial) {
            level <<= 1;
        }

        switch (level) {
            case 2://只有一种
                if (len >= 12) {
                    secretLevel = Setting.SECRET_MIDDLE;
                }
                break;
            case 4://两种

            case 8://三种
               
            case 16://四种
                if (len >= 12) {
                    secretLevel = Setting.SECRET_HIGH;
                } else if (len >= 6) {
                    secretLevel = Setting.SECRET_MIDDLE;
                }
                break;
            default:
                break;
        }
        return secretLevel;
    }


    /**
     * 判断是否有大写
     *
     * @param secret
     * @return
     */
    public static boolean hasLargeChar(char[] secret) {
        boolean isHad = false;
        int len = secret.length;
        for (int i = 0; i < len; i++) {
            if (secret[i] >= 'A' && secret[i] <= 'Z') {
                isHad = true;
                break;
            }
        }
        return isHad;
    }

    /**
     * 是否有小写
     *
     * @param secret
     * @return
     */
    public static boolean hasChar(char[] secret) {
        boolean isHad = false;
        int len = secret.length;
        for (int i = 0; i < len; i++) {
            if (secret[i] >= 'a' && secret[i] <= 'z') {
                isHad = true;
                break;
            }
        }
        return isHad;
    }

    /**
     * 是否有数字
     *
     * @param secret
     * @return
     */
    public static boolean hasNumber(char[] secret) {
        boolean isHad = false;
        int len = secret.length;
        for (int i = 0; i < len; i++) {
            if (secret[i] >= '0' && secret[i] <= '9') {
                isHad = true;
                break;
            }
        }
        return isHad;
    }

    /**
     * 是否有特殊字符
     *
     * @param secret
     * @return
     */
    public static boolean hasSpecial(char[] secret) {
        boolean isHad = false;
        int len = secret.length;
        for (int i = 0; i < len; i++) {
            if ((secret[i] >= '!' && secret[i] <= '/') || (secret[i] >= ':' && secret[i] <= '@') ||
                    (secret[i] >= '[' && secret[i] <= '`') || (secret[i] >= '{' && secret[i] <= '~')) {
                isHad = true;
                break;
            }
        }
        return isHad;
    }

}
