package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.commonlib.util.SPUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.SettingContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.SettingPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppBaseActivity<SettingContract.Presenter> implements SettingContract.View {
    @BindView(R.id.setting_activity_ll_modify_pw)
    LinearLayout llModifyPassword;
    @BindView(R.id.setting_activity_ll_logout)
    LinearLayout llLogout;
    String pageName = "设置";

    public SettingActivity() {
        super(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mPresenter = new SettingPresenterImpl(this);
    }

    @Override
    public void initTitlebar() {
        initTitle("设置",getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void judgeLogin() {
        if(LoginHelper.isLogin()){
            llModifyPassword.setVisibility(View.VISIBLE);
            llLogout.setVisibility(View.VISIBLE);
        }else {
            llModifyPassword.setVisibility(View.GONE);
            llLogout.setVisibility(View.GONE);
        }
    }

    @Override
    public void logoutSuccessful() {
        //清除本地内容
        SPUtil.saveString(LoginHelper.ISLOGIN,"0");
        SPUtil.saveObjectToShare(LoginHelper.USER,null);
        finish();
    }

    @OnClick({R.id.setting_activity_ll_vip,R.id.setting_activity_ll_general,R.id.setting_activity_ll_about_us,R.id.setting_activity_ll_faq,R.id.setting_activity_ll_modify_pw,R.id.setting_activity_ll_logout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setting_activity_ll_vip:
                OpenHelper.startActivity(SettingActivity.this,VipActivity.class);
                break;

            case R.id.setting_activity_ll_general:
                OpenHelper.startActivity(SettingActivity.this,NormalActivity.class);
                break;

            case R.id.setting_activity_ll_about_us:
                OpenHelper.startActivity(SettingActivity.this,AboutUsActivity.class);
                break;

            case R.id.setting_activity_ll_faq:
                OpenHelper.startActivity(SettingActivity.this,FAQActivity.class);
                break;

            case R.id.setting_activity_ll_modify_pw:
                Intent intent = new Intent(SettingActivity.this,ModifyPasswordActivity.class);
                intent.putExtra("from","modify");
                OpenHelper.startActivity(SettingActivity.this,intent);
                break;

            case R.id.setting_activity_ll_logout:
                //ZhugeIO
                initClickLogout();

                mPresenter.logout();
                break;
        }
    }

    private void initClickLogout() {
        Map<String,String> map = new HashMap<>();
        map.put("pagename",pageName);
        map.put("name", ZhugeHelper.logout);
        ZhugeHelper.clickButton(this,map);
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","设置");
        ZhugeHelper.browseOther(this,map);
    }
}
