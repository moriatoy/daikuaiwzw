package com.fuzhuangfaqianla.xinhuahua.base;

import android.view.KeyEvent;

import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.just.library.AgentWeb;

/**
 * Created by xiongchang on 2017/9/22.
 */

public abstract class WebBaseActivity<T extends BasePresenter> extends AppBaseActivity implements BaseView {
    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;

    public AgentWeb agentWeb;

    public WebBaseActivity(int layoutResID) {
        super(layoutResID);
    }

    public WebBaseActivity(int layoutResID, boolean needFinishApp) {
        super(layoutResID, needFinishApp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb!=null && agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if(agentWeb!=null)
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        if(agentWeb!=null)
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(agentWeb!=null)
            agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

}
