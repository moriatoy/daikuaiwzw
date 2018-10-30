package com.fuzhuangfaqianla.xinhuahua.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.commonlib.base.BaseActivity;
import com.commonlib.base.BaseApplication;
import com.commonlib.loadsir.EmptyCallback;
import com.commonlib.loadsir.ErrorCallback;
import com.commonlib.loadsir.NetworkErrorCallback;
import com.commonlib.mvp.BasePresenter;
import com.commonlib.mvp.BaseView;
import com.commonlib.util.DialogUtil;
import com.commonlib.util.MeasureUtil;
import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMShareAPI;
import com.fuzhuangfaqianla.xinhuahua.ui.main.MainActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.splash.SplashActivity;

/**
 * Created by xiongchang on 17/7/7.
 */

public abstract class AppBaseActivity<T extends BasePresenter> extends BaseActivity implements BaseView {
    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;

    public AppBaseActivity(int layoutResID) {
        super(layoutResID);
    }

    public AppBaseActivity(int layoutResID, boolean needFinishApp) {
        super(layoutResID, needFinishApp);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initSlideBack();
        PushAgent.getInstance(this).onAppStart();
        initZhugeEvent();
    }

    protected abstract void initZhugeEvent();


    public void changeViewAplha(final View view, RecyclerView recyclerView, final int judgeHight){
        view.getBackground().mutate().setAlpha(0);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int scrollY = recyclerView.computeVerticalScrollOffset();
                //改变透明度
                changeAlpha(view,judgeHight,scrollY);
            }
        });
    }

    private void changeAlpha(View view,int judgeHight,int scrollY) {
        //快速下拉会引起scollY<0
        if(scrollY<0){
            view.getBackground().mutate().setAlpha(0);
            return;
        }
        int bannerHeight = MeasureUtil.dip2px(this,judgeHight);
        float radio = Math.min(1, scrollY/(bannerHeight - view.getHeight()*1f));
        view.getBackground().mutate().setAlpha((int)(radio*0xFF));
    }

    private void initSlideBack() {
        // 默认开启侧滑，默认是整个页码侧滑
        if(BaseApplication.getInstance().getActivityManager().getNum() == 1 || BaseApplication.getInstance().getActivityManager().currentActivity() instanceof SplashActivity || BaseApplication.getInstance().getActivityManager().currentActivity() instanceof MainActivity){
            return;
        }
        mSlideBackLayout = SlideBackHelper.attach(this, BaseApplication.getActivityHelper(), new SlideConfig.Builder()
                // 是否侧滑
                .edgeOnly(true)
                // 是否会屏幕旋转
                .rotateScreen(false)
                // 是否禁止侧滑
                .lock(false)
                // 侧滑的响应阈值，0~1，对应屏幕宽度*percent
                .edgePercent(0.2f)
                // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                .slideOutPercent(0.5f).create(), null);
    }

    @Override
    public void beforeInitView() {
        super.beforeInitView();
        //初始化状态栏
        //ViewUtil.setTranslucentStatusbar(this, getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if(mPresenter != null){
            mPresenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        if(mPresenter != null){
            mPresenter.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public ProgressDialog showProgressDialog(String message) {
        ProgressDialog progressDialog = DialogUtil.getProgressDialog(this,message);
        progressDialog.show();
        return progressDialog;
    }

    public void hideProgressDialog(ProgressDialog progressDialog) {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public Context _getContext() {
        return this;
    }

    @Override
    public void showNetworkErrorView() {
        mLoadService.showCallback(NetworkErrorCallback.class);
    }

    @Override
    public void showEmptyView() {
        mLoadService.showCallback(EmptyCallback.class);
    }

    @Override
    public void showSuccessfulView() {
        mLoadService.showSuccess();
    }

    @Override
    public void showErrorView() {
        mLoadService.showCallback(ErrorCallback.class);
    }
}
