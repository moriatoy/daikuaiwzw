package com.fuzhuangfaqianla.xinhuahua.ui.login.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginDynamicContract;
import com.fuzhuangfaqianla.xinhuahua.ui.login.model.LoginDynamicModelImpl;

import java.util.Map;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class LoginDynamicPresenterImpl extends BasePresenerImpl<LoginDynamicContract.View> implements LoginDynamicContract.Presenter {
    LoginDynamicContract.Model model;

    public LoginDynamicPresenterImpl(LoginDynamicContract.View view){
        mView = view;
        model = new LoginDynamicModelImpl();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void loginByDynamic(Map<String, String> map) {
        mSubscription = model.loginByDynamic(map, new DefaultSubscriber<User>(mView._getContext(),true) {
            @Override
            protected void _onNext(User user) {
                mView.loginSuccessful(user);
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
