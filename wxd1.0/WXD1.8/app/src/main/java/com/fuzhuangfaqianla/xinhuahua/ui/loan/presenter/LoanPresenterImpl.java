package com.fuzhuangfaqianla.xinhuahua.ui.loan.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.bean.Sort;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.contract.LoanContract;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.model.LoanModelImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by xiongchang on 2017/9/23.
 */

public class LoanPresenterImpl extends BasePresenerImpl<LoanContract.View> implements LoanContract.Presenter {
    LoanContract.Model model;

    public LoanPresenterImpl(LoanContract.View view){
        mView = view;
        model = new LoanModelImpl();

        mView.initTitlebar();
        mView.initRefreshLayout();
        mView.initRecyclerView();
        mView.initChooseRecyclerView();
    }

    @Override
    public void refreshRecyclerView(Map<String,String> map) {
        mSubscription = model.refreshRecyclerView(map,new DefaultSubscriber<List<Object>>(mView) {
            @Override
            protected void _onNext(List<Object> items) {
                mView.refreshRecyclerView(items);
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
    public void getTagsByType(final String from, Map<String, String> map) {
        model.getTagsByType(from, map, new DefaultSubscriber<List<Sort>>(mView._getContext()) {
            @Override
            protected void _onNext(List<Sort> sorts) {
                switch (from){
                    case "ocp":
                        mView.requestOcpSuccessful(sorts);
                        break;

                    case "loantype":
                        mView.requestLoanTypeSuccessful(sorts);
                        break;

                    case "money":
                        mView.requestTimeSuccessful(sorts);
                        break;
                }
            }

            @Override
            protected void _onError(Throwable e) {
                mView.requestSortFailed();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void loadMore(Map<String, String> map) {
        model.loadMore(map, new DefaultSubscriber<List<Object>>(mView) {
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
