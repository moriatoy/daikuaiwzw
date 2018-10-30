package com.fuzhuangfaqianla.xinhuahua.ui.borrow.contract;

import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

/**
 * Created by xiongch on 2017/12/7.
 */

public interface WebContract {
    interface Model {
    }

    interface View extends BaseView {
        void initTitlebar();
        void initAgentWeb();
    }

    interface Presenter extends BasePresenter {
    }
}
