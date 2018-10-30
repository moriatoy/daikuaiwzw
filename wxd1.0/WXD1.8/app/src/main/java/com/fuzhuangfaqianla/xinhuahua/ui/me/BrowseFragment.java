package com.fuzhuangfaqianla.xinhuahua.ui.me;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.commonlib.util.ToastUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppLazyBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.inter.CustomRefreshListener;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BrowseFragmentContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.holder.BrowseAdapter;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.BrowseFragmentPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.RefreshHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseFragment extends AppLazyBaseFragment<BrowseFragmentContract.Presenter> implements BrowseFragmentContract.View {
    @BindView(R.id.browse_fragment_refreshlayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.browse_fragment_recyclerview)
    RecyclerView recyclerView;
    BrowseAdapter adapter;
    int position;
    int page = 1;

    public BrowseFragment() {
        super(R.layout.fragment_browse);
    }

    public static BrowseFragment newInstance(int position) {
        BrowseFragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    private void getBundleData() {
        position = getArguments().getInt("position");
    }

    @Override
    public void loadData() {
        startToRefresh();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        getBundleData();
        mPresenter = new BrowseFragmentPresenterImpl(this);
    }

    @Override
    public void initLoadSir(View view) {
        super.initLoadSir(view);
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                startToRefresh();
            }
        });
    }

    @Override
    public View getFragmentReturnLayout(View view) {
        return mLoadService.getLoadLayout();
    }

    @Override
    public void initRefreshLayout() {
        RefreshHelper.initRefreshLayout(refreshLayout, true, new CustomRefreshListener() {
            @Override
            public void _onRefresh(TwinklingRefreshLayout refreshLayout) {
                startToRefresh();
            }

            @Override
            public void _onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                if(page>mPresenter.getPageCount()){
                    ToastUtil.toastShortShow("已加载全部数据");
                    hideLoadMoreLayout();
                    return;
                }

                Map<String,String> map = new HashMap<>();
                map.put("isPage","1");
                map.put("page", page +"");
                if(position==0){
                    mPresenter.loadMoreApplyData(map);
                }else if(position==1){
                    mPresenter.loadMoreVisitData(map);
                }
            }
        });
    }

    private void startToRefresh() {
        page = 1;

        Map<String,String> map = new HashMap<>();
        map.put("isPage","1");
        map.put("page", page +"");
        if(position==0){
            mPresenter.requestApplyData(map);
        }else if(position==1){
            mPresenter.requestVisitData(map);
        }
    }

    @Override
    public void initRecylcerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BrowseAdapter(position);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshRecyclerView(List<List<Map<String,Object>>> datas) {
        adapter.addAll(datas,true);
    }

    @Override
    public void hideRefreshLayout() {
        RefreshHelper.hideRefreshLayout(refreshLayout);
    }

    @Override
    public void hideLoadMoreLayout() {
        RefreshHelper.hideLoadMorLayout(refreshLayout);
    }


    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        if(position==0){
            map.put("name","已申请");
        }else if(position==1){
            map.put("name","已浏览");
        }
        ZhugeHelper.browseOther(getContext(),map);
    }
}
