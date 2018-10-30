package com.fuzhuangfaqianla.xinhuahua.ui.index.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiongchang on 2017/9/21.
 */
public class OnlineLoan implements Serializable {
    int productId;
    int type;
    String productName;
    String iconUrl;
    String intro;
    int applicants;
    String rateType;
    String rate;
    String redirectUrl;
    int rcmdIndex;
    String loanRange;
    String paymentMethod;
    String requirements;
    String requiredInfo;
    String timeLimit;
    String details;
    String companyPicUrl;
    String companyIntro;
    boolean online;
    String productUrl;

    List<Object[]> applyProcess;

    public List<Object[]> getApplyProcess() {
        return applyProcess;
    }

    public void setApplyProcess(List<Object[]> applyProcess) {
        this.applyProcess = applyProcess;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getApplicants() {
        return applicants;
    }

    public void setApplicants(int applicants) {
        this.applicants = applicants;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getRcmdIndex() {
        return rcmdIndex;
    }

    public void setRcmdIndex(int rcmdIndex) {
        this.rcmdIndex = rcmdIndex;
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

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getRequiredInfo() {
        return requiredInfo;
    }

    public void setRequiredInfo(String requiredInfo) {
        this.requiredInfo = requiredInfo;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCompanyPicUrl() {
        return companyPicUrl;
    }

    public void setCompanyPicUrl(String companyPicUrl) {
        this.companyPicUrl = companyPicUrl;
    }

    public String getCompanyIntro() {
        return companyIntro;
    }

    public void setCompanyIntro(String companyIntro) {
        this.companyIntro = companyIntro;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}