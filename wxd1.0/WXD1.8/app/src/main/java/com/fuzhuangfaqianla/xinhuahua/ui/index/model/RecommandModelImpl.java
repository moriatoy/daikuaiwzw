package com.fuzhuangfaqianla.xinhuahua.ui.index.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.contract.RecommandContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by xiongchang on 2017/9/22.
 */

public class RecommandModelImpl implements RecommandContract.Model {
    List<Object> items;

    @Override
    public Subscription refreshRecyclerView(Map<String, String> map, DefaultSubscriber<List<Object>> subscriber) {
        items = new ArrayList<>();

        return HttpManager.api.getLoanProductsByRecommendTag(map)
                .compose(RxHelper.<List<OnlineLoan>>handleResult())
                .map(new Func1<List<OnlineLoan>, List<Object>>() {
            @Override
            public List<Object> call(List<OnlineLoan> onlineLoen) {
                for(OnlineLoan data : onlineLoen){
                    items.add(data);
                }
                return items;
            }
        }).subscribe(subscriber);
    }
}
