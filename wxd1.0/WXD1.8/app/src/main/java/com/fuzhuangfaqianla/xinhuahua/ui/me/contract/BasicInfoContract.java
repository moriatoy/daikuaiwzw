package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import java.util.List;

/**
 * Created by xiongchang on 2017/9/29.
 */

public interface BasicInfoContract {
    interface Model {
        void requestArea(DefaultSubscriber<List<Object>> subscriber);
    }

    interface View extends BaseView {
        void initInfo();
        void requestAreaSuccessful(List<Object> datas);
        void requestAreaFailed();
    }

    interface Presenter extends BasePresenter {
        void requestArea();
    }
}
