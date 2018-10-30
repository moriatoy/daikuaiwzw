package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BasicInfoContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.model.BasicInfoModelImpl;

import java.util.List;

/**
 * Created by xiongchang on 2017/9/29.
 */

public class BasicInfoPresenterImpl extends BasePresenerImpl<BasicInfoContract.View> implements BasicInfoContract.Presenter {
    private BasicInfoContract.Model model;

    public BasicInfoPresenterImpl(BasicInfoContract.View view){
        mView = view;
        model = new BasicInfoModelImpl(mView._getContext());

        mView.initInfo();
    }

    @Override
    public void requestArea() {
        model.requestArea(new DefaultSubscriber<List<Object>>(mView._getContext()) {
            @Override
            protected void _onNext(List<Object> datas) {
                mView.requestAreaSuccessful(datas);
            }

            @Override
            protected void _onError(Throwable e) {
                mView.requestAreaFailed();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

}
