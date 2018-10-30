package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/27.
 */

public interface ModifyPasswordContract {
    interface Model {
        Subscription modifyPassword(Map<String,String> map,DefaultSubscriber<Object> subscriber);
        void getCheckCode(Map<String, String> map, DefaultSubscriber<String> subscriber);
    }

    interface View extends BaseView {
        void showKeyBoard();
        void initTitlebar();
        void initShowPassword();
        void getCheckCodeSuccessful();
        void getCheckCodeFailed();
        void modifyPasswordSuccessful();
        void modifyPasswordFailed();
    }

    interface Presenter extends BasePresenter {
        void getCheckCode(Map<String, String> map);
        void modifyPassword(Map<String, String> map);
    }
}
