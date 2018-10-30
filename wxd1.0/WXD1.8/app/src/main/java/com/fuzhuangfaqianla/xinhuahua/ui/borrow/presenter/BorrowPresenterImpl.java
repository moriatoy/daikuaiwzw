package com.fuzhuangfaqianla.xinhuahua.ui.borrow.presenter;

import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.borrow.contract.BorrowContract;

/**
 * Created by xiongch on 2017/12/7.
 */

public class BorrowPresenterImpl extends BasePresenerImpl<BorrowContract.View> implements BorrowContract.Presenter {

    public BorrowPresenterImpl(BorrowContract.View view){
        mView = view;

        mView.initStatusBar();
        mView.initAgentWeb();
    }
}
