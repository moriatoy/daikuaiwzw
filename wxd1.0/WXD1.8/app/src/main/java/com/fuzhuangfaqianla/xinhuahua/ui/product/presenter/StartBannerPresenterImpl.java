package com.fuzhuangfaqianla.xinhuahua.ui.product.presenter;

import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.StartBannerContract;

/**
 * Created by xiongchang on 2017/10/19.
 */

public class StartBannerPresenterImpl extends BasePresenerImpl<StartBannerContract.View> implements StartBannerContract.Presenter {

    public StartBannerPresenterImpl(StartBannerContract.View view){
        mView = view;

        mView.initInfo();
        mView.initTitlebar();
        mView.initAgentWeb();
    }
}
