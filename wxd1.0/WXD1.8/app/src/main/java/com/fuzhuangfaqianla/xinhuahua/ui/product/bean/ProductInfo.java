package com.fuzhuangfaqianla.xinhuahua.ui.product.bean;

/**
 * Created by xiongchang on 2017/10/18.
 */
public class ProductInfo {
    String iconUrl;
    String loanRange;
    String paymentMethod;
    int rcmIndex;
    String rateType;
    String rate;
    String timeLimit;
    int applicants;

    public ProductInfo(String iconUrl, String loanRange, String paymentMethod, int rcmIndex, String rateType, String rate, String timeLimit, int applicants) {
        this.iconUrl = iconUrl;
        this.loanRange = loanRange;
        this.paymentMethod = paymentMethod;
        this.rcmIndex = rcmIndex;
        this.rateType = rateType;
        this.rate = rate;
        this.timeLimit = timeLimit;
        this.applicants = applicants;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLoanRange() {
        return loanRange;
    }

    public void setLoanRange(String loanRange) {
        this.loanRange = loanRange;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getRcmIndex() {
        return rcmIndex;
    }

    public void setRcmIndex(int rcmIndex) {
        this.rcmIndex = rcmIndex;
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

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getApplicants() {
        return applicants;
    }

    public void setApplicants(int applicants) {
        this.applicants = applicants;
    }
}