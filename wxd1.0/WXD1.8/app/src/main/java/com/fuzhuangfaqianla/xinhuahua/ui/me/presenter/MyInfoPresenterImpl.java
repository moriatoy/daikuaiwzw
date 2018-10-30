package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.MyInfoContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.model.MyInfoModelImpl;

/**
 * Created by xiongchang on 2017/9/27.
 */

public class MyInfoPresenterImpl extends BasePresenerImpl<MyInfoContract.View> implements MyInfoContract.Presenter {
    private MyInfoContract.Model model;

    public MyInfoPresenterImpl(MyInfoContract.View view){
        mView = view;
        model = new MyInfoModelImpl(mView._getContext());

        mView.initTitlebar();
    }

    @Override
    public void onResume() {
        super.onResume();
        mView.initUserInfo();
    }

    @Override
    public void modifyAvatar(String path) {
        mSubscription = model.modifyAvatar(path, new DefaultSubscriber<String>(mView._getContext(),true) {
            @Override
            protected void _onNext(String s) {
                mView.modifyAvatarSuccessful();
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public String getUploadUrl() {
        return model.getUploadUrl();
    }

}
