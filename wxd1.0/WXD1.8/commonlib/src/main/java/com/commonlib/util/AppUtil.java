package com.commonlib.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by dmx on 2017/1/9.
 */

public class AppUtil {
    public static String WEIXIN_PCK = "com.tencent.mm";
    public static String QQ_PCK = "com.tencent.mobileqq";
    public static String FACEBOOK_PCK = "com.facebook.katana";
    public static String WEIBO_PCK = "com.sina.weibo";

    /**
     * 判断应用是否已经安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 启动应用
     * @param context
     * @param packgeName
     * @param cls
     */
    public static void startApp(Context context, String packgeName, String cls) {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName(packgeName, cls);
        intent.setComponent(cn);
        context.startActivity(intent);
    }

    /**
     * 获取包名
     */
    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
}
