package com.fuzhuangfaqianla.xinhuahua.ui.index.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.index.contract.RecommandContract;
import com.fuzhuangfaqianla.xinhuahua.ui.index.model.RecommandModelImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by xiongchang on 2017/9/22.
 */

public class RecommandPresenterImpl extends BasePresenerImpl<RecommandContract.View> implements RecommandContract.Presenter {
    private RecommandContract.Model model;

    public RecommandPresenterImpl(RecommandContract.View view){
        mView = view;
        model = new RecommandModelImpl();

        mView.initTitlebar();
        mView.initRefreshLayout();
        mView.initRecyclerView();
    }

    @Override
    public void refreshRecyclerView(final Map<String, String> map) {
        mSubscription = model.refreshRecyclerView(map, new DefaultSubscriber<List<Object>>(mView) {
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
}
