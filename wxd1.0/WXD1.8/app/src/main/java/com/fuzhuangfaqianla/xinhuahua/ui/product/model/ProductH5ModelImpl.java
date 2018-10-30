package com.fuzhuangfaqianla.xinhuahua.ui.product.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.ProductH5Contract;

import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/22.
 */

public class ProductH5ModelImpl implements ProductH5Contract.Model {

    @Override
    public Subscription apply(Map<String, String> map, DefaultSubscriber<String> subscriber) {
        return HttpManager.api.setApplyLoan(map).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }

    @Override
    public Subscription visit(Map<String, String> map, DefaultSubscriber<String> subscriber) {
        return HttpManager.api.setVisitLaon(map).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }
}
