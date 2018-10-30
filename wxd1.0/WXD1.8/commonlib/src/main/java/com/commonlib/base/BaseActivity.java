package com.commonlib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.commonlib.R;
import com.commonlib.config.ProjectConfig;
import com.commonlib.util.LanguageUtil;
import com.commonlib.util.SPUtil;
import com.commonlib.util.ToastUtil;
import com.commonlib.util.ViewUtil;
import com.commonlib.widget.TitleBar;
import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;
import com.oubowu.slideback.widget.SlideBackLayout;

import java.util.Date;
import java.util.Locale;

import rx.Subscription;

/**
 * Created by xiongchang on 17/7/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 页面打开时的时间戳
     */
    private Date beginTimeStamp;

    /**
     * 最后一次返回按钮操作事件
     */
    private long lastEventTime;

    /**
     * 推出app的等待时间
     */
    private static int TIME_TO_WAIT = 3 * 1000;
    /**
     * 加载界面的资源id
     */
    protected int mLayoutResID = -1;
    /**
     * 是否需要关闭app
     */
    protected boolean mNeedFinishApp = false;

    protected TitleBar mTitle;

    protected LayoutInflater mLayoutInflater;

    //页面侧滑
    protected SlideBackLayout mSlideBackLayout;

    //RxBus
    protected Subscription rxBusSubscription;

    //状态栏
    protected ImmersionBar mImmersionBar;

    //各种状态图
    protected LoadService mLoadService;

    /**
     * @param layoutResID 界面资源文件id
     */
    public BaseActivity(int layoutResID) {
        this.mLayoutResID = layoutResID;
    }

    /**
     * @param layoutResID   界面资源文件id
     * @param needFinishApp 该界面是否可以退出app
     */
    public BaseActivity(int layoutResID, boolean needFinishApp) {
        this.mLayoutResID = layoutResID;
        this.mNeedFinishApp = needFinishApp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().getActivityManager().pushActivity(this);
        setContentView(mLayoutResID);
        mLayoutInflater = (LayoutInflater) (this)
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        //初始化状态栏
        if(isImmersionBarEnabled()){
            initImmersionBar();
        }
        getIntentData();
        beforeInitView();
        initLoadService();
        initView();
    }

    //界面是否需要loadSir 需要则重写
    public void initLoadService() {

    }

    public void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this).statusBarColor(R.color.white).statusBarDarkFont(true,0.2f);
        mImmersionBar.init();
    }

    //此方法设置与initView()之前    比如设置状态栏
    public void beforeInitView() {
    }

    public void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        beginTimeStamp=new Date();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消RxBus订阅
        if(rxBusSubscription != null){
            rxBusSubscription.unsubscribe();
            rxBusSubscription=null;
        }
        //在BaseActivity里销毁
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
        ViewUtil.fixInputMethodManagerLeak(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Date date=new Date();
        int diff= (int) ((date.getTime()-beginTimeStamp.getTime())/1000);

    }

    /**
     * 关闭界面，重写了系统的方法，在里面增加了系统Activity堆栈管理功能
     */
    @Override
    public void finish() {
        BaseApplication.getInstance().getActivityManager().popActivity(this);
        super.finish();
    }

    /**
     * 关闭除指定界面以外的所有Activity
     *
     * @param cls 指定界面
     */
    public void finishAllExt(Class<?> cls) {
        BaseApplication.getInstance().getActivityManager().popAllActivityExceptOne(cls);
    }

    /**
     * 关闭所有Activity
     */
    public void finishAll() {
        BaseApplication.getInstance().getActivityManager().popAllActivity();
    }

    /**
     * 返回按钮点击事件，已经封装完毕，非必要，请勿重写
     */
    @Override
    public void onBackPressed() {
        if (mNeedFinishApp) {
            long currentEventTime = System.currentTimeMillis();
            if ((currentEventTime - lastEventTime) > TIME_TO_WAIT) {
                ToastUtil.toastShow(this, R.string.tip_finishapp, TIME_TO_WAIT);
                lastEventTime = currentEventTime;
            } else {
                finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());
                Runtime.getRuntime().gc();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * initTitle:初始化标题. <br/>
     *
     * @param id 标题资源文件id
     */
    public void initTitle(int id,int bgcolor,int titlecolor) {
        if (mTitle == null) {
            mTitle = new TitleBar(this, findViewById(android.R.id.content), id,bgcolor,titlecolor);
        }
    }

    /**
     * initTitle:初始化标题. <br/>
     *
     * @param name 标题文本
     */
    public void initTitle(String name,int bgcolor,int titlecolor) {
        mTitle = new TitleBar(this, findViewById(android.R.id.content), name,bgcolor,titlecolor);
    }

    /**
     * 弹出界面提示内容
     *
     * @param msg 提示内容文字内容
     */
    public void showToast(String msg) {
        ToastUtil.toastShortShow(this, msg);
    }

    /**
     * 弹出界面提示内容
     *
     * @param textResId 文本资源id
     */
    public void showToast(int textResId) {
        ToastUtil.toastShortShow(this, textResId);
    }

    /**
     * 得到界面跳转过来的数值，如果上一个界面有数据传递过来，那么这边需要进行重写该方法
     */
    public void getIntentData() {

    }

    /**
     * 事件的分发
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 判断软键盘是否需要隐藏
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }

}
