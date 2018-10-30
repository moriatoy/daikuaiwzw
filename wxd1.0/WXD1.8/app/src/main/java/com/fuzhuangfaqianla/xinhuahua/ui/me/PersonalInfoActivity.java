package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.commonlib.adapter.FragmentVpAdapter;
import com.commonlib.util.DialogUtil;
import com.commonlib.util.SPUtil;
import com.commonlib.util.ToastUtil;
import com.commonlib.util.ViewUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.PersonalInfoContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.PersonalInfoPresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalInfoActivity extends AppBaseActivity<PersonalInfoContract.Presenter> implements PersonalInfoContract.View,TabLayout.OnTabSelectedListener {
    @BindView(R.id.personal_info_activity_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.personal_info_activity_viewpager)
    ViewPager viewPager;
    List<Fragment> fragmentList;
    List<String> tabTitleList;
    FragmentVpAdapter adapter;
    BasicInfoFragment basicInfoFragment;
    IdentityInfoFragment identityInfoFragment;
    AssetInfoFragment assetInfoFragment;
    public static User user;
    String pageName = "个人信息";

    public PersonalInfoActivity() {
        super(R.layout.activity_personal_info);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mPresenter = new PersonalInfoPresenterImpl(this);
    }

    @Override
    public void initUserData() {
        user = LoginHelper.getUserInfo();
        //在login之后初始化
//        if(user.getUserInfo()==null){
//            user.setUserInfo(new User.UserInfo());
//        }
    }

    @Override
    public void initTitlebar() {
        initTitle("个人信息",getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTitle.setIv_right(R.drawable.ic_save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ZhugeIo
                clickSave();

                mPresenter.setUserInfo(user);
            }
        });
    }

    private void clickSave() {
        Map<String,String> zhuge_map = new HashMap<>();
        zhuge_map.put("pagename",pageName);
        zhuge_map.put("name", ZhugeHelper.personal_info_save);
        ZhugeHelper.clickButton(this,zhuge_map);
    }

    @Override
    public void setUserInfoSuccessful() {
        ToastUtil.toastShortShow("保存成功");
        //替换偏好设置
        SPUtil.saveObjectToShare(LoginHelper.USER,user);
        finish();
    }

    @Override
    public void setUserInfoFailed() {
        ToastUtil.toastShortShow("保存失败");
    }

    @Override
    public void initViewPager() {
        fragmentList = new ArrayList<>();
        tabTitleList = new ArrayList<>();

        tabLayout.addOnTabSelectedListener(this);

        tabTitleList.add("基本信息");
        tabTitleList.add("身份信息");
        tabTitleList.add("资产信息");

        basicInfoFragment = new BasicInfoFragment();
        identityInfoFragment = new IdentityInfoFragment();
        assetInfoFragment = new AssetInfoFragment();
        fragmentList.add(basicInfoFragment);
        fragmentList.add(identityInfoFragment);
        fragmentList.add(assetInfoFragment);

        viewPager.setOffscreenPageLimit(3);
        adapter = new FragmentVpAdapter(getSupportFragmentManager(),fragmentList,tabTitleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initIndicatorWidth() {
        ViewUtil.changeTabLayoutMargin(tabLayout,27);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();

        //ZhugeIO
        initClick(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void initClick(int position) {
        Map<String,String> map = new HashMap<>();
        map.put("pagename",pageName);
        switch (position){
            case 0:
                map.put("name", ZhugeHelper.essential_information);
                break;
            case 1:
                map.put("name", ZhugeHelper.identify_information);
                break;
            case 2:
                map.put("name", ZhugeHelper.asset_information);
                break;
        }
        ZhugeHelper.clickButton(this,map);
    }

    @Override
    public void onBackPressed() {
        if(!LoginHelper.getUserInfo().equals(user)){
            DialogUtil.getAlertDialog(this, "提醒", "是否保存所修改的信息", "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mPresenter.setUserInfo(user);
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initZhugeEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        basicInfoFragment.onActivityResult(requestCode,resultCode,data);
    }
}
