package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.PersonalInfoContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.model.PersonalInfoModelImpl;

/**
 * Created by xiongchang on 2017/9/29.
 */

public class PersonalInfoPresenterImpl extends BasePresenerImpl<PersonalInfoContract.View> implements PersonalInfoContract.Presenter {
    private PersonalInfoContract.Model model;

    public PersonalInfoPresenterImpl(PersonalInfoContract.View view){
        mView = view;
        model = new PersonalInfoModelImpl();

        mView.initUserData();
        mView.initTitlebar();
        mView.initViewPager();
        mView.initIndicatorWidth();
    }

    @Override
    public void setUserInfo(User user) {
        mSubscription = model.setUserInfo(user, new DefaultSubscriber<String>(mView._getContext(),true) {
            @Override
            protected void _onNext(String s) {
                mView.setUserInfoSuccessful();
            }

            @Override
            protected void _onError(Throwable e) {
                mView.setUserInfoFailed();
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }
}
