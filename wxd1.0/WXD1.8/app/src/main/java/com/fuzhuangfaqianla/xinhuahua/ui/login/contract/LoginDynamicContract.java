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

public interface LoginDynamicContract {
    interface Model {
        void getCheckCode(Map<String, String> map,DefaultSubscriber<String> subscriber);
        Subscription loginByDynamic(Map<String, String> map, DefaultSubscriber<User> subscriber);
    }

    interface View extends BaseView {
        void showKeyBoard();
        void loginSuccessful(User user);
        void getCheckCodeSuccessful();
        void getCheckCodeFailed();
    }

    interface Presenter extends BasePresenter {
        void loginByDynamic(Map<String, String> map);
        void getCheckCode(Map<String, String> map);
    }
}
