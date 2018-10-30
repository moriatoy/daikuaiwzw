package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BrowseContract;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class BrowsePresenterImpl extends BasePresenerImpl<BrowseContract.View> implements BrowseContract.Presenter {
    public BrowsePresenterImpl(BrowseContract.View view){
        mView = view;

        mView.initTitlebar();
        mView.initViewPager();
        mView.initIndicatorWidth();
    }
}
