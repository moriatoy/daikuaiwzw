package com.fuzhuangfaqianla.xinhuahua.ui.borrow;


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

import com.gyf.barlibrary.ImmersionBar;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppLazyBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.ui.borrow.contract.BorrowContract;
import com.fuzhuangfaqianla.xinhuahua.ui.borrow.presenter.BorrowPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BorrowFragment extends AppLazyBaseFragment<BorrowContract.Presenter> implements BorrowContract.View {
    @BindView(R.id.borrow_fragment_view_status)
    View statusView;
    @BindView(R.id.borrow_fragment_fl_web_content)
    FrameLayout flWebContent;
    @BindView(R.id.borrow_fragment_fl_status_content)
    FrameLayout flStatusContent;
    AgentWeb agentWeb;
    AgentWeb.PreAgentWeb preAgentWeb;
    public static final int REQUEST_OPEN_WEB = 101;

    public BorrowFragment() {
        super(R.layout.fragment_borrow);
    }

    @Override
    public void loadData() {
        agentWeb = preAgentWeb.go("https://jinshuju.net/f/jznwaL");
    }

    @Override
    protected void initZhugeEvent() {

    }

    @Override
    public void initLoadSir(View view) {
        super.initLoadSir(view);
        ButterKnife.bind(this,view);
        mLoadService = LoadSir.getDefault().register(flStatusContent, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                agentWeb.getWebCreator().get().reload();
            }
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mPresenter = new BorrowPresenterImpl(this);
    }

    @Override
    public void initStatusBar() {
        ImmersionBar.setTitleBar(getActivity(),statusView);
    }

    @Override
    public void initAgentWeb() {
        preAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWebContent,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator(getResources().getColor(R.color.colorMain))
                .setWebViewClient(mWebViewClient)
                .setReceivedTitleCallback(mCallBack)
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready();
    }

    private WebViewClient mWebViewClient = new WebViewClient(){

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
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

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebActivity.startWebActivity(getActivity(),"",url);
            return true;
        }
    };

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallBack = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {

        }
    };

    @Override
    public void onPause() {
        if(agentWeb!=null)
            agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if(agentWeb!=null)
            agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(agentWeb!=null)
            agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_OPEN_WEB && resultCode == getActivity().RESULT_OK){
            agentWeb.getWebCreator().get().reload();
        }
    }
}
