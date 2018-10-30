package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/25.
 */

public interface BrowseFragmentContract {
    interface Model {
        Subscription requestApplyData(Map<String,String> map,DefaultSubscriber<List<List<Map<String,Object>>>> subscriber);
        Subscription requestVisitData(Map<String,String> map,DefaultSubscriber<List<List<Map<String,Object>>>> subscriber);
        void loadMoreApplyData(Map<String, String> map,DefaultSubscriber<List<List<Map<String,Object>>>> subscriber);
        void loadMoreVisitData(Map<String, String> map,DefaultSubscriber<List<List<Map<String,Object>>>> subscriber);
        int getPageCount();
    }

    interface View extends BaseView {
        void initRefreshLayout();
        void initRecylcerView();
        void refreshRecyclerView(List<List<Map<String,Object>>> datas);
        void hideRefreshLayout();
        void hideLoadMoreLayout();
    }

    interface Presenter extends BasePresenter {
        void requestApplyData(Map<String,String> map);
        void requestVisitData(Map<String,String> map);
        void loadMoreApplyData(Map<String, String> map);
        void loadMoreVisitData(Map<String, String> map);
        int getPageCount();

    }
}
