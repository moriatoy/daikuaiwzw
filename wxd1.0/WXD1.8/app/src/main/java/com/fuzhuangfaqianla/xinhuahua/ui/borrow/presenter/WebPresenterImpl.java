package com.fuzhuangfaqianla.xinhuahua.ui.borrow.presenter;

import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.borrow.contract.WebContract;

/**
 * Created by xiongch on 2017/12/7.
 */

public class WebPresenterImpl extends BasePresenerImpl<WebContract.View> implements WebContract.Presenter {

    public WebPresenterImpl(WebContract.View view){
        mView = view;

        mView.initTitlebar();
        mView.initAgentWeb();
    }
}
