package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.view.View;
import android.webkit.WebView;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutCompanyActivity extends AppBaseActivity {
    @BindView(R.id.agree_activity_webview)
    WebView webView;

    public AboutCompanyActivity() {
        super(R.layout.activity_about_company);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitlebar();
        getSystemInfo();
    }

    private void initTitlebar() {
        initTitle("关于花钱啦", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getSystemInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("key", "aboutUs");
        HttpManager.api.getSystemInfoByKey(map)
                .compose(RxHelper.<String>handleResult())
                .subscribe(new DefaultSubscriber<String>(_getContext()) {
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


    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","关于花钱啦");
        ZhugeHelper.browseOther(this,map);
    }
}
