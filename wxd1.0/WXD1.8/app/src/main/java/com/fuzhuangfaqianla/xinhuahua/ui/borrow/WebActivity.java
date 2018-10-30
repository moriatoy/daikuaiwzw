package com.fuzhuangfaqianla.xinhuahua.ui.borrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.WebBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.borrow.contract.WebContract;
import com.fuzhuangfaqianla.xinhuahua.ui.borrow.presenter.WebPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends WebBaseActivity<WebContract.Presenter> implements WebContract.View {
    @BindView(R.id.web_activity_fl_web_content)
    FrameLayout flWebContent;
    @BindView(R.id.web_activity_fl_status_content)
    FrameLayout flStatusContent;
    @BindView(R.id.web_activity_ll_finish)
    LinearLayout llFinish;
    String url;
    String title;

    public WebActivity() {
        super(R.layout.activity_web);
    }

    public static void startWebActivity(Context context, String title, String url){
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        OpenHelper.startActivity(context,intent,BorrowFragment.REQUEST_OPEN_WEB);
    }

    @Override
    protected void initZhugeEvent() {

    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        url = getIntent().getStringExtra("url");
        if(url == null){
            url = "https://www.baidu.com";
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
        mPresenter = new WebPresenterImpl(this);
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
                .setSecutityType(AgentWeb.SecurityType.strict)
                .setReceivedTitleCallback(mCallBack)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private WebViewClient mWebViewClient = new WebViewClient(){

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(WebActivity.this.url.equals(url) || url.equals("") || url==null){
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
        setResult(RESULT_OK);
        finish();
    }
}
