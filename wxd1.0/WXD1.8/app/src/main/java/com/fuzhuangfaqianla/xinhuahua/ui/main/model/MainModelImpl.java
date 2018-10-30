package com.fuzhuangfaqianla.xinhuahua.ui.main.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.BottomInfo;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.Message;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.UpdateApk;
import com.fuzhuangfaqianla.xinhuahua.ui.main.contract.MainContract;

import java.util.List;
import java.util.Map;

/**
 * Created by xiongchang on 2017/9/21.
 */

public class MainModelImpl implements MainContract.Model {

    @Override
    public void setDeviceToken(Map<String, String> map, DefaultSubscriber<String> subscriber) {
        HttpManager.api.setDeviceToken(map).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }

    @Override
    public void requestNoticeDetail(Map<String, String> map, DefaultSubscriber<Message> subscriber) {
        HttpManager.api.getNoticeDetail(map).compose(RxHelper.<Message>handleResult()).subscribe(subscriber);
    }

    @Override
    public void requestLoanProduct(Map<String, String> map, DefaultSubscriber<OnlineLoan> subscriber) {
        HttpManager.api.getLoanProductDetail(map).compose(RxHelper.<OnlineLoan>handleResult()).subscribe(subscriber);
    }

    @Override
    public void requestStartBanner(DefaultSubscriber<List<StartBanner>> subscriber) {
        HttpManager.api.getStartBanner().compose(RxHelper.<List<StartBanner>>handleResult()).subscribe(subscriber);
    }

    @Override
    public void requestApkAddress(Map<String, String> map, DefaultSubscriber<List<UpdateApk>> subscriber) {
        HttpManager.api.getApkAddress(map).compose(RxHelper.<List<UpdateApk>>handleResult()).subscribe(subscriber);
    }

    @Override
    public void requestMenuButton(DefaultSubscriber<List<BottomInfo>> subscriber) {
        HttpManager.api.getWXDMenuButton().compose(RxHelper.<List<BottomInfo>>handleResult()).subscribe(subscriber);
    }
}
