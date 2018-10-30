package com.fuzhuangfaqianla.xinhuahua.ui.me.presenter;

import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.NormalContract;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class NormalPresenterImpl extends BasePresenerImpl<NormalContract.View> implements NormalContract.Presenter {

    public NormalPresenterImpl(NormalContract.View view){
        mView = view;

        mView.initTitle();
        mView.initCache();
    }
}
