package com.fuzhuangfaqianla.xinhuahua.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonlib.base.BaseFragment;
import com.commonlib.loadsir.EmptyCallback;
import com.commonlib.loadsir.ErrorCallback;
import com.commonlib.loadsir.NetworkErrorCallback;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

/**
 * Created by xiongchang on 17/7/8.
 */

public abstract class AppBaseFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;

    protected AppBaseFragment(int resId) {
        super(resId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initZhugeEvent();
        return view;
    }

    protected abstract void initZhugeEvent();

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.onDestroy();
        }
    }

    @Override
    public Context _getContext() {
        return getContext();
    }

    @Override
    public void showNetworkErrorView() {
        mLoadService.showCallback(NetworkErrorCallback.class);
    }

    @Override
    public void showEmptyView() {
        mLoadService.showCallback(EmptyCallback.class);
    }

    @Override
    public void showSuccessfulView() {
        mLoadService.showSuccess();
    }

    @Override
    public void showErrorView() {
        mLoadService.showCallback(ErrorCallback.class);
    }
}
