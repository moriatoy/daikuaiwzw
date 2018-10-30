package com.fuzhuangfaqianla.xinhuahua.ui.product.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.commonlib.http.SchedulerTransformer;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.Apply;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.Intro;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.LoanDetail;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.PinnedHeader;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.PinnedHeaderEntity;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.ProductInfo;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.ProductContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiongchang on 2017/9/28.
 */

public class ProductModelImpl implements ProductContract.Model {
    OnlineLoan onlineLoan;

    @Override
    public Observable<OnlineLoan> requestProductDetailById(String productId) {
        Map<String,String> map = new HashMap<>();
        map.put("productId",productId);
        return HttpManager.api.getLoanProductDetail(map).compose(RxHelper.<OnlineLoan>handleResult()).asObservable();
    }

    @Override
    public Observable<List<Object>> requestProductStep(final OnlineLoan data) {
        return Observable.create(new Observable.OnSubscribe<List<Object>>() {
            @Override
            public void call(Subscriber<? super List<Object>> subscriber) {
                List<Object> items = new ArrayList<>();

                ProductInfo productInfo = new ProductInfo(data.getIconUrl(),data.getLoanRange(),data.getPaymentMethod(),data.getRcmdIndex(),data.getRateType(),data.getRate(),data.getTimeLimit(),data.getApplicants());
                items.add(new PinnedHeaderEntity<ProductInfo>(productInfo, PinnedHeaderEntity.TYPE_PRODUCT_INFO, "产品详情"));

                items.add(new PinnedHeaderEntity<PinnedHeader>(new PinnedHeader("详细介绍"), PinnedHeaderEntity.TYPE_PINNED_HEADER, "详细介绍"));

                items.add(new PinnedHeaderEntity<Intro>(new Intro(data.getDetails()), PinnedHeaderEntity.TYPE_INTRO, "详细介绍"));

                items.add(new PinnedHeaderEntity<PinnedHeader>(new PinnedHeader("贷款计算"), PinnedHeaderEntity.TYPE_PINNED_HEADER, "贷款计算"));

                items.add(new PinnedHeaderEntity<LoanDetail>(new LoanDetail(data.getRateType(),data.getRate()), PinnedHeaderEntity.TYPE_LOAN_DETAIL, "贷款计算"));

                items.add(new PinnedHeaderEntity<PinnedHeader>(new PinnedHeader("申请流程"), PinnedHeaderEntity.TYPE_PINNED_HEADER, "申请流程"));

                items.add(new PinnedHeaderEntity<Apply>(new Apply(data.getApplyProcess()), PinnedHeaderEntity.TYPE_APPLY, "申请流程"));

                subscriber.onNext(items);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Subscription requestRecyclerView(String productId,DefaultSubscriber<List<Object>> subscriber) {
        return requestProductDetailById(productId)
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<OnlineLoan, Observable<List<Object>>>() {
                    @Override
                    public Observable<List<Object>> call(OnlineLoan onlineLoan) {
                        ProductModelImpl.this.onlineLoan = onlineLoan;

                        return requestProductStep(ProductModelImpl.this.onlineLoan);
                    }
                }).compose(new SchedulerTransformer<List<Object>>()).subscribe(subscriber);
    }

    @Override
    public OnlineLoan getOnlineLoan() {
        return onlineLoan;
    }
}
