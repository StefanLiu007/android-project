package com.example.leohuang.password_manager.algorithm;

import java.util.Random;

/**
 * 密码生成
 */
public class PasswordGenerate {
    //    private static final String PWD_CHAR_TABLE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*?";
    private static final Random random = new Random(System.currentTimeMillis());

    public static String generate(int paramInt) {
        if (paramInt <= 0)
            return null;
        char[] arrayOfChar1 = "0123456789".toCharArray();
        int i = arrayOfChar1.length;
        char[] arrayOfChar5 = new char[paramInt];
        for (int j = 0; ; j++) {
            if (j >= paramInt)                                                        //只有数字
                return new String(arrayOfChar5);
            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
        }
    }

    public static String generate2(int paramInt) {
        if (paramInt <= 0)
            return null;
        char[] arrayOfChar1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int i = arrayOfChar1.length;
        char[] arrayOfChar5 = new char[paramInt];                                    //只有字母
        for (int j = 0; ; j++) {
            if (j >= paramInt)
                return new String(arrayOfChar5);
            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
        }
    }

    public static String generate3(int paramInt) {
        if (paramInt <= 0)
            return null;
        char[] arrayOfChar1 = "~!@#$%^&*?".toCharArray();
        int i = arrayOfChar1.length;
        char[] arrayOfChar5 = new char[paramInt];                                    //只有符号
        for (int j = 0; ; j++) {
            if (j >= paramInt)
                return new String(arrayOfChar5);
            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
        }
    }

    public static String generate4(int paramInt) {
        if (paramInt <= 0)
            return null;
        char[] arrayOfChar1 = "0123456789".toCharArray();
        char[] arrayOfChar2 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int i = arrayOfChar1.length;
        int i1 = arrayOfChar2.length;
        char[] arrayOfChar5 = new char[paramInt / 2];                                    //数字加字母
        char[] arrayOfChar6 = new char[paramInt - paramInt / 2];
        for (int j = 0; ; j++) {
            if (j >= paramInt / 2) {
                return new String(arrayOfChar6) + new String(arrayOfChar5);
            }
            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
            arrayOfChar6[j] = arrayOfChar2[random.nextInt(i1)];
//            if (j >= paramInt / 2) {
//               return new  String(arrayOfChar5);
//            }
//            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
//            if (paramInt % 2 == 0) {
//                if (j>=paramInt/2){
//                    return new String(arrayOfChar6);
//                }
//                arrayOfChar6[j] = arrayOfChar2[random.nextInt(i1)];
//            }else {
//                if(j>=paramInt/2+1){
//                    return new String(arrayOfChar6);
//                }
//                arrayOfChar6[j] = arrayOfChar2[random.nextInt(i1)];
//            }
//            return new String(arrayOfChar6) + new String(arrayOfChar5);
        }
    }

    public static String generate5(int paramInt) {
        if (paramInt <= 0)
            return null;
        char[] arrayOfChar1 = "0123456789".toCharArray();
        char[] arrayOfChar2 = "~!@#$%^&*?".toCharArray();
        int i = arrayOfChar1.length;
        int i1 = arrayOfChar2.length;
        char[] arrayOfChar5 = new char[paramInt / 2];                                    //数字加符号
        char[] arrayOfChar6 = new char[paramInt - paramInt / 2];
        for (int j = 0; ; j++) {
            if (j >= paramInt / 2)
                return new String(arrayOfChar5) + new String(arrayOfChar6);
            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
            arrayOfChar6[j] = arrayOfChar2[random.nextInt(i1)];
        }
    }

    public static String generate6(int paramInt) {
        if (paramInt <= 0)
            return null;
        char[] arrayOfChar1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] arrayOfChar2 = "~!@#$%^&*?".toCharArray();
        int i = arrayOfChar1.length;
        int i1 = arrayOfChar2.length;
        char[] arrayOfChar5 = new char[paramInt / 2];
        char[] arrayOfChar6 = new char[paramInt - paramInt / 2]; //字母加符号
        for (int j = 0; ; j++) {
            if (j >= paramInt / 2)
                return new String(arrayOfChar5) + new String(arrayOfChar6);
            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
            arrayOfChar6[j] = arrayOfChar2[random.nextInt(i1)];
        }
    }

    public static String generate7(int paramInt) {
        if (paramInt <= 0)
            return null;
        char[] arrayOfChar1 = "0123456789".toCharArray();
        char[] arrayOfChar2 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] arrayOfChar3 = "~!@#$%^&*?".toCharArray();
        int i = arrayOfChar1.length;
        int i1 = arrayOfChar1.length;
        int i2 = arrayOfChar1.length;
        char[] arrayOfChar5 = new char[paramInt / 3];                                    //数字加字母加符号
        char[] arrayOfChar6 = new char[paramInt / 3];
        char[] arrayOfChar7 = new char[paramInt - 2 * (paramInt / 3)];
        for (int j = 0; ; j++) {
            if (j >= paramInt / 3)
                return new String(arrayOfChar5) + new String(arrayOfChar6) + new String(arrayOfChar7);
            arrayOfChar5[j] = arrayOfChar1[random.nextInt(i)];
            arrayOfChar6[j] = arrayOfChar2[random.nextInt(i1)];
            arrayOfChar7[j] = arrayOfChar3[random.nextInt(i2)];
        }
    }


    /**
     * 生成密码
     *
     * @param hasLargeChar
     * @param hasChar
     * @param hasNumber
     * @param hasSpecial
     * @param len
     * @return
     */
    public static String generateSecret(boolean hasLargeChar, boolean hasChar, boolean hasNumber, boolean hasSpecial, int len) {
        StringBuilder builder = new StringBuilder();
        int myLen = 0;
        int largeCharIndex = -1;
        int charIndex = -1;
        int numberIndex = -1;
        int specialIndex = -1;
        //随机生成必须出现该字符的位置
        if (hasLargeChar) {//随机生成必须是大小字符的位置
            largeCharIndex = (int) (len * Math.random());
        }
        if (hasChar) {//随机生成必须是小写字符的位置
            do {
                charIndex = (int) (len * Math.random());
            } while (charIndex == largeCharIndex);
        }

        if (hasNumber) {//随机生成必须是数字的位置
            do {
                numberIndex = (int) (len * Math.random());
            }
            while ((numberIndex == largeCharIndex) || (numberIndex == charIndex));
        }

        if (hasSpecial) {//随机生成必须是特殊字符的位置
            do {
                specialIndex = (int) (len * Math.random());
            }
            while ((specialIndex == largeCharIndex) || (specialIndex == charIndex) || (specialIndex == numberIndex));
        }

        while (myLen < len) {
            int num = Math.abs(random.nextInt() % 127);

            if (num > 126 || num < 33) {//如果随机的数字超过ASCII码的可见位置
                continue;
            }

            if (myLen == largeCharIndex) {
                while (!(num >= 65 && num <= 90)) {
                    num = Math.abs(random.nextInt() % 127);
                }
            } else if (myLen == charIndex) {
                while (!(num >= 97 && num <= 122)) {
                    num = Math.abs(random.nextInt() % 127);
                }
            } else if (myLen == numberIndex) {
                while (!(num >= 48 && num <= 57)) {
                    num = Math.abs(random.nextInt() % 127);
                }
            } else if (myLen == specialIndex) {
                while (!((num >= 33 && num <= 47) || (num >= 58 && num <= 64) || (num >= 91 && num <= 96) || (num >= 123 && num <= 126))) {
                    num = Math.abs(random.nextInt() % 127);
                }
            } else {

                if (!hasLargeChar && num >= 65 && num <= 90) {//不含大写字符
                    continue;
                }
                if (!hasChar && num >= 97 && num <= 122) {
                    continue;
                }
                if (!hasNumber && num >= 48 && num <= 57) {
                    continue;
                }
                if (!hasSpecial && ((num >= 33 && num <= 47) || (num >= 58 && num <= 64) || (num >= 91 && num <= 96) || (num >= 123 && num <= 126))) {
                    continue;
                }
            }
            char c = (char) num;
            builder.append(c);
            myLen++;
        }
        return builder.toString();
    }

    /**
     * 生成
     *
     * @param hasLargeChar
     * @param hasChar
     * @param hasNumber
     * @param hasSpecial
     * @param len
     * @return
     */

    public static String generateSecretTwo(boolean hasLargeChar, boolean hasChar, boolean hasNumber, boolean hasSpecial, int len) {
        StringBuilder builder = new StringBuilder();
        int largeCharIndex = -1;
        int charIndex = -1;
        int numberIndex = -1;
        int specialIndex = -1;
        Random positionRandom = new Random(len);
        //随机生成必须出现该字符的位置
        if (hasLargeChar) {//随机生成必须是大小字符的位置
            largeCharIndex = positionRandom.nextInt();
        }
        if (hasChar) {//随机生成必须是小写字符的位置
            charIndex = positionRandom.nextInt();
            while (charIndex != largeCharIndex) {
                charIndex = positionRandom.nextInt();
            }
        }

        if (hasNumber) {//随机生成必须是数字的位置
            numberIndex = positionRandom.nextInt();
            if ((numberIndex != largeCharIndex) && (numberIndex != charIndex)) {
                numberIndex = positionRandom.nextInt();
            }
        }

        if (hasSpecial) {//随机生成必须是特殊字符的位置
            specialIndex = positionRandom.nextInt();
            if ((specialIndex != largeCharIndex) && (specialIndex != charIndex) && (specialIndex != numberIndex)) {
                specialIndex = positionRandom.nextInt();
            }
        }

        for (int i = 0; i < len; i++) {//开始随机生成

        }


        return builder.toString();
    }
}
