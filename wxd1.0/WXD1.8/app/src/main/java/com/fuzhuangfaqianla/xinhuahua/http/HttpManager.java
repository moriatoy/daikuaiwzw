package com.fuzhuangfaqianla.xinhuahua.http;

import com.commonlib.http.RetrofitAsyncTask;

/**
 * Created by xiongchang on 2017/8/16.
 */

public class HttpManager {

    public static Api api = RetrofitAsyncTask.getInstance().getRetrofit().create(Api.class);

}
