package com.fuzhuangfaqianla.xinhuahua.ui.index.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/22.
 */

public interface RecommandContract {
    interface Model {
        Subscription refreshRecyclerView(Map<String, String> map, DefaultSubscriber<List<Object>> subscriber);
    }

    interface View extends BaseView {
        void initTitlebar();
        void initRefreshLayout();
        void initRecyclerView();
        void refreshRecyclerView(List<Object> objects);
        void hideRefreshLayout();
    }

    interface Presenter extends BasePresenter {
        void refreshRecyclerView(Map<String, String> map);
    }
}
