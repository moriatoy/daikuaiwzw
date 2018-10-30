package com.fuzhuangfaqianla.xinhuahua.ui.login.contract;

import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

/**
 * Created by xiongchang on 2017/9/26.
 */

public interface LoginContract {
    interface Model {
    }

    interface View extends BaseView {
        void initTitlebar();
        void initViewPager();
        void initIndicatorWidth();
    }

    interface Presenter extends BasePresenter {
    }
}
