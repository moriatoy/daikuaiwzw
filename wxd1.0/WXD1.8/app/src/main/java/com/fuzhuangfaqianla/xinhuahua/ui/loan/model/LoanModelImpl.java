package com.fuzhuangfaqianla.xinhuahua.ui.loan.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoanWrap;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.bean.Sort;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.contract.LoanContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by xiongchang on 2017/9/23.
 */

public class LoanModelImpl implements LoanContract.Model {
    List<Object> items;
    int pageCount;

    @Override
    public Subscription refreshRecyclerView(Map<String,String> map, DefaultSubscriber<List<Object>> subscriber) {
        items = new ArrayList<>();

        return HttpManager.api.selectLoanProductByTag(map)
                .compose(RxHelper.<OnlineLoanWrap>handleResult())
                .map(new Func1<OnlineLoanWrap, List<Object>>() {
            @Override
            public List<Object> call(OnlineLoanWrap onlineLoanWrap) {
                LoanModelImpl.this.pageCount = onlineLoanWrap.getPageCount();

                for(OnlineLoan data : onlineLoanWrap.getData()){
                    items.add(data);
                }
                return items;
            }
        }).subscribe(subscriber);
    }

    @Override
    public void getTagsByType(final String from, Map<String, String> map, DefaultSubscriber<List<Sort>> subscriber) {
        HttpManager.api.getTagsByType(map)
                .compose(RxHelper.<List<String[]>>handleResult())
                .map(new Func1<List<String[]>, List<Sort>>() {
                    @Override
                    public List<Sort> call(List<String[]> strings) {
                        List<Sort> list = new ArrayList<Sort>();
                        list.add(new Sort(from,"-1","不限",true));
                        for(int i=0; i<strings.size(); i++){
                            list.add(new Sort(from,strings.get(i)[0],strings.get(i)[1],false));
                        }
                        return list;
                    }
                }).subscribe(subscriber);
    }

    @Override
    public void loadMore(Map<String, String> map, DefaultSubscriber<List<Object>> subscriber) {
        HttpManager.api.selectLoanProductByTag(map)
                .compose(RxHelper.<OnlineLoanWrap>handleResult())
                .map(new Func1<OnlineLoanWrap, List<Object>>() {
                    @Override
                    public List<Object> call(OnlineLoanWrap onlineLoanWrap) {
                        for(OnlineLoan data : onlineLoanWrap.getData()){
                            items.add(data);
                        }
                        return items;
                    }
                }).subscribe(subscriber);
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }
}
