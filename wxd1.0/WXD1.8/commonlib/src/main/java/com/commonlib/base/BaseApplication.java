package com.commonlib.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.commonlib.CustomActivityManager;
import com.commonlib.R;
import com.commonlib.config.ProjectConfig;
import com.commonlib.imageloader.ImageLoaderManager;
import com.commonlib.imagepicker.GlideImageLoader;
import com.commonlib.loadsir.EmptyCallback;
import com.commonlib.loadsir.ErrorCallback;
import com.commonlib.loadsir.NetworkErrorCallback;
import com.commonlib.loadsir.LoadingCallback;
import com.kingja.loadsir.core.LoadSir;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.oubowu.slideback.ActivityHelper;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import top.wefor.circularanim.CircularAnim;

/**
 * Created by xiongchang on 17/7/7.
 */

public class BaseApplication extends Application {
    private static BaseApplication mApplication = null;

    /**
     * 单例方法
     */
    public static BaseApplication getInstance() {
        return mApplication;
    }

    /**
     * Activity管理器
     */
    private CustomActivityManager mActivityManager = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mActivityManager = new CustomActivityManager();

        initFileDir();// 初始化文件缓存目录
        initOtherLib();//导入第三方SDK包


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            //"com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 当系统内存不足时调用
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Runtime.getRuntime().gc();// 通知Java虚拟机回收垃圾
    }

    /**
     * 程序终止时候调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 获取activity管理器
     *
     * @return CustomActivityManager
     */
    public CustomActivityManager getActivityManager() {
        return mActivityManager;
    }

    /**
     * 页面侧滑
     */
    private static ActivityHelper mActivityHelper;

    public static ActivityHelper getActivityHelper() {
        return mActivityHelper;
    }

    private void initSlideBack() {
        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);
    }

    /**
     * 初始化SDK
     */
    private void initOtherLib() {
        initSlideBack();
        initImageLoaderManager();
        initImagePicker();
        initCircleAnim();
        initLoadSir();
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new NetworkErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

    private void initCircleAnim() {
        CircularAnim.init(700, 500, R.color.white);
    }

    private void initImageLoaderManager() {
        //初始化ImageLoaderManager
        ImageLoaderManager.getInstance().init(this);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    /**
     * 初始化界面缓存目录
     */
    private void initFileDir() {
        File appFile = new File(ProjectConfig.APP_PATH);
        if (!appFile.exists()) {
            appFile.mkdirs();
        }
        File cacheFile = new File(ProjectConfig.DIR_CACHE);// 其他下载缓存数据目录
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        File upDataCacheFile = new File(ProjectConfig.DIR_UPDATE);// 程序在手机SDK中的更新缓存目录.
        if (!upDataCacheFile.exists()) {
            upDataCacheFile.mkdirs();
        }
        File logCacheFile = new File(ProjectConfig.LOGCAT_DIR);// 程序在手机SDK中的日志缓存目录.
        if (!logCacheFile.exists()) {
            logCacheFile.mkdirs();
        }

        File imageCacheFile = new File(ProjectConfig.DIR_IMG);// 程序在手机SDK中的图片缓存目录.
        if (!imageCacheFile.exists()) {
            imageCacheFile.mkdirs();
        }

        File videoCacheFile = new File(ProjectConfig.DIR_VIDEO);// 程序在手机SDK中的视频缓存目录.
        if (!videoCacheFile.exists()) {
            videoCacheFile.mkdirs();
        }
        File audioCacheFile = new File(ProjectConfig.DIR_AUDIO);// 程序在手机SDK中的音频缓存目录.
        if (!audioCacheFile.exists()) {
            audioCacheFile.mkdirs();
        }
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
