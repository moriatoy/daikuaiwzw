package com.fuzhuangfaqianla.xinhuahua.helper;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.inter.CustomRefreshListener;


/**
 * Created by xiongchang on 2017/9/21.
 */

public class RefreshHelper {

    public static void initRefreshLayout(TwinklingRefreshLayout refreshLayout, boolean loadMore, final CustomRefreshListener refreshListener) {
        //下拉刷新头
        ProgressLayout header = new ProgressLayout(refreshLayout.getContext());
        header.setColorSchemeResources(R.color.colorMain);
        header.setProgressBackgroundColorSchemeColor(refreshLayout.getContext().getResources().getColor(R.color.white));
        //加载更多头
        LoadingView loadingView = new LoadingView(refreshLayout.getContext());

        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setEnableOverScroll(false);
        refreshLayout.setHeaderHeight(140);
        refreshLayout.setMaxHeadHeight(200);
        refreshLayout.setOverScrollHeight(140);
        refreshLayout.setEnableLoadmore(loadMore);
        refreshLayout.setBottomView(loadingView);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                refreshListener._onRefresh(refreshLayout);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                refreshListener._onLoadMore(refreshLayout);
            }
        });
    }

    public static void hideRefreshLayout(TwinklingRefreshLayout refreshLayout){
        if(refreshLayout==null){
            return;
        }
        refreshLayout.finishRefreshing();
    }

    public static void hideLoadMorLayout(TwinklingRefreshLayout refreshLayout){
        if(refreshLayout==null){
            return;
        }
        refreshLayout.finishLoadmore();
    }
}
