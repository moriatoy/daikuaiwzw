package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.fuzhuangfaqianla.xinhuahua.ui.me.bean.UploadImage;

import rx.Observable;
import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/27.
 */

public interface MyInfoContract {
    interface Model {
        Observable<UploadImage> uploadImage(String path);
        Subscription modifyAvatar(String path, DefaultSubscriber<String> subscriber);
        String getUploadUrl();
    }

    interface View extends BaseView {
        void initTitlebar();
        void initUserInfo();
        void modifyAvatarSuccessful();
    }

    interface Presenter extends BasePresenter {
        void modifyAvatar(String path);
        String getUploadUrl();
    }
}
