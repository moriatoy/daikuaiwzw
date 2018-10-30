package com.fuzhuangfaqianla.xinhuahua.ui.product.bean;

import java.util.List;

/**
 * Created by xiongchang on 2017/9/28.
 */
public class Apply {
    List<Object[]> datas;

    public Apply(List<Object[]> datas) {
        this.datas = datas;
    }

    public List<Object[]> getDatas() {
        return datas;
    }

    public void setDatas(List<Object[]> datas) {
        this.datas = datas;
    }
}