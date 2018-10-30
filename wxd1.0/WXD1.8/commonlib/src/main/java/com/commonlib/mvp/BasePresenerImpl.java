package com.commonlib.mvp;

import rx.Subscription;

/**
 * Created by xiongchang on 17/7/8.
 */

public class BasePresenerImpl< T extends BaseView> implements BasePresenter {
    protected Subscription mSubscription;
    protected T mView;

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if(mSubscription != null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
        mView = null;
    }
}
