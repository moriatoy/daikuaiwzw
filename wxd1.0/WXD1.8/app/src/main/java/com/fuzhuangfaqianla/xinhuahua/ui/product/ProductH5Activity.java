package com.fuzhuangfaqianla.xinhuahua.ui.product;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.commonlib.util.DialogUtil;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.WebBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.ProductH5Contract;
import com.fuzhuangfaqianla.xinhuahua.ui.product.presenter.ProductH5PresenterImpl;
import com.zhuge.analysis.stat.ZhugeSDK;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductH5Activity extends WebBaseActivity<ProductH5Contract.Presenter> implements ProductH5Contract.View {
    @BindView(R.id.product_h5_activity_ll)
    LinearLayout ll;
    @BindView(R.id.product_h5_activity_fl_web_content)
    FrameLayout flWebContent;
    @BindView(R.id.product_h5_activity_fl_status_content)
    FrameLayout flStatusContent;
    @BindView(R.id.product_h5_ll_finish)
    LinearLayout llFinish;
    String title;
    String url;
    String from;

    public ProductH5Activity() {
        super(R.layout.activity_produce_h5);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name",title);
        map.put("from",from);
        ZhugeHelper.browseProductApply(this,map);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        url = getIntent().getStringExtra("url");

        if(url == null){
            url = "https://www.baidu.com";
        }
        from = getIntent().getStringExtra("from");
        if(from == null){
            from = "";
        }
        title = getIntent().getStringExtra("title");
        if(title==null){
            title="";
        }

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initLoadService() {
        super.initLoadService();
        ButterKnife.bind(this);
        mPresenter = new ProductH5PresenterImpl(this);
        mLoadService = LoadSir.getDefault().register(flStatusContent, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                agentWeb.getWebCreator().get().reload();
            }
        });
    }

    @Override
    public void initTitlebar() {
        initTitle(title, getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!agentWeb.back()){
                    onBackPressed();
                }
            }
        });
        llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initAgentWeb() {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWebContent,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()
                .setIndicatorColor(getResources().getColor(R.color.colorMain))
                .setWebViewClient(mWebViewClient)
                .setReceivedTitleCallback(mCallBack)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(url);

        //ZhugeIO
        agentWeb.getAgentWebSettings().getWebSettings().setJavaScriptEnabled(true);
        agentWeb.getWebCreator().get().addJavascriptInterface(new ZhugeSDK.ZhugeJS(),"zhugeTracker");
    }

    private WebViewClient mWebViewClient = new WebViewClient(){

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(ProductH5Activity.this.url.equals(url) || url.equals("") || url==null){
                llFinish.setVisibility(View.INVISIBLE);
            }else {
                llFinish.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            showSuccessfulView();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            showNetworkErrorView();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    };

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallBack = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {

        }
    };

    @Override
    public void onBackPressed() {
        DialogUtil.getAlertDialog(this, "提醒", "是否放弃此次申请", "已申请", "放弃",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //已申请
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("productName",title);
                        mPresenter.apply(map);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //放弃
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("productName",title);
                        mPresenter.visit(map);
                    }
                }).show();
    }

    @Override
    public void recordComplete() {
        finish();
    }
}
