package com.fuzhuangfaqianla.xinhuahua.ui.login;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.commonlib.adapter.FragmentVpAdapter;
import com.commonlib.util.ViewUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginContract;
import com.fuzhuangfaqianla.xinhuahua.ui.login.presenter.LoginPresenterImpl;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppBaseActivity<LoginContract.Presenter> implements LoginContract.View,TabLayout.OnTabSelectedListener {
    @BindView(R.id.login_activity_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.login_activity_viewpager)
    ViewPager viewPager;
    List<Fragment> fragmentList;
    List<String> tabTitleList;
    FragmentVpAdapter adapter;

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void initTitlebar() {
        initTitle("登录", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initViewPager() {
        fragmentList = new ArrayList<>();
        tabTitleList = new ArrayList<>();

        tabLayout.addOnTabSelectedListener(this);

        LoginPasswordFragment loginPasswordFragment = new LoginPasswordFragment();
        LoginDynamicFragment loginDynamicFragment = new LoginDynamicFragment();
        fragmentList.add(loginPasswordFragment);
        fragmentList.add(loginDynamicFragment);

        tabTitleList.add("密码登录");
        tabTitleList.add("验证码登录");

        viewPager.setOffscreenPageLimit(2);
        adapter = new FragmentVpAdapter(getSupportFragmentManager(),fragmentList,tabTitleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initIndicatorWidth() {
        ViewUtil.changeTabLayoutMargin(tabLayout,29);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void initZhugeEvent() {

    }
}
