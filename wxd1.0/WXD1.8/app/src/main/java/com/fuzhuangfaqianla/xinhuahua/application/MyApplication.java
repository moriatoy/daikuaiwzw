package com.fuzhuangfaqianla.xinhuahua.application;

import android.util.Log;

import com.commonlib.base.BaseApplication;
import com.commonlib.config.ProjectConfig;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.fuzhuangfaqianla.xinhuahua.service.MyPushIntentService;
import com.zhuge.analysis.stat.ZhugeSDK;


/**
 * Created by xiongchang on 2017/9/21.
 */

public class MyApplication extends BaseApplication {
    public static String mDeviceToken;

    @Override
    public void onCreate() {
        super.onCreate();

//        initLeakCanary();
        initUMengAnalytics();
        initUMengPush();
        initUMShare();
        initZhugeIO();
    }

    private boolean initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return true;
        }
        LeakCanary.install(this);
        return false;
    }

    private void initZhugeIO() {
        if(ProjectConfig.DEBUG){

        }else {
            ZhugeSDK.getInstance().init(getApplicationContext(),"1abf33e3728747ffb2b648af2b4011a5", AnalyticsConfig.getChannel(getApplicationContext()));
        }
    }

    private void initUMShare() {
        UMShareAPI.get(getApplicationContext());

        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        //PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setQQZone("101410216", "fe4593e44a06773f2cc794832c5efbf0");
    }

    private void initUMengAnalytics() {
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setCatchUncaughtExceptions(true);//统计错误日志
        MobclickAgent.setDebugMode(false);//调试模式
        MobclickAgent.enableEncrypt(true);//6.0.0版本及以后//日志加密
    }

    private void initUMengPush() {
        PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                mDeviceToken = deviceToken;
                Log.i("test", "push init sucess -- device token: "+deviceToken);

//                if(MyApplication.mDeviceToken!=null && LoginHelper.isLogin()){
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("deviceToken",MyApplication.mDeviceToken);
//                    map.put("deviceType", "android");
//
//                    HttpManager.api.setDeviceToken(map).compose(RxHelper.<String>handleResult())
//                            .subscribe(new DefaultSubscriber<String>(getApplicationContext()) {
//                        @Override
//                        protected void _onNext(String s) {
//                            MyLogUtil.i("test", "device token set successful: ");
//                        }
//
//                        @Override
//                        protected void _onError(Throwable e) {
//                            MyLogUtil.i("test", "device token set failed: ");
//                        }
//
//                        @Override
//                        protected void _onCompleted() {
//
//                        }
//                    });
//                }
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }
}
