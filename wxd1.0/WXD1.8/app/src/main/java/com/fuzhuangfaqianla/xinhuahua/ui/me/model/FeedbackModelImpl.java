package com.fuzhuangfaqianla.xinhuahua.ui.me.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.FeedbackContract;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class FeedbackModelImpl implements FeedbackContract.Model {
    @Override
    public Subscription feedback(Map<String, String> map, DefaultSubscriber<String> subscriber) {
        return HttpManager.api.setUserFeedBack(map).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }
}
