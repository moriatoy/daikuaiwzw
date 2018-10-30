package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.SettingContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.model.SettingModelImpl;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class SettingPresenterImpl extends BasePresenerImpl<SettingContract.View> implements SettingContract.Presenter {
    private SettingContract.Model model;

    public SettingPresenterImpl(SettingContract.View view){
        mView = view;
        model = new SettingModelImpl();

        mView.initTitlebar();
    }

    @Override
    public void onResume() {
        super.onResume();
        mView.judgeLogin();
    }

    @Override
    public void logout() {
        mSubscription = model.logout(new DefaultSubscriber<Object>(mView._getContext(),true) {
            @Override
            protected void _onNext(Object o) {
                mView.logoutSuccessful();
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }
}
