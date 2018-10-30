package com.fuzhuangfaqianla.xinhuahua.ui.borrow.contract;

import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;

/**
 * Created by xiongch on 2017/12/7.
 */

public interface BorrowContract {
    interface Model {
    }

    interface View extends BaseView {
        void initStatusBar();
        void initAgentWeb();
    }

    interface Presenter extends BasePresenter {
    }
}
