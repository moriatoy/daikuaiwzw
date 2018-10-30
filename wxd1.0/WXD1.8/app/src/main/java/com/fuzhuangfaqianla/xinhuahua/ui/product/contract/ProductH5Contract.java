package com.fuzhuangfaqianla.xinhuahua.ui.product.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/22.
 */

public interface ProductH5Contract {
    interface Model {
        Subscription apply(Map<String, String> map, DefaultSubscriber<String> subscriber);
        Subscription visit(Map<String, String> map, DefaultSubscriber<String> subscriber);
    }

    interface View extends BaseView {
        void initTitlebar();
        void initAgentWeb();
        void recordComplete();
    }

    interface Presenter extends BasePresenter {
        void apply(Map<String, String> map);
        void visit(Map<String, String> map);
    }
}
