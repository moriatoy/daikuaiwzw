package com.commonlib.http;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongchang on 17/6/27.
 */

public class SchedulerTransformer<T> implements Observable.Transformer<T, T>  {
    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SchedulerTransformer<T> create() {
        return new SchedulerTransformer<>();
    }
}
