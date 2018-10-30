package com.fuzhuangfaqianla.xinhuahua.ui.product.contract;

import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

/**
 * Created by xiongchang on 2017/10/19.
 */

public interface StartBannerContract {
    interface Model {
    }

    interface View extends BaseView {
        void initTitlebar();
        void initInfo();
        void initAgentWeb();
    }

    interface Presenter extends BasePresenter {
    }
}
