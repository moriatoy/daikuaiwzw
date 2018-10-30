package com.fuzhuangfaqianla.xinhuahua.ui.login.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginDynamicContract;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class LoginDynamicModelImpl implements LoginDynamicContract.Model {
    @Override
    public void getCheckCode(Map<String, String> map, DefaultSubscriber<String> subscriber) {
        HttpManager.api.getCheckCode(map).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }

    @Override
    public Subscription loginByDynamic(Map<String, String> map, DefaultSubscriber<User> subscriber) {
        return HttpManager.api.loginByDynamic(map).compose(RxHelper.<User>handleResult()).subscribe(subscriber);
    }
}
