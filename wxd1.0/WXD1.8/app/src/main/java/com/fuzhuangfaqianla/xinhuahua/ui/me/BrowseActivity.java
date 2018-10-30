package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.commonlib.adapter.FragmentVpAdapter;
import com.commonlib.util.ViewUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BrowseContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.BrowsePresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseActivity extends AppBaseActivity<BrowseContract.Presenter> implements BrowseContract.View,TabLayout.OnTabSelectedListener {
    @BindView(R.id.browse_activity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.browse_activity_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.activity_browse_viewpager)
    ViewPager viewPager;
    List<Fragment> fragmentList;
    List<String> tabTitleList;
    FragmentVpAdapter adapter;
    String pageName = "浏览足迹";

    public BrowseActivity() {
        super(R.layout.activity_browse);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mPresenter = new BrowsePresenterImpl(this);
    }

    @Override
    public void initTitlebar() {
        setSupportActionBar(toolbar);
        initTitle("浏览足迹", getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
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

        BrowseFragment BrowseFragment1 = BrowseFragment.newInstance(0);
        BrowseFragment BrowseFragment2 = BrowseFragment.newInstance(1);
        fragmentList.add(BrowseFragment1);
        fragmentList.add(BrowseFragment2);

        tabTitleList.add("已申请");
        tabTitleList.add("已浏览");

        viewPager.setOffscreenPageLimit(2);
        adapter = new FragmentVpAdapter(getSupportFragmentManager(),fragmentList,tabTitleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initIndicatorWidth() {
        ViewUtil.changeTabLayoutMargin(tabLayout,63);
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
        if(position==0){
            map.put("name", ZhugeHelper.applied);
        }else if(position==1){
            map.put("name", ZhugeHelper.visited);
        }
        ZhugeHelper.clickButton(this,map);
    }

    @Override
    protected void initZhugeEvent() {

    }
}
