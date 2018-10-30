package com.fuzhuangfaqianla.xinhuahua.ui.me.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.SettingContract;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class SettingModelImpl implements SettingContract.Model {

    @Override
    public Subscription logout(DefaultSubscriber<Object> subscriber) {
        return HttpManager.api.logout().compose(RxHelper.<Object>handleResult()).subscribe(subscriber);
    }
}
