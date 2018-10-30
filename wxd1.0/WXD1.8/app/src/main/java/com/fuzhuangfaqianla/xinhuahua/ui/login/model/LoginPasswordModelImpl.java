package com.fuzhuangfaqianla.xinhuahua.ui.login.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginPasswordContract;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/26.
 */

public class LoginPasswordModelImpl implements LoginPasswordContract.Model {

    @Override
    public Subscription loginByPassword(Map<String, String> map, DefaultSubscriber<User> subscriber) {
        return HttpManager.api.loginByPassword(map).compose(RxHelper.<User>handleResult()).subscribe(subscriber);
    }
}
