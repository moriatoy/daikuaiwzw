package com.fuzhuangfaqianla.xinhuahua.ui.loan.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.bean.Sort;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/23.
 */

public interface LoanContract {
    interface Model {
        Subscription refreshRecyclerView(Map<String,String> map,DefaultSubscriber<List<Object>> subscriber);
        void getTagsByType(String from, Map<String, String> ocp_map, DefaultSubscriber<List<Sort>> subscriber);
        void loadMore(Map<String, String> map,DefaultSubscriber<List<Object>> subscriber);
        int getPageCount();
    }

    interface View extends BaseView {
        void initTitlebar();
        void initRefreshLayout();
        void initRecyclerView();
        void refreshRecyclerView(List<Object> onlineLoen);
        void hideRefreshLayout();
        void hideLoadMoreLayout();
        void initChooseRecyclerView();
        void requestSortFailed();
        void requestOcpSuccessful(List<Sort> sorts);
        void requestLoanTypeSuccessful(List<Sort> sorts);
        void requestTimeSuccessful(List<Sort> sorts);
    }

    interface Presenter extends BasePresenter {
        void refreshRecyclerView(Map<String,String> map);
        void getTagsByType(String from, Map<String, String> ocp_map);
        void loadMore(Map<String, String> map);
        int getPageCount();
    }
}
