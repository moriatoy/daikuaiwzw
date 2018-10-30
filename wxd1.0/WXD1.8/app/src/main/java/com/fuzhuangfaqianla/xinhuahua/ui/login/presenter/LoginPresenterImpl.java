package com.fuzhuangfaqianla.xinhuahua.ui.login.presenter;

import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginContract;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class LoginPresenterImpl extends BasePresenerImpl<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenterImpl(LoginContract.View view){
        mView = view;

        mView.initTitlebar();
        mView.initViewPager();
        mView.initIndicatorWidth();
    }
}
