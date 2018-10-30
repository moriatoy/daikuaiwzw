package com.fuzhuangfaqianla.xinhuahua.ui.me.contract;

import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

/**
 * Created by xiongchang on 2017/9/25.
 */

public interface BrowseContract {
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
