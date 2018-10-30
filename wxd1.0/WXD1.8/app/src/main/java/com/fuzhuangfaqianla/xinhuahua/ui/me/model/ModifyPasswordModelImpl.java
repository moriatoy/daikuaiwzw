package com.fuzhuangfaqianla.xinhuahua.ui.me.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.ModifyPasswordContract;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/27.
 */

public class ModifyPasswordModelImpl implements ModifyPasswordContract.Model {

    @Override
    public Subscription modifyPassword(Map<String, String> map, DefaultSubscriber<Object> subscriber) {
        return HttpManager.api.modifyPassword(map).compose(RxHelper.<Object>handleResult()).subscribe(subscriber);
    }

    @Override
    public void getCheckCode(Map<String, String> map, DefaultSubscriber<String> subscriber) {
        HttpManager.api.getCheckCode(map).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }
}
