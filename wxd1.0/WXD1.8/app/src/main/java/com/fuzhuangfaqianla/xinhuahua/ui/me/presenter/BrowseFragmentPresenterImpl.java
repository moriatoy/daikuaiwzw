package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BrowseFragmentContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.model.BrowseFragmentModelImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class BrowseFragmentPresenterImpl extends BasePresenerImpl<BrowseFragmentContract.View> implements BrowseFragmentContract.Presenter {
    private BrowseFragmentContract.Model model;

    public BrowseFragmentPresenterImpl(BrowseFragmentContract.View view){
        mView = view;
        model = new BrowseFragmentModelImpl();

        mView.initRefreshLayout();
        mView.initRecylcerView();
    }

    @Override
    public void requestApplyData(Map<String,String> map) {
        mSubscription = model.requestApplyData(map,new DefaultSubscriber<List<List<Map<String,Object>>>>(mView) {
            @Override
            protected void _onNext(List<List<Map<String,Object>>> datas) {
                mView.refreshRecyclerView(datas);
            }

            @Override
            protected void _onError(Throwable e) {
                mView.hideRefreshLayout();
            }

            @Override
            protected void _onCompleted() {
                mView.hideRefreshLayout();
            }
        });
    }

    @Override
    public void requestVisitData(Map<String,String> map) {
        mSubscription = model.requestVisitData(map,new DefaultSubscriber<List<List<Map<String,Object>>>>(mView) {
            @Override
            protected void _onNext(List<List<Map<String,Object>>> datas) {
                mView.refreshRecyclerView(datas);
            }

            @Override
            protected void _onError(Throwable e) {
                mView.hideRefreshLayout();
            }

            @Override
            protected void _onCompleted() {
                mView.hideRefreshLayout();
            }
        });
    }

    @Override
    public void loadMoreApplyData(Map<String, String> map) {
        model.loadMoreApplyData(map, new DefaultSubscriber<List<List<Map<String, Object>>>>(mView) {
            @Override
            protected void _onNext(List<List<Map<String, Object>>> lists) {
                mView.refreshRecyclerView(lists);
            }

            @Override
            protected void _onError(Throwable e) {
                mView.hideLoadMoreLayout();
            }

            @Override
            protected void _onCompleted() {
                mView.hideLoadMoreLayout();
            }
        });
    }

    @Override
    public void loadMoreVisitData(Map<String, String> map) {
        model.loadMoreVisitData(map, new DefaultSubscriber<List<List<Map<String, Object>>>>(mView) {
            @Override
            protected void _onNext(List<List<Map<String, Object>>> lists) {
                mView.refreshRecyclerView(lists);
            }

            @Override
            protected void _onError(Throwable e) {
                mView.hideLoadMoreLayout();
            }

            @Override
            protected void _onCompleted() {
                mView.hideLoadMoreLayout();
            }
        });
    }

    @Override
    public int getPageCount() {
        return model.getPageCount();
    }


}
