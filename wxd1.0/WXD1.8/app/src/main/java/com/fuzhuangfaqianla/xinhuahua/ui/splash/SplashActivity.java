package com.fuzhuangfaqianla.xinhuahua.ui.splash;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;

import com.commonlib.config.ProjectConfig;
import com.commonlib.http.SchedulerTransformer;
import com.commonlib.util.ToastUtil;
import com.commonlib.util.ViewUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.main.MainActivity;
import com.zhuge.analysis.stat.ZhugeSDK;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

@RuntimePermissions
public class SplashActivity extends AppBaseActivity {
    Subscription subscription;
    Dialog dialog;

    public SplashActivity() {
        super(R.layout.activity_splash);
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","启动页");
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ViewUtil.quitFullScreen(this);
        initZhugeIO();
        super.onCreate(savedInstanceState);
    }


    private void initZhugeIO() {
        //ZhugeIO
        if(ProjectConfig.DEBUG){
            ZhugeSDK.getInstance().openLog();
            ZhugeSDK.getInstance().setLogLevel(Log.INFO);
        }
        ZhugeSDK.getInstance().init(getApplicationContext());
    }

    @Override
    public void initView() {
        super.initView();
        SplashActivityPermissionsDispatcher.initPermissionWithPermissionCheck(this);
//        initOpenNextDelay();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            initOpenNextDelay();
        }
    }

    private void initOpenNextDelay() {
        if(subscription!=null){
            subscription.unsubscribe();
            subscription=null;
        }

        subscription = Observable.timer(2500, TimeUnit.MILLISECONDS)
                .compose(new SchedulerTransformer<Long>())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription!=null){
            subscription.unsubscribe();
            subscription=null;
        }
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public Context _getContext() {
        return this;
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void initPermission() {

    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showRationaleForPermission(final PermissionRequest request) {
        dialog = new AlertDialog.Builder(this)
                .setMessage("需要获取一些权限")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showDeniedForPermission() {
        ToastUtil.toastShortShow("获取权限失败");
        initOpenNextDelay();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showNeverAskForPermission() {
        ToastUtil.toastShortShow("获取权限失败");
        initOpenNextDelay();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
            initOpenNextDelay();
        }
    }

}
