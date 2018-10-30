package com.fuzhuangfaqianla.xinhuahua.ui.product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.commonlib.config.ProjectConfig;
import com.commonlib.util.MyLogUtil;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.WebBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ProductClickHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.js.AndroidInterface;
import com.fuzhuangfaqianla.xinhuahua.ui.login.LoginActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.StartBannerContract;
import com.fuzhuangfaqianla.xinhuahua.ui.product.presenter.StartBannerPresenterImpl;
import com.zhuge.analysis.stat.ZhugeSDK;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartBannerActivity extends WebBaseActivity<StartBannerContract.Presenter> implements StartBannerContract.View,AndroidInterface.AndroidInterfaceCallBack {
    @BindView(R.id.start_banner_activity_ll)
    LinearLayout ll;
    @BindView(R.id.start_banner_activity_ll_finish)
    LinearLayout llFinish;
    String url;
    String title;
    StartBanner startBanner;
    String pageName = "";

    public StartBannerActivity() {
        super(R.layout.activity_start_banner);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        startBanner = (StartBanner) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void initZhugeEvent() {

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initLoadService() {
        super.initLoadService();
        ButterKnife.bind(this);
        mPresenter = new StartBannerPresenterImpl(this);
        mLoadService = LoadSir.getDefault().register(agentWeb.getWebCreator().get(), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                agentWeb.getWebCreator().get().reload();
            }
        });
    }

    @Override
    public void initInfo() {
        if(startBanner.getTitle()==null){
            title="";
        }else {
            title = startBanner.getTitle();
        }

        if(ProjectConfig.DEBUG){
            url = ProjectConfig.DEBUG_BASE_URL + "weixin-wxd/index.html#/pop";
        }else {
            url = ProjectConfig.RELEASE_BASE_URL + "weixin-xhh/index.html#/pop";
        }
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
                .setAgentWebParent(ll,new LinearLayout.LayoutParams(-1,-1))
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

        //交互
        agentWeb.getAgentWebSettings().getWebSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        agentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface(this, agentWeb, this));
    }

    private WebViewClient mWebViewClient = new WebViewClient(){

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(StartBannerActivity.this.url.equals(url) || url.equals("") || url==null){
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
    public void jumpToProduct(int productId) {
        MyLogUtil.i("test","productId: "+productId);

        if(LoginHelper.isLogin()==false){
            OpenHelper.startActivity(this,LoginActivity.class);
            return;
        }

        //TODO  属于贷款产品点击量
        ProductClickHelper.addClickProductCount(this,productId);

        Intent intent = new Intent(this,ProductActivity.class);
        intent.putExtra("from",ZhugeHelper.banner_from);
        intent.putExtra("productId",productId+"");
        OpenHelper.startActivity(this,intent);
    }

}
