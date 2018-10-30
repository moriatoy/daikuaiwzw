package com.fuzhuangfaqianla.xinhuahua.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.commonlib.base.BaseApplication;
import com.commonlib.config.ProjectConfig;
import com.commonlib.http.SchedulerTransformer;
import com.commonlib.rxbus.RxBus;
import com.commonlib.rxbus.RxBusEvent;
import com.commonlib.util.MyLogUtil;
import com.commonlib.util.SPUtil;
import com.commonlib.util.ToastUtil;
import com.commonlib.widget.MenuBar;
import com.commonlib.widget.NoScrollViewPager;
import com.gyf.barlibrary.ImmersionBar;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.application.MyApplication;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ProductClickHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.manager.DialogManager;
import com.fuzhuangfaqianla.xinhuahua.ui.borrow.WebActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.index.IndexFragment;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.LoanFragment;
import com.fuzhuangfaqianla.xinhuahua.ui.login.LoginActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.BottomInfo;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.Message;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.UpdateApk;
import com.fuzhuangfaqianla.xinhuahua.ui.main.contract.MainContract;
import com.fuzhuangfaqianla.xinhuahua.ui.main.presenter.MainPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.MeFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.product.ProductH5Activity;
import com.zhuge.analysis.stat.ZhugeSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.functions.Action1;
import util.UpdateAppUtils;

@RuntimePermissions
public class MainActivity extends AppBaseActivity<MainContract.Presenter> implements MainContract.View,MenuBar.OnMenuSelectedListener {
    @BindView(R.id.main_activity_viewpager)
    NoScrollViewPager viewPager;
    MenuBar menuBar;
    IndexFragment indexFragment;
    LoanFragment loanFragment;
    MeFragment meFragment;
    List<Fragment> fragmentList;
    String after_open = "";
    String url = "";
    String activity = "";
    String custom = "";
    long last_open_mill;
    long interval = 8*60*60*1000;
    String pageName = "首页";

    public MainActivity() {
        super(R.layout.activity_main,true);
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mPresenter = new MainPresenterImpl(this);
        initMenuButton();
        initStartBanner();
        updateApp();
        //设置deviceToken 用于判断是否登录
        RxBus.getDefault().post(new RxBusEvent(701,"set_device_token"));
    }

    @Override
    public void getPushIntentData() {
        after_open = getIntent().getStringExtra("after_open");
        url = getIntent().getStringExtra("url");
        if(url == null){
            url = "";
        }
        activity = getIntent().getStringExtra("activity");
        if(activity == null){
            activity = "";
        }
        custom = getIntent().getStringExtra("custom");
        if(custom == null){
            custom = "";
        }

        if(after_open != null){
            if(LoginHelper.isLogin()){
                if(after_open.equals("notice")){
                    Map<String,String> map = new HashMap<>();
                    map.put("noticeId",url);

                    mPresenter.requestNoticeDetail(map);
                }else if(after_open.equals("loanProduct")){
                    Map<String,String> map = new HashMap<>();
                    map.put("productId",url);

                    mPresenter.requestLoanProduct(map);
                }
            }else {
                OpenHelper.startActivity(this, LoginActivity.class);
            }
        }
    }

    @Override
    public void requestNoticeDetailSuccessful(Message message) {
        Intent intent = new Intent(this,MessageDetailActivity.class);
        intent.putExtra("data",message);
        OpenHelper.startActivity(this,intent);
    }

    @Override
    public void requestLoanProductSuccessful(OnlineLoan onlineLoan) {
        Intent intent = new Intent(MainActivity.this,ProductH5Activity.class);
        intent.putExtra("from", ZhugeHelper.push_from);
        intent.putExtra("title",onlineLoan.getProductName());
        intent.putExtra("url",onlineLoan.getProductUrl());
        OpenHelper.startActivity(this,intent);
    }

    @Override
    public void initStartBanner() {
        last_open_mill = SPUtil.getLong("last_open_mill");
        MyLogUtil.i("test", "last_open_mill: "+last_open_mill);

        long current_mill = System.currentTimeMillis();
        //存储
        SPUtil.saveLong("last_open_mill",current_mill);
        if(ProjectConfig.DEBUG){
            interval = 0;
        }
        if((current_mill - last_open_mill) > interval){
            Log.i("test", "show start banner: ");
            mPresenter.requestStartBanner();
        }
    }

    @Override
    public void requestStartBannerSuccessful(List<StartBanner> startBanners) {
        //显示弹窗
        DialogManager.showStartBannerDialog(this,startBanners);
    }

    @Override
    public void updateApp() {
        mPresenter.updateApp();
    }

    @Override
    public void requestApkAddressSuccessful(List<UpdateApk> updateApks) {
        if(updateApks==null || updateApks.size()==0){
            return;
        }
        final UpdateApk updateApk = updateApks.get(0);

        if(Build.VERSION.SDK_INT<23){
            updateApk(updateApk);
        }else {
            //申请权限
            MainActivityPermissionsDispatcher.updateApkWithPermissionCheck(this,updateApk);
        }
    }

    private void initMenuButton() {
        mPresenter.initMenuButton();
    }

    @Override
    public void initMenuButtonSuccessfulOrFailed(final List<BottomInfo> list) {
        menuBar.tv_menu3.setText(list.get(0).getName());
        menuBar.tv_menu4.setText(list.get(1).getName());
        menuBar.ll_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductClickHelper.addBottomButtonClick(MainActivity.this,1);
                WebActivity.startWebActivity(MainActivity.this, list.get(0).getName(), list.get(0).getUrl());
            }
        });
        menuBar.ll_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductClickHelper.addBottomButtonClick(MainActivity.this,2);
                WebActivity.startWebActivity(MainActivity.this, list.get(1).getName(), list.get(1).getUrl());
            }
        });
    }

    @Override
    public void initMenuBar() {
        menuBar = new MenuBar(this,this);
    }

    @Override
    public void initViewPager() {
        fragmentList = new ArrayList<>();
        indexFragment = new IndexFragment();
        loanFragment = new LoanFragment();
        meFragment = new MeFragment();
        fragmentList.add(indexFragment);
        fragmentList.add(loanFragment);
        fragmentList.add(meFragment);

        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }

    @Override
    public void onMenuSelected(int position) {
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true,0.2f);
        mImmersionBar.init();
        switch (position){
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                viewPager.setCurrentItem(3);
                break;
        }


        //ZhugeIO
        initClick(position);
    }

    private void initClick(int position) {
        Map<String,String> map = new HashMap<>();
        map.put("pagename",pageName);
        switch (position){
            case 0:
                map.put("name",ZhugeHelper.homepage);
                break;
            case 1:
                map.put("name",ZhugeHelper.loan);
                break;
            case 2:
                map.put("name",ZhugeHelper.mine);
                break;
        }

        ZhugeHelper.clickButton(this,map);
    }

    @Override
    public void initRxBus() {
        rxBusSubscription = RxBus.getDefault().toObservable(RxBusEvent.class)
                .compose(new SchedulerTransformer<RxBusEvent>())
                .subscribe(new Action1<RxBusEvent>() {
            @Override
            public void call(RxBusEvent rxBusEvent) {
                switch (rxBusEvent.getName()){
                    case "set_device_token":
                        if(MyApplication.mDeviceToken!=null && LoginHelper.isLogin()) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("deviceToken", MyApplication.mDeviceToken);
                            map.put("deviceType", "android");

                            mPresenter.setDeviceToken(map);
                        }
                        break;

                    case "login_expired_from_default_subscriber":
                        //清除本地内容
                        SPUtil.saveString(LoginHelper.ISLOGIN,"0");
                        SPUtil.saveObjectToShare(LoginHelper.USER,null);
                        OpenHelper.startActivity(MainActivity.this,LoginActivity.class);
                        BaseApplication.getInstance().getActivityManager().popAllActivityExcept(MainActivity.class,LoginActivity.class);
                        break;
                }
            }
        });
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void updateApk(UpdateApk updateApk) {
        //开关
        if(updateApk.getAutoUpdate()==null || updateApk.getAutoUpdate()==false){
            return;
        }

        if(updateApk.getDownloadUrl()==null || updateApk.getDownloadUrl().equals("")){
            MyLogUtil.i("test","download url is null");
            return;
        }
        if(updateApk.getForceUpdate()==null){
            updateApk.setForceUpdate(false);
        }

        UpdateAppUtils.from(this)
                .serverVersionCode(Integer.parseInt(updateApk.getVersionCode()))
                .serverVersionName(updateApk.getVersionName())
                .apkPath(updateApk.getDownloadUrl())
                .isForce(updateApk.getForceUpdate())
                .update();
    }



    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForPermission(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("需要获取一些权限")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForPermission() {
        ToastUtil.toastShortShow("获取权限失败");
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForPermission() {
        ToastUtil.toastShortShow("获取权限失败");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    protected void initZhugeEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZhugeSDK.getInstance().flush(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        borrowFragment.onActivityResult(requestCode, resultCode, data);
    }
}
