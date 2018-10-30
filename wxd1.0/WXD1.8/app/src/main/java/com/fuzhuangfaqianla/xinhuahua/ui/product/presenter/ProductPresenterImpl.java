package com.fuzhuangfaqianla.xinhuahua.ui.product.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.ProductContract;
import com.fuzhuangfaqianla.xinhuahua.ui.product.model.ProductModelImpl;

import java.util.List;

/**
 * Created by xiongchang on 2017/9/28.
 */

public class ProductPresenterImpl extends BasePresenerImpl<ProductContract.View> implements ProductContract.Presenter {
    private ProductContract.Model model;

    public ProductPresenterImpl(ProductContract.View view){
        mView = view;
        model = new ProductModelImpl();

        //网络请求之前初始化
        mView.initTitlebar("");

        requestRecyclerView();
    }

    @Override
    public void requestRecyclerView() {
        model.requestRecyclerView(mView.getProductId(),new DefaultSubscriber<List<Object>>(mView) {
            @Override
            protected void _onNext(List<Object> objects) {
                mView.setOnlineData(model.getOnlineLoan());
                mView.initZhuge();
                mView.initTitlebar(model.getOnlineLoan().getProductName());
                mView.initProductInfo();
                mView.initRecyclerView();
                mView.initCollapsedAnimation();
                mView.initBottomAnimation();
                mView.refreshRecyclerView(objects);
            }

            @Override
            protected void _onError(Throwable e) {
            }

            @Override
            protected void _onCompleted() {
            }
        });
    }

}
