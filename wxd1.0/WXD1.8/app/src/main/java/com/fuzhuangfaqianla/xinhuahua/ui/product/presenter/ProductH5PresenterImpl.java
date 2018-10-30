package com.fuzhuangfaqianla.xinhuahua.ui.product.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.ProductH5Contract;
import com.fuzhuangfaqianla.xinhuahua.ui.product.model.ProductH5ModelImpl;

import java.util.Map;

/**
 * Created by xiongchang on 2017/9/22.
 */

public class ProductH5PresenterImpl extends BasePresenerImpl<ProductH5Contract.View> implements ProductH5Contract.Presenter {
    private ProductH5Contract.Model model;

    public ProductH5PresenterImpl(ProductH5Contract.View view){
        mView = view;
        model = new ProductH5ModelImpl();

        mView.initTitlebar();
        mView.initAgentWeb();
    }

    @Override
    public void apply(Map<String, String> map) {
        mSubscription = model.apply(map, new DefaultSubscriber<String>(mView._getContext()) {
            @Override
            protected void _onNext(String s) {
                mView.recordComplete();
            }

            @Override
            protected void _onError(Throwable e) {
                mView.recordComplete();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void visit(Map<String, String> map) {
        mSubscription = model.visit(map, new DefaultSubscriber<String>(mView._getContext()) {
            @Override
            protected void _onNext(String s) {
                mView.recordComplete();
            }

            @Override
            protected void _onError(Throwable e) {
                mView.recordComplete();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }
}
