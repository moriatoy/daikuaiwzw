package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.MeContract;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class MePresenterImpl extends BasePresenerImpl<MeContract.View> implements MeContract.Presenter {

    public MePresenterImpl(MeContract.View view){
        mView = view;

        mView.initTitlebar();
    }

    @Override
    public void onResume() {
        super.onResume();
        mView.initUserInfo();
    }
}
