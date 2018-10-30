package com.fuzhuangfaqianla.xinhuahua.ui.main.presenter;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.util.AppUtil;
import com.commonlib.util.MyLogUtil;
import com.umeng.analytics.AnalyticsConfig;
import com.commonlib.mvp.BasePresenerImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.BottomInfo;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.Message;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.UpdateApk;
import com.fuzhuangfaqianla.xinhuahua.ui.main.contract.MainContract;
import com.fuzhuangfaqianla.xinhuahua.ui.main.model.MainModelImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiongchang on 2017/9/21.
 */

public class MainPresenterImpl extends BasePresenerImpl<MainContract.View> implements MainContract.Presenter {
    private MainContract.Model model;

    public MainPresenterImpl(MainContract.View view) {
        mView = view;
        model = new MainModelImpl();

        mView.initRxBus();
        mView.getPushIntentData();
        mView.initMenuBar();
        mView.initViewPager();
    }

    @Override
    public void onResume() {
        super.onResume();
//        mView.initStartBanner();
//        mView.updateApp();
    }

    @Override
    public void setDeviceToken(Map<String, String> map) {
        model.setDeviceToken(map, new DefaultSubscriber<String>(mView._getContext()) {
            @Override
            protected void _onNext(String s) {
                MyLogUtil.i("test", "device token set successful: ");
            }

            @Override
            protected void _onError(Throwable e) {
                MyLogUtil.i("test", "device token set failed: ");
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void requestNoticeDetail(Map<String, String> map) {
        model.requestNoticeDetail(map, new DefaultSubscriber<Message>(mView._getContext()) {
            @Override
            protected void _onNext(Message message) {
                mView.requestNoticeDetailSuccessful(message);
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void requestLoanProduct(Map<String, String> map) {
        model.requestLoanProduct(map, new DefaultSubscriber<OnlineLoan>(mView._getContext()) {
            @Override
            protected void _onNext(OnlineLoan onlineLoan) {
                mView.requestLoanProductSuccessful(onlineLoan);
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void requestStartBanner() {
        model.requestStartBanner(new DefaultSubscriber<List<StartBanner>>(mView._getContext()) {
            @Override
            protected void _onNext(List<StartBanner> startBanners) {
                mView.requestStartBannerSuccessful(startBanners);
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void updateApp() {
        //获取渠道
        String channel = AnalyticsConfig.getChannel(mView._getContext());
        if(channel==null){
            channel = "other";
        }
        String packageName = AppUtil.getAppProcessName(mView._getContext());
        MyLogUtil.i("test","channel: "+channel);
        MyLogUtil.i("test","packageName: "+packageName);

        Map<String,String> map = new HashMap<>();
        map.put("channel",channel);
        map.put("packageName",packageName);

        model.requestApkAddress(map, new DefaultSubscriber<List<UpdateApk>>(mView._getContext()) {
            @Override
            protected void _onNext(List<UpdateApk> updateApks) {
                mView.requestApkAddressSuccessful(updateApks);
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @Override
    public void initMenuButton() {
        model.requestMenuButton(new DefaultSubscriber<List<BottomInfo>>(mView._getContext()) {
            @Override
            protected void _onNext(List<BottomInfo> list) {
                mView.initMenuButtonSuccessfulOrFailed(list);
            }

            @Override
            protected void _onError(Throwable e) {
                List<BottomInfo> list = new ArrayList<>();
                list.add(new BottomInfo("秒放款",""));
                list.add(new BottomInfo("秒通过",""));
                mView.initMenuButtonSuccessfulOrFailed(list);
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }
}
