package com.fuzhuangfaqianla.xinhuahua.ui.index.bean;

import java.util.List;

/**
 * Created by xiongchang on 2017/10/14.
 */

public class TodayRecommendList {
    List<TodayRecommend> datas;

    public TodayRecommendList(List<TodayRecommend> datas) {
        this.datas = datas;
    }

    public List<TodayRecommend> getDatas() {
        return datas;
    }

    public void setDatas(List<TodayRecommend> datas) {
        this.datas = datas;
    }
}
