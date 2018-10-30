package com.fuzhuangfaqianla.xinhuahua.ui.index.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.index.contract.IndexContract;
import com.fuzhuangfaqianla.xinhuahua.ui.index.model.IndexModelImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by xiongchang on 2017/9/21.
 */

public class IndexPresenterImpl extends BasePresenerImpl<IndexContract.View> implements IndexContract.Presenter {
    IndexContract.Model model;

    public IndexPresenterImpl(IndexContract.View view){
        mView = view;
        model = new IndexModelImpl();

        mView.initRefreshLayout();
        mView.initRecyclerView();
        mView.initTitlebar();
    }

    @Override
    public void refreshRecyclerView(Map<String,String> map) {
        mSubscription = model.refreshRecyclerView(map,new DefaultSubscriber<List<Object>>(mView) {
            @Override
            protected void _onNext(List<Object> objects) {
                mView.refreshRecyclerView(objects);
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
    public void loadMore(Map<String,String> map) {
        model.loadMore(map,new DefaultSubscriber<List<Object>>(mView) {
            @Override
            protected void _onNext(List<Object> objects) {
                mView.refreshRecyclerView(objects);
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
