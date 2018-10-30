package com.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonlib.R;
import com.commonlib.util.ToastUtil;
import com.commonlib.util.ViewUtil;
import com.commonlib.widget.TitleBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.util.Date;

import rx.Subscription;

/**
 * Created by xiongchang on 17/7/7.
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 页面打开时的时间戳
     */
    private Date beginTimeStamp;
    protected int resId;
    protected TitleBar mTitle;
    //RxBus
    protected Subscription rxBusSubscription;

    protected LoadService mLoadService;

    protected BaseFragment(int resId) {
        this.resId = resId;
    }

    @Override
    public void onResume() {
        super.onResume();
        beginTimeStamp = new Date();
    }

    @Override
    public void onPause() {
        super.onPause();
        Date date = new Date();
        int diff = (int) ((date.getTime() - beginTimeStamp.getTime()) / 1000);
       /* if (!StringUtil.isNullOrEmpty(getEventId())) {
            MobclickAgent.onEventValue(getContext(), getEventId(), null, diff);
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(resId, container, false);
        initLoadSir(view);
        initView(view);
        return getFragmentReturnLayout(view);
    }

    //如需要替换为loadSir  则重写该方法
    public View getFragmentReturnLayout(View view) {
        return view;
    }

    //界面是否需要loadSir 需要则重写
    public void initLoadSir(View view) {

    }


    /**
     * 初始化页面布局
     * @param view
     */
    public void initView(View view) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消RxBus订阅
        if(rxBusSubscription != null){
            rxBusSubscription.unsubscribe();
            rxBusSubscription=null;
        }
        ViewUtil.fixInputMethodManagerLeak(getContext());
    }

    /**
     * 弹出界面提示内容
     *
     * @param msg 提示内容文字内容
     */
    public void showToast(String msg) {
        ToastUtil.toastShortShow(getActivity().getApplication(), msg);
    }

    /**
     * 弹出界面提示内容
     *
     * @param textResId 文本资源id
     */
    public void showToast(int textResId) {
        ToastUtil.toastShortShow(getActivity().getApplication(), textResId);
    }

    /**
     * initTitle:初始化标题. <br/>
     *
     * @param id 标题资源文件id
     */
    public void initTitle(int id,View view,int bgcolor,int titlecolor) {
        if (mTitle == null) {
            mTitle = new TitleBar(getActivity(), view, id,bgcolor,titlecolor);
        }
    }

    /**
     * initTitle:初始化标题. <br/>
     *
     * @param name 标题文本
     */
    public void initTitle(String name,View view,int bgcolor,int titlecolor) {
        if (mTitle == null) {
            mTitle = new TitleBar(getActivity(), view, name,bgcolor,titlecolor);
        }
    }
}
