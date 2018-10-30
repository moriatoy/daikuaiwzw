package com.commonlib.mvp;

/**
 * Created by xiongchang on 17/7/8.
 */

public interface RequestCallback<T> {
    /**
     * 请求之前调用
     */
    void onStart();

    /**
     * 请求错误调用
     *
     * @param msg 错误信息
     */
    void onError(String msg);

    /**
     * 请求完成调用
     */
    void onComplete();

    /**
     * 请求成功调用
     *
     * @param data 数据
     */
    void onNext(T data);
}
