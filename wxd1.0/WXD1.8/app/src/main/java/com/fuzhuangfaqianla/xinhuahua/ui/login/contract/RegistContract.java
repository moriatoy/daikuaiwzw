package com.fuzhuangfaqianla.xinhuahua.ui.login.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/26.
 */

public interface RegistContract {
    interface Model {
        Subscription regist(Map<String, String> map, DefaultSubscriber<Object> subscriber);
        void getCheckCode(Map<String, String> map, DefaultSubscriber<String> subscriber);
    }

    interface View extends BaseView {
        void showKeyBoard();
        void initTitlebar();
        void initShowPassword();
        void getCheckCodeSuccessful();
        void getCheckCodeFailed();
        void registSuccessful();
        void registFailed();
    }

    interface Presenter extends BasePresenter {
        void getCheckCode(Map<String, String> map);
        void regist(Map<String, String> map);
    }
}
