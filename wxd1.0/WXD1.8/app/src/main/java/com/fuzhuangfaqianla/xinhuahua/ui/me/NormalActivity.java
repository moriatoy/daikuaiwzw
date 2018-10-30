package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.commonlib.util.CleanCacheUtil;
import com.commonlib.util.DialogUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.NormalContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.NormalPresenterImpl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalActivity extends AppBaseActivity<NormalContract.Presenter> implements NormalContract.View {
    @BindView(R.id.normal_activity_tv_cache)
    TextView tvCache;
    String pageName = "通用—清除缓存";

    public NormalActivity() {
        super(R.layout.activity_normal);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mPresenter = new NormalPresenterImpl(this);
    }

    @Override
    public void initTitle() {
        initTitle("通用", getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initCache() {
        try{
            tvCache.setText(CleanCacheUtil.getTotalCacheSize(NormalActivity.this));
        }catch (Exception e){
            tvCache.setText("0.00MB");
        }
    }

    @OnClick({R.id.normal_activity_ll_cache})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.normal_activity_ll_cache:
                DialogUtil.getAlertDialog(NormalActivity.this, "", "是否清除缓存", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ZhugeIO
                        clickConfirm();

                        CleanCacheUtil.clearAllCache(NormalActivity.this);
                        try {
                            tvCache.setText(CleanCacheUtil.getTotalCacheSize(NormalActivity.this));
                        } catch (Exception e) {
                            tvCache.setText("0.00MB");
                        }
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ZhugeIO
                        clickCancel();

                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }

    private void clickCancel() {
        Map<String,String> map = new HashMap<>();
        map.put("pagename",pageName);
        map.put("name", ZhugeHelper.cancel_clear_cache);
        ZhugeHelper.clickButton(this,map);
    }

    private void clickConfirm() {
        Map<String,String> map = new HashMap<>();
        map.put("pagename",pageName);
        map.put("name", ZhugeHelper.confirm_clear_cache);
        ZhugeHelper.clickButton(this,map);
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","通用");
        ZhugeHelper.browseOther(this,map);
    }
}
