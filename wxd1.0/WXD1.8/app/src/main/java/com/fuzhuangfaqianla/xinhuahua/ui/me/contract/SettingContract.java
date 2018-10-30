package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/25.
 */

public interface SettingContract {
    interface Model {
        Subscription logout(DefaultSubscriber<Object> subscriber);
    }

    interface View extends BaseView {
        void initTitlebar();
        void judgeLogin();
        void logoutSuccessful();
    }

    interface Presenter extends BasePresenter {
        void logout();
    }
}
