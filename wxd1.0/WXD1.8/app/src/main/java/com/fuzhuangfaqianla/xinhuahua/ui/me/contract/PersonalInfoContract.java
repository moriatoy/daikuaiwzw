package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/29.
 */

public interface PersonalInfoContract {
    interface Model {
        Subscription setUserInfo(User user, DefaultSubscriber<String> subsciber);
    }

    interface View extends BaseView {
        void initUserData();
        void initTitlebar();
        void initViewPager();
        void initIndicatorWidth();
        void setUserInfoSuccessful();
        void setUserInfoFailed();
    }

    interface Presenter extends BasePresenter {
        void setUserInfo(User user);
    }
}
