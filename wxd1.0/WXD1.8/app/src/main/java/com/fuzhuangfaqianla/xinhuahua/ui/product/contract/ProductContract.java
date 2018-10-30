package com.fuzhuangfaqianla.xinhuahua.ui.product.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/28.
 */

public interface ProductContract {
    interface Model {
        Observable<OnlineLoan> requestProductDetailById(String productId);
        Observable<List<Object>> requestProductStep(OnlineLoan onlineLoan);
        Subscription requestRecyclerView(String productId,DefaultSubscriber<List<Object>> subscriber);
        OnlineLoan getOnlineLoan();
    }

    interface View extends BaseView {
        String getProductId();
        void initZhuge();
        void initTitlebar(String title);
        void initProductInfo();
        void initRecyclerView();
        void initCollapsedAnimation();
        void initBottomAnimation();
        void refreshRecyclerView(List<Object> objects);
        void setOnlineData(OnlineLoan onlineData);
        OnlineLoan getOnlineLoan();
    }

    interface Presenter extends BasePresenter {
        void requestRecyclerView();
    }
}
