package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

/**
 * Created by xiongchang on 2017/9/25.
 */

public interface NormalContract {
    interface Model {
    }

    interface View extends BaseView {
        void initTitle();
        void initCache();
    }

    interface Presenter extends BasePresenter {
    }
}
