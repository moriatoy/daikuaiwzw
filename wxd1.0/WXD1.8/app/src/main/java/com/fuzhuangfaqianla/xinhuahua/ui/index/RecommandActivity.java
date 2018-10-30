package com.fuzhuangfaqianla.xinhuahua.ui.index;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.inter.CustomRefreshListener;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.contract.RecommandContract;
import com.fuzhuangfaqianla.xinhuahua.ui.index.holder.RecommandViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.index.presenter.RecommandPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.RefreshHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;

public class RecommandActivity extends AppBaseActivity<RecommandContract.Presenter> implements RecommandContract.View {
    @BindView(R.id.recommand_activity_refreshlayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.recommand_activity_recyclerview)
    RecyclerView recyclerView;
    String title;
    int recommandTagId;
    MultiTypeAdapter adapter;

    public RecommandActivity() {
        super(R.layout.activity_recommand);
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","为你推荐");
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        recommandTagId = getIntent().getIntExtra("tagId",0);
        title = getIntent().getStringExtra("title");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        loadingDialog = showLoadingDialog();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initLoadService() {
        super.initLoadService();
        ButterKnife.bind(this);
        mPresenter = new RecommandPresenterImpl(this);
        startToRefresh();

        mLoadService = LoadSir.getDefault().register(recyclerView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                startToRefresh();
            }
        });

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initTitlebar() {
        initTitle(title,getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initRefreshLayout() {
//        refreshLayout.startRefresh();

        RefreshHelper.initRefreshLayout(refreshLayout, false, new CustomRefreshListener() {
            @Override
            public void _onRefresh(TwinklingRefreshLayout refreshLayout) {
                startToRefresh();
            }

            @Override
            public void _onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });
    }

    private void startToRefresh() {
        Map<String,String> map = new HashMap<>();
        map.put("tagId",recommandTagId+"");

        mPresenter.refreshRecyclerView(map);
    }

    @Override
    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MultiTypeAdapter();
        adapter.register(OnlineLoan.class,new RecommandViewBinder());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshRecyclerView(List<Object> objects) {
        adapter.setItems(objects);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideRefreshLayout() {
        RefreshHelper.hideRefreshLayout(refreshLayout);
    }
}
