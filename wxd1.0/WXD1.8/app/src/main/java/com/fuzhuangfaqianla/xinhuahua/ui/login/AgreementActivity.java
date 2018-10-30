package com.fuzhuangfaqianla.xinhuahua.ui.login;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.zhuge.analysis.stat.ZhugeSDK;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgreementActivity extends AppBaseActivity {
    @BindView(R.id.agreement_activity_ll_content)
    LinearLayout llContent;
    @BindView(R.id.agreement_activity_webview)
    WebView webView;

    public AgreementActivity() {
        super(R.layout.activity_agreement);
    }

    @Override
    protected void initZhugeEvent() {

    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitlebar();
        initData();
    }

    private void initData() {
        //ZhugeIO
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new ZhugeSDK.ZhugeJS(),"zhugeTracker");

        Map<String,String> map = new HashMap<>();
        map.put("key","userAgreement");
        HttpManager.api.getSystemInfoByKey(map)
                .compose(RxHelper.<String>handleResult())
                .subscribe(new DefaultSubscriber<String>(this,true) {
            @Override
            protected void _onNext(String s) {
                webView.loadDataWithBaseURL(null,s,"text/html", "UTF-8",null);
            }

            @Override
            protected void _onError(Throwable e) {
            }

            @Override
            protected void _onCompleted() {
            }
        });
    }

    private void initTitlebar() {
        initTitle("花钱啦注册协议", getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
