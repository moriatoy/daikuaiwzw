package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/25.
 */

public interface FeedbackContract {
    interface Model {
        Subscription feedback(Map<String, String> map, DefaultSubscriber<String> subscriber);
    }

    interface View extends BaseView {
        void initTitlebar();
        void showKeyboard();
        void feedbackSuccessful();
    }

    interface Presenter extends BasePresenter {
        void feedback(Map<String, String> map);
    }
}
