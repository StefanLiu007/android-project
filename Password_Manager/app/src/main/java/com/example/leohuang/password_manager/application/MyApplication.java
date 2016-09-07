package com.example.leohuang.password_manager.application;

import android.app.Application;
import android.content.ClipboardManager;

import com.example.leohuang.password_manager.R;
import com.example.leohuang.password_manager.bean.Item;
import com.example.leohuang.password_manager.bean.Model;
import com.example.leohuang.password_manager.bean.SimpleLabel;
import com.example.leohuang.password_manager.bean.User;
import com.example.leohuang.password_manager.db.DatabaseManager;
import com.example.leohuang.password_manager.utils.PrefUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Application
 * Created by leo.huang on 16/3/24.
 */
public class MyApplication extends Application {

    public static List<Item> items;
    public List<SimpleLabel> labels;
    public static List<Model> models;
    public boolean usePin;
    public boolean backgroundLock = true;
//    public String pin;

    public User user;
    //判断锁屏的设置
    public static volatile boolean stop = false;

    public static volatile boolean isFirst = true;

    public ClipboardManager mClipboardManager;//剪贴板
    /**
     * 主密码
     */
//    public String password;
    /**
     * 线程池
     */
    public static ExecutorService mExecutor;

    /**
     * 图片映射
     */
    public static Map<String, Integer> icons;
    public static Map<Integer, String> modelName;
    public static String databaseFilename;
    //数据库名
    private static String DATABASE_FILENAME = "tpass.db";
    //获取datebase

    @Override
    public void onCreate() {
        super.onCreate();
        iconMap();
        modelNameMap();
//        password= DatabaseManager.
        isHasTPass();
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        user = DatabaseManager.queryUser();
        mExecutor = Executors.newFixedThreadPool(5);
        /**
         * 判断sd卡里是否有tpass.db如果没有，将raw文件夹下的资源写进/data/data/com.example.leohuang.password_manager/files/databases11
         */

        usePin = PrefUtils.getUsePIN(this);
        backgroundLock = PrefUtils.getBackgroundTime(this);
        isFirst = PrefUtils.getFirst(this);
    }

    private void modelNameMap() {
        modelName = new HashMap<Integer, String>();
        modelName.put(1, "密码");
        modelName.put(2, "登陆信息");
        modelName.put(3, "银行账户");
        modelName.put(4, "信用卡");
        modelName.put(5, "安全备注");
        modelName.put(6, "会籍");
        modelName.put(7, "护照");
        modelName.put(8, "身份标识");
        modelName.put(9, "驾驶执照");
        modelName.put(10, "户外许可证");
        modelName.put(11, "数据库");
        modelName.put(12, "服务器");
        modelName.put(13, "无线路由器");
        modelName.put(14, "软件许可");
        modelName.put(15, "社会保险号码");
        modelName.put(16, "保险单");
        modelName.put(17, "共同基金");
        modelName.put(18, "股票投资");
        modelName.put(19, "电子邮件账户");
        modelName.put(20, "FTP账户");
        modelName.put(21, "SSH账号");
        modelName.put(22, "网络托管");
    }

    private void iconMap() {
        icons = new HashMap<String, Integer>();
        icons.put("type_password", R.drawable.type_password);
        icons.put("type_login", R.drawable.type_login);
        icons.put("type_bank", R.drawable.type_bank);
        icons.put("type_credit_card", R.drawable.type_credit_card);
        icons.put("type_note", R.drawable.type_note);
        icons.put("type_membership", R.drawable.type_membership);
        icons.put("type_passport", R.drawable.type_passport);
        icons.put("type_identity", R.drawable.type_identity);
        icons.put("type_driver_license", R.drawable.type_driver_license);
        icons.put("type_hunting_license", R.drawable.type_hunting_license);
        icons.put("type_database", R.drawable.type_database);
        icons.put("type_unix_server", R.drawable.type_unix_server);
        icons.put("type_airport", R.drawable.type_airport);
        icons.put("type_software", R.drawable.type_software);
        icons.put("type_ssn", R.drawable.type_ssn);
        icons.put("type_insurance", R.drawable.type_insurance);
        icons.put("type_fund", R.drawable.type_fund);
        icons.put("type_investment", R.drawable.type_investment);
        icons.put("type_email", R.drawable.type_email);
        icons.put("type_ftp", R.drawable.type_ftp);
        icons.put("type_ssh", R.drawable.type_ssh);
        icons.put("type_network", R.drawable.type_network);
    }

    //是否有数据库文件
    //是否有数据库文件
    public void isHasTPass() {
        try {

            boolean b = false;
            File dir = getFilesDir();
            File databases = new File(dir, "databases");
            if (!databases.exists()) {
                databases.mkdir();
            }
            databaseFilename = databases.getAbsolutePath() + "/tpass.db";
            File dbFile = new File(databaseFilename);
            //判断是否存在该文件
            if (!dbFile.exists()) {
                dbFile.createNewFile();
                //不存在得到数据库输入流对象
                InputStream is = getApplicationContext().getResources().openRawResource(
                        R.raw.tpass);
                //创建输出流
                OutputStream fos = new FileOutputStream(dbFile);
                //将数据输出
                byte[] buffer = new byte[1024 * 5];
                int count = 0;

                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                //关闭资源
                fos.close();
                is.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
