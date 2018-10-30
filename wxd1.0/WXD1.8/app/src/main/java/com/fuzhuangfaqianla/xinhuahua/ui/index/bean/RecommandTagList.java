package com.fuzhuangfaqianla.xinhuahua.ui.index.bean;

import java.util.List;

/**
 * Created by xiongchang on 2017/9/21.
 */

public class RecommandTagList {
    List<RecommandTag> datas;

    public RecommandTagList(List<RecommandTag> datas) {
        this.datas = datas;
    }

    public List<RecommandTag> getDatas() {
        return datas;
    }

    public void setDatas(List<RecommandTag> datas) {
        this.datas = datas;
    }
}
