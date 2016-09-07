package com.tdActivity.android.util;

import android.net.Uri;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.tdActivity.android.entity.UserInfo;

/**
 * Created by 46697 on 2015/12/4.
 */
public class UriDecodor {
    private static final String SECRET_PARAM = "secret";
    /**
     * 对扫描出来的String进行分解
     * @param uriStr 
     * @return
     */
    public static UserInfo getInfoFromUrl(String uriStr)//�?要先分解 ，在对uri编码的部分解�?
    {
    	UserInfo account=new UserInfo();
    	//先进行Uri编码
        Uri uri=Uri.parse(uriStr);
        final String  scheme =uri.getScheme().toLowerCase();//optauth
        final String path=uri.getPath();
        final String authority =uri.getAuthority(); //totp
        final String user;
        final String secret;
        //获取用户�? 对项目和账号名进行uri解码
        try {
            uriStr= URLDecoder.decode(path,"utf-8");
            System.out.println(uriStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user=validateAndGetUserInPath(path);
        String[] strInfo=getUserInPath(user);
        secret = uri.getQueryParameter(SECRET_PARAM);
        account.yy=(strInfo[0]);
        System.out.println("pname+++++"+strInfo[0]);
        account.name=(strInfo[1]);
        account.password=(secret);
        return account;
    }

    private static String validateAndGetUserInPath(String path) {
        if (path == null || !path.startsWith("/")) {
            return null;
        }
        // path is "/user", so remove leading "/", and trailing white spaces
        String user = path.substring(1).trim();
        if (user.length() == 0) {
            return null; // only white spaces.
        }
        return user;
    }

    private static String[] getUserInPath(String path)
    {
        String[] info=path.split(":");
        return info;
    }
}
