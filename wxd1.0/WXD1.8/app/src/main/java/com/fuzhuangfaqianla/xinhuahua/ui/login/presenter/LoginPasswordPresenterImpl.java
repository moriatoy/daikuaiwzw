package com.fuzhuangfaqianla.xinhuahua.ui.login.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginPasswordContract;
import com.fuzhuangfaqianla.xinhuahua.ui.login.model.LoginPasswordModelImpl;

import java.util.Map;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class LoginPasswordPresenterImpl extends BasePresenerImpl<LoginPasswordContract.View> implements LoginPasswordContract.Presenter {
    LoginPasswordContract.Model model;

    public LoginPasswordPresenterImpl(LoginPasswordContract.View view){
        mView = view;
        model = new LoginPasswordModelImpl();

        mView.initTextView();
        mView.initShowPassword();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loginByPassword(Map<String, String> map) {
        mSubscription = model.loginByPassword(map, new DefaultSubscriber<User>(mView._getContext(),true) {
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
