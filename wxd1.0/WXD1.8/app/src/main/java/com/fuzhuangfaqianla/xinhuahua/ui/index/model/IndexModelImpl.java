package com.fuzhuangfaqianla.xinhuahua.ui.index.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.commonlib.http.SchedulerTransformer;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.Carousel;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.CarouselList;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoanWrap;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.RecommandTag;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.RecommandTagList;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommend;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommendList;
import com.fuzhuangfaqianla.xinhuahua.ui.index.contract.IndexContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func4;

/**
 * Created by xiongchang on 2017/9/21.
 */

public class IndexModelImpl implements IndexContract.Model {
    List<Object> items;
    int pageCount;

    @Override
    public Subscription refreshRecyclerView(Map<String,String> map,DefaultSubscriber<List<Object>> subscriber) {
        items = new ArrayList<>();

        return Observable.zip(requestCarousel(),
                requestRecommandTag(),
                requestTodayRecommend(),
                requestOnlineLoanProduct(map),
                new Func4<List<Carousel>, List<RecommandTag>, List<TodayRecommend>, List<OnlineLoan>, List<Object>>() {
                    @Override
                    public List<Object> call(List<Carousel> carousels, List<RecommandTag> recommandTags, List<TodayRecommend> todayRecommends, List<OnlineLoan> onlineLoens) {
                        items.add(new CarouselList(carousels));

                        items.add(new RecommandTagList(recommandTags));

                        items.add(new TodayRecommendList(todayRecommends));

                        for(OnlineLoan onlineLoan : onlineLoens){
                            items.add(onlineLoan);
                        }

                        return items;
                    }
                }
        ).compose(new SchedulerTransformer<List<Object>>()).subscribe(subscriber);
    }

    @Override
    public Observable<List<Carousel>> requestCarousel() {
        return HttpManager.api.getCarouselPic().compose(RxHelper.<List<Carousel>>handleResult()).asObservable();
    }

    @Override
    public Observable<List<RecommandTag>> requestRecommandTag() {
        return HttpManager.api.getRecommandTag().compose(RxHelper.<List<RecommandTag>>handleResult()).asObservable();
    }

    @Override
    public Observable<List<TodayRecommend>> requestTodayRecommend() {
//        return HttpManager.api.getApplets().compose(RxHelper.<Applet>handleResult()).asObservable();
        return HttpManager.api.getTodayRecommend().compose(RxHelper.<List<TodayRecommend>>handleResult()).asObservable();
    }

    @Override
    public Observable<List<OnlineLoan>> requestOnlineLoanProduct(Map<String,String> map) {
        return HttpManager.api.getOnlineLoanList(map)
                .compose(RxHelper.<OnlineLoanWrap>handleResult())
                .map(new Func1<OnlineLoanWrap, List<OnlineLoan>>() {
            @Override
            public List<OnlineLoan> call(OnlineLoanWrap onlineLoanWrap) {
                IndexModelImpl.this.pageCount = onlineLoanWrap.getPageCount();

                return onlineLoanWrap.getData();
            }
        }).asObservable();
    }

    @Override
    public void loadMore(Map<String,String> map, final DefaultSubscriber<List<Object>> subscriber) {
        HttpManager.api.getOnlineLoanList(map)
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
