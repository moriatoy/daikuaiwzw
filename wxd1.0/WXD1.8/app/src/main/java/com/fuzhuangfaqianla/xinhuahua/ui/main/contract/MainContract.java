package com.fuzhuangfaqianla.xinhuahua.ui.main.contract;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.BottomInfo;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.Message;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.UpdateApk;

import java.util.List;
import java.util.Map;

/**
 * Created by xiongchang on 2017/9/21.
 */

public interface MainContract {
    interface Model {
        void setDeviceToken(Map<String, String> map, DefaultSubscriber<String> subscriber);
        void requestNoticeDetail(Map<String, String> map, DefaultSubscriber<Message> subscriber);
        void requestLoanProduct(Map<String, String> map, DefaultSubscriber<OnlineLoan> subscriber);
        void requestStartBanner(DefaultSubscriber<List<StartBanner>> subscriber);
        void requestApkAddress(Map<String,String> map, DefaultSubscriber<List<UpdateApk>> subscriber);
        void requestMenuButton(DefaultSubscriber<List<BottomInfo>> subscriber);
    }

    interface View extends BaseView {
        void initRxBus();
        void getPushIntentData();
        void initStartBanner();
        void updateApp();
        void initMenuBar();
        void initViewPager();
        void requestNoticeDetailSuccessful(Message message);
        void requestLoanProductSuccessful(OnlineLoan onlineLoan);
        void requestStartBannerSuccessful(List<StartBanner> startBanners);
        void requestApkAddressSuccessful(List<UpdateApk> updateApks);
        void initMenuButtonSuccessfulOrFailed(List<BottomInfo> list);
    }

    interface Presenter extends BasePresenter {
        void setDeviceToken(Map<String, String> map);
        void requestNoticeDetail(Map<String, String> map);
        void requestLoanProduct(Map<String, String> map);
        void requestStartBanner();
        void updateApp();
        void initMenuButton();
    }
}
