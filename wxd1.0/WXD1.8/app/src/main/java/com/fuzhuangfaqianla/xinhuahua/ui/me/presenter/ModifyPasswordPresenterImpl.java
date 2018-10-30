package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.ModifyPasswordContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.model.ModifyPasswordModelImpl;

import java.util.Map;

/**
 * Created by xiongchang on 2017/9/27.
 */

public class ModifyPasswordPresenterImpl extends BasePresenerImpl<ModifyPasswordContract.View> implements ModifyPasswordContract.Presenter {
    private ModifyPasswordContract.Model model;

    public ModifyPasswordPresenterImpl(ModifyPasswordContract.View view){
        mView = view;
        model = new ModifyPasswordModelImpl();

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
    public void modifyPassword(Map<String, String> map) {
        mSubscription = model.modifyPassword(map, new DefaultSubscriber<Object>(mView._getContext(),true) {
            @Override
            protected void _onNext(Object s) {
                mView.modifyPasswordSuccessful();
            }

            @Override
            protected void _onError(Throwable e) {
                mView.modifyPasswordFailed();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }
}
