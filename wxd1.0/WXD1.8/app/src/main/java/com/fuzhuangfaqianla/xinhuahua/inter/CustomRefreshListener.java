package com.fuzhuangfaqianla.xinhuahua.inter;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * Created by xiongchang on 2017/9/21.
 */

public interface CustomRefreshListener {

    void _onRefresh(TwinklingRefreshLayout refreshLayout);
    void _onLoadMore(TwinklingRefreshLayout refreshLayout);
}
