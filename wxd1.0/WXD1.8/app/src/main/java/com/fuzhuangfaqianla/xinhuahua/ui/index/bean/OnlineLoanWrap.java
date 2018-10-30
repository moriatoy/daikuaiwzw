package com.fuzhuangfaqianla.xinhuahua.ui.index.bean;

import java.util.List;

/**
 * Created by xiongchang on 2017/10/19.
 */

public class OnlineLoanWrap {
    int pageCount;
    List<OnlineLoan> data;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<OnlineLoan> getData() {
        return data;
    }

    public void setData(List<OnlineLoan> data) {
        this.data = data;
    }
}
