package com.fuzhuangfaqianla.xinhuahua.js;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.just.library.AgentWeb;

/**
 * Created by xiongchang on 17/7/7.
 */

public class AndroidInterface {
    private AgentWeb agent;
    private Context context;
    private AndroidInterfaceCallBack callBack;

    public AndroidInterface(Context context, AgentWeb agent, AndroidInterfaceCallBack callBack) {
        this.agent = agent;
        this.context = context;
        this.callBack = callBack;
    }

    /*加载贷款详情页*/
    @JavascriptInterface
    public void jumpToProduct(int productId){
        callBack.jumpToProduct(productId);
    }

    public interface AndroidInterfaceCallBack{
        void jumpToProduct(int productId);
    }
}
