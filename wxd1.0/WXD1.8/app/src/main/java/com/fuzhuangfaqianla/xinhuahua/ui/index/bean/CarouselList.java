package com.fuzhuangfaqianla.xinhuahua.ui.index.bean;

import java.util.List;

/**
 * Created by xiongchang on 2017/9/21.
 */

public class CarouselList {
    List<Carousel> datas;

    public CarouselList(List<Carousel> datas) {
        this.datas = datas;
    }

    public List<Carousel> getDatas() {
        return datas;
    }

    public void setDatas(List<Carousel> datas) {
        this.datas = datas;
    }
}
