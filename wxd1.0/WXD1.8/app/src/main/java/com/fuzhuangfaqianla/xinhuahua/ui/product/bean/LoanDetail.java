package com.fuzhuangfaqianla.xinhuahua.ui.product.bean;

/**
 * Created by xiongchang on 2017/9/28.
 */
public class LoanDetail {
    String rateType;
    String rate;

    public LoanDetail(String rateType, String rate) {
        this.rateType = rateType;
        this.rate = rate;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}