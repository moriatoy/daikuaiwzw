package com.fuzhuangfaqianla.xinhuahua.ui.login.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/26.
 */

public interface LoginPasswordContract {
    interface Model {
        Subscription loginByPassword(Map<String, String> map, DefaultSubscriber<User> subscriber);
    }

    interface View extends BaseView {
        void showKeyBoard();
        void initTextView();
        void initShowPassword();
        void loginSuccessful(User user);
    }

    interface Presenter extends BasePresenter {
        void loginByPassword(Map<String, String> map);
    }
}
