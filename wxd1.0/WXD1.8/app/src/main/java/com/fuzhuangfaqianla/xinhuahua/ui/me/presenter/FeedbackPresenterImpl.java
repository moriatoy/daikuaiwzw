package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.FeedbackContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.model.FeedbackModelImpl;

import java.util.Map;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class FeedbackPresenterImpl extends BasePresenerImpl<FeedbackContract.View> implements FeedbackContract.Presenter {
    private FeedbackContract.Model model;

    public FeedbackPresenterImpl(FeedbackContract.View view){
        mView = view;
        model = new FeedbackModelImpl();

        mView.initTitlebar();
        mView.showKeyboard();
    }

    @Override
    public void feedback(Map<String, String> map) {
        mSubscription = model.feedback(map, new DefaultSubscriber<String>(mView._getContext(),true) {
            @Override
            protected void _onNext(String o) {
                mView.feedbackSuccessful();
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
