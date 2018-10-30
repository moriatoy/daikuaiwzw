package com.fuzhuangfaqianla.xinhuahua.ui.index.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.Carousel;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.RecommandTag;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommend;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/21.
 */

public interface IndexContract {
    interface Model {
        Subscription refreshRecyclerView(Map<String,String> map,DefaultSubscriber<List<Object>> subscriber);
        Observable<List<Carousel>> requestCarousel();
        Observable<List<RecommandTag>> requestRecommandTag();
        Observable<List<TodayRecommend>> requestTodayRecommend();
        Observable<List<OnlineLoan>> requestOnlineLoanProduct(Map<String,String> map);
        void loadMore(Map<String,String> map,DefaultSubscriber<List<Object>> subscriber);
        int getPageCount();
    }

    interface View extends BaseView {
        void initRefreshLayout();
        void initRecyclerView();
        void initTitlebar();
        void refreshRecyclerView(List<Object> objects);
        void hideRefreshLayout();
        void hideLoadMoreLayout();
    }

    interface Presenter extends BasePresenter {
        void refreshRecyclerView(Map<String,String> map);
        void loadMore(Map<String,String> map);
        int getPageCount();
    }
}
