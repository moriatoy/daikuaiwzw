package com.fuzhuangfaqianla.xinhuahua.ui.login.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.RegistContract;
import com.fuzhuangfaqianla.xinhuahua.ui.login.model.RegistModelImpl;

import java.util.Map;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class RegistPresenterImpl extends BasePresenerImpl<RegistContract.View> implements RegistContract.Presenter {
    RegistContract.Model model;

    public RegistPresenterImpl(RegistContract.View view){
        mView = view;
        model = new RegistModelImpl();

        mView.showKeyBoard();
        mView.initTitlebar();
        mView.initShowPassword();
    }

    @Override
    public void getCheckCode(Map<String, String> map) {
        model.getCheckCode(map, new DefaultSubscriber<String>(mView._getContext(),true) {
            @Override
            protected void _onNext(String s) {
                mView.getCheckCodeSuccessful();
            }

            @Override
            protected void _onError(Throwable e) {
                mView.getCheckCodeFailed();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void regist(Map<String, String> map) {
        mSubscription = model.regist(map, new DefaultSubscriber<Object>(mView._getContext(),true) {
            @Override
            protected void _onNext(Object datas) {
                mView.registSuccessful();
            }

            @Override
            protected void _onError(Throwable e) {
                mView.registFailed();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }
}
