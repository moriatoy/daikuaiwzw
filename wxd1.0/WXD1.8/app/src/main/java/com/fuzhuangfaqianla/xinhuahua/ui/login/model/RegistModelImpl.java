package com.fuzhuangfaqianla.xinhuahua.ui.login.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.RegistContract;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class RegistModelImpl implements RegistContract.Model {
    @Override
    public Subscription regist(Map<String, String> map, DefaultSubscriber<Object> subscriber) {
        return HttpManager.api.regist(map).compose(RxHelper.handleResult()).subscribe(subscriber);
    }

    @Override
    public void getCheckCode(Map<String, String> map, DefaultSubscriber<String> subscriber) {
        HttpManager.api.getCheckCode(map).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }
}
