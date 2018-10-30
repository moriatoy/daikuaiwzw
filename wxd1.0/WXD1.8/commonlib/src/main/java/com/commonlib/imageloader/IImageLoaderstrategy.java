package com.commonlib.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by xiongchang on 17/7/15.
 */

public interface IImageLoaderstrategy {
    void showImage(@NonNull ImageLoaderOptions options);
    void cleanMemory(Context context);
    //在application的onCreate中初始化
    void init(Context context);
}
