package com.example.leohuang.password_manager.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Stream的帮助类
 * Created by leo.huang on 16/4/6.
 */
public class StreamUtils {
    /**
     * 获取in流中的内容
     *
     * @param is
     */
    public static String isToString(InputStream is) {
        int len = -1;
        byte[] buf = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] result = baos.toByteArray();
        return new String(result);
    }


    /**
     * 写数据
     *
     * @param socket
     * @param message
     */
    public static void writer(Socket socket, byte[] message) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            bos.write(intToByteArray1(message.length));
            bos.flush();
            bos.write(message);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static byte[] reader(Socket socket) {
        try {
            byte[] head = new byte[4];
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());;
            byte[] date = new byte[byteArrayToInt(head)];
            bis.read(head);
            return head;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将长度转换为byte数组
     *
     * @param i
     * @return
     */
    public  static byte[] intToByteArray1(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * 获取byte数组中的长度
     *
     * @param b
     * @return
     */
    public static int byteArrayToInt(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }



    public static byte[] intToByteArray2(int i) throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
        out.writeInt(i);
        byte[] b = buf.toByteArray();
        out.close();
        buf.close();
        return b;
    }
}
