package com.fuzhuangfaqianla.xinhuahua.ui.index;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.commonlib.util.MeasureUtil;
import com.commonlib.util.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppLazyBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.inter.CustomRefreshListener;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.CarouselList;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.RecommandTagList;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommendList;
import com.fuzhuangfaqianla.xinhuahua.ui.index.contract.IndexContract;
import com.fuzhuangfaqianla.xinhuahua.ui.index.holder.TodayRecommendViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.index.holder.CarouselViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.index.holder.RecommandTagViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.index.holder.OnlineLoanViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.index.presenter.IndexPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.RefreshHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;

public class IndexFragment extends AppLazyBaseFragment<IndexContract.Presenter> implements IndexContract.View {
    @BindView(R.id.index_fragment_refreshlayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.index_fragment_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.index_fragment_fl)
    FrameLayout flTitle;
    @BindView(R.id.index_fragment_fl_tv_title)
    TextView tvTitle;
    @BindView(R.id.index_fragment_fl_line)
    View line;
    MultiTypeAdapter adapter;
    int page = 1;

    public IndexFragment() {
        super(R.layout.fragment_index);
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","首页");
        ZhugeHelper.browseOther(getContext(),map);
    }

    @Override
    public void loadData() {
        startToRefresh();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        mPresenter = new IndexPresenterImpl(this);
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
    public void initTitlebar() {
        ImmersionBar.setTitleBar(getActivity(),flTitle);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if(newState==RecyclerView.SCROLL_STATE_SETTLING){
//                    //快速下拉
//                    flTitle.setBackgroundColor(getResources().getColor(R.color.white));
//                    tvTitle.setTextColor(getResources().getColor(R.color.color_33));
//                    line.setVisibility(View.VISIBLE);
//                    flTitle.setAlpha(1);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int scrollY = recyclerView.computeVerticalScrollOffset();
//
//                if(scrollY>0 && scrollY<100 && dy>0){
//                    flTitle.setBackgroundColor(getResources().getColor(R.color.white));
//                    tvTitle.setTextColor(getResources().getColor(R.color.color_33));
//                    line.setVisibility(View.VISIBLE);
//                    flTitle.setAlpha(0);
//                }else if(scrollY>0 && scrollY<100 && dy<0){
//                    flTitle.setBackgroundColor(Color.parseColor("#66000000"));
//                    tvTitle.setTextColor(getResources().getColor(R.color.white));
//                    line.setVisibility(View.INVISIBLE);
//                    flTitle.setAlpha(1);
//                }else if(scrollY>=100) {
//                    //改变透明度
//                    changeAlpha(flTitle,216,scrollY);
//                }
//            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int scrollY = recyclerView.computeVerticalScrollOffset();
                //改变透明度
                changeAlpha(flTitle,180,scrollY);
            }
        });

    }

    private void changeAlpha(View view, int judgeHeight, int scrollY) {
        //快速下拉会引起scollY<0
        /*if(scrollY<0){
            view.setAlpha(0);
            return;
        }*/
        int bannerHeight = MeasureUtil.dip2px(getActivity(),judgeHeight);
        float radio = Math.min(1, scrollY/(bannerHeight - view.getHeight()*1f));
        view.setAlpha(radio);
    }

    @Override
    public void initRefreshLayout() {
        RefreshHelper.initRefreshLayout(refreshLayout,true,new CustomRefreshListener() {
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
                mPresenter.loadMore(map);
            }
        });
    }

    private void startToRefresh() {
        page = 1;

        Map<String,String> map = new HashMap<>();
        map.put("isPage","1");
        map.put("page", page +"");
        mPresenter.refreshRecyclerView(map);
    }

    @Override
    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MultiTypeAdapter();

        adapter.register(CarouselList.class, new CarouselViewBinder());
        adapter.register(RecommandTagList.class, new RecommandTagViewBinder());
        adapter.register(TodayRecommendList.class, new TodayRecommendViewBinder());
        adapter.register(OnlineLoan.class, new OnlineLoanViewBinder());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshRecyclerView(List<Object> objects) {
        flTitle.setVisibility(View.VISIBLE);
        adapter.setItems(objects);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideRefreshLayout() {
        RefreshHelper.hideRefreshLayout(refreshLayout);
    }

    @Override
    public void hideLoadMoreLayout() {
        RefreshHelper.hideLoadMorLayout(refreshLayout);
    }

}
