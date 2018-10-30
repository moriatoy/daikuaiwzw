package com.commonlib.http;

import android.util.Log;

import com.commonlib.util.MyLogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created by xiongchang on 17/6/27.
 */

public class RxHelper {

    //对结果进行预处理
    public static <T> Observable.Transformer<BaseModel<T>, T> handleResult(){
        return new Observable.Transformer<BaseModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseModel<T>> baseModelObservable) {
                return baseModelObservable.flatMap(new Func1<BaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseModel<T> result) {
                        MyLogUtil.i("test", "rxhelper --- result from network: " + result);
                        try {
                            if(result != null){
                                if(result.sucess()){
                                    return createData(result.data);
//                                    String message = ErrorMessage.get(10005);
//                                    return Observable.error(new APIException(10005,message));
                                }else {
                                    String message = ErrorMessage.get(result.state);
                                    return Observable.error(new APIException(result.state,message));
                                }
                            }else {
                                return null;
                            }
                        }catch (Exception ex) {
                            if (ex instanceof RuntimeException) {
                                throw ex;
                            } else {
                                throw Exceptions.propagate(ex);
                            }
                        }
                    }
                }).compose(new SchedulerTransformer<T>())
                        .retryWhen(new RetryWhenNetworkException(2, 1000));
            }
        };
    }

    //创建成功的数据
    public static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }
}
