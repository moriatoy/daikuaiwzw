package com.fuzhuangfaqianla.xinhuahua.ui.me.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BrowseFragmentContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by xiongchang on 2017/9/25.
 */

public class BrowseFragmentModelImpl implements BrowseFragmentContract.Model {
    List<List<Map<String, Object>>> applyDatas;
    List<List<Map<String, Object>>> visitDatas;
    int pageCount;

    @Override
    public Subscription requestApplyData(Map<String,String> map,DefaultSubscriber<List<List<Map<String,Object>>>> subscriber) {
        applyDatas = new ArrayList<>();

        return HttpManager.api.getApplyLoan(map)
                .compose(RxHelper.<Map<String,Object>>handleResult())
                .map(new Func1<Map<String,Object>, List<List<Map<String,Object>>>>() {
                    @Override
                    public List<List<Map<String, Object>>> call(Map<String,Object> stringObjectMap) {
                        pageCount = (int)(double)stringObjectMap.get("pageCount");

                        applyDatas = (List<List<Map<String, Object>>>) stringObjectMap.get("data");
                        return applyDatas;
                    }
                }).subscribe(subscriber);
    }

    @Override
    public Subscription requestVisitData(Map<String,String> map,DefaultSubscriber<List<List<Map<String,Object>>>> subscriber) {
        visitDatas = new ArrayList<>();

        return HttpManager.api.getBrowsedLoan(map)
                .compose(RxHelper.<Map<String,Object>>handleResult())
                .map(new Func1<Map<String,Object>, List<List<Map<String,Object>>>>() {
                    @Override
                    public List<List<Map<String, Object>>> call(Map<String,Object> stringObjectMap) {
                        pageCount = (int)(double)stringObjectMap.get("pageCount");

                        visitDatas = (List<List<Map<String, Object>>>) stringObjectMap.get("data");
                        return visitDatas;
                    }
                })
                .subscribe(subscriber);
    }

    @Override
    public void loadMoreApplyData(Map<String, String> map, DefaultSubscriber<List<List<Map<String, Object>>>> subscriber) {
        HttpManager.api.getApplyLoan(map)
                .compose(RxHelper.<Map<String,Object>>handleResult())
                .map(new Func1<Map<String,Object>, List<List<Map<String,Object>>>>() {
                    @Override
                    public List<List<Map<String, Object>>> call(Map<String,Object> stringObjectMap) {
                        for(List<Map<String, Object>> data : (List<List<Map<String, Object>>>)stringObjectMap.get("data")){
                            applyDatas.add(data);
                        }
                        return applyDatas;
                    }
                }).subscribe(subscriber);
    }

    @Override
    public void loadMoreVisitData(Map<String, String> map, DefaultSubscriber<List<List<Map<String, Object>>>> subscriber) {
        HttpManager.api.getBrowsedLoan(map)
                .compose(RxHelper.<Map<String,Object>>handleResult())
                .map(new Func1<Map<String,Object>, List<List<Map<String,Object>>>>() {
                    @Override
                    public List<List<Map<String, Object>>> call(Map<String,Object> stringObjectMap) {
                        for(List<Map<String, Object>> data : (List<List<Map<String, Object>>>)stringObjectMap.get("data")){
                            visitDatas.add(data);
                        }
                        return visitDatas;
                    }
                }).subscribe(subscriber);
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }
}
