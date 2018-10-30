package com.fuzhuangfaqianla.xinhuahua.ui.login.bean;

import java.io.Serializable;

/**
 * Created by dasiy on 17/4/20.
 */

public class User implements Serializable{
    private int userId;
    private Object auth;
    private String userName;
    private String idNum;
    private String realName;
    private String phoneNum;
    private String avatar;
    private Long createTime;
    private UserInfo userInfo;

    public static class UserInfo implements Serializable{
        private String gender;
        private Long timeOfBirth;
        private String maritalStatus;
        private String culturalLevel;
        private String ocp;
        private String monthlyIncome;
        private String incomeForm;
        private String localSocSec;
        private String localProvidentFund;
        private String property;
        private String car;
        private String psnlIns;
        private String creditRecord;
        private String creditCardLimit;
        private Boolean loanSuccess;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Long getTimeOfBirth() {
            return timeOfBirth;
        }

        public void setTimeOfBirth(Long timeOfBirth) {
            this.timeOfBirth = timeOfBirth;
        }

        public String getPsnlIns() {
            return psnlIns;
        }

        public void setPsnlIns(String psnlIns) {
            this.psnlIns = psnlIns;
        }

        public String getCreditRecord() {
            return creditRecord;
        }

        public void setCreditRecord(String creditRecord) {
            this.creditRecord = creditRecord;
        }

        public String getCreditCardLimit() {
            return creditCardLimit;
        }

        public void setCreditCardLimit(String creditCardLimit) {
            this.creditCardLimit = creditCardLimit;
        }

        public Boolean getLoanSuccess() {
            return loanSuccess;
        }

        public void setLoanSuccess(Boolean loanSuccess) {
            this.loanSuccess = loanSuccess;
        }



        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getCulturalLevel() {
            return culturalLevel;
        }

        public void setCulturalLevel(String culturalLevel) {
            this.culturalLevel = culturalLevel;
        }

        public String getOcp() {
            return ocp;
        }

        public void setOcp(String ocp) {
            this.ocp = ocp;
        }

        public String getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(String monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public String getIncomeForm() {
            return incomeForm;
        }

        public void setIncomeForm(String incomeForm) {
            this.incomeForm = incomeForm;
        }

        public String getLocalSocSec() {
            return localSocSec;
        }

        public void setLocalSocSec(String localSocSec) {
            this.localSocSec = localSocSec;
        }

        public String getLocalProvidentFund() {
            return localProvidentFund;
        }

        public void setLocalProvidentFund(String localProvidentFund) {
            this.localProvidentFund = localProvidentFund;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserInfo userInfo = (UserInfo) o;

            if (gender != null ? !gender.equals(userInfo.gender) : userInfo.gender != null)
                return false;
            if (timeOfBirth != null ? !timeOfBirth.equals(userInfo.timeOfBirth) : userInfo.timeOfBirth != null)
                return false;
            if (maritalStatus != null ? !maritalStatus.equals(userInfo.maritalStatus) : userInfo.maritalStatus != null)
                return false;
            if (culturalLevel != null ? !culturalLevel.equals(userInfo.culturalLevel) : userInfo.culturalLevel != null)
                return false;
            if (ocp != null ? !ocp.equals(userInfo.ocp) : userInfo.ocp != null) return false;
            if (monthlyIncome != null ? !monthlyIncome.equals(userInfo.monthlyIncome) : userInfo.monthlyIncome != null)
                return false;
            if (incomeForm != null ? !incomeForm.equals(userInfo.incomeForm) : userInfo.incomeForm != null)
                return false;
            if (localSocSec != null ? !localSocSec.equals(userInfo.localSocSec) : userInfo.localSocSec != null)
                return false;
            if (localProvidentFund != null ? !localProvidentFund.equals(userInfo.localProvidentFund) : userInfo.localProvidentFund != null)
                return false;
            if (property != null ? !property.equals(userInfo.property) : userInfo.property != null)
                return false;
            if (car != null ? !car.equals(userInfo.car) : userInfo.car != null) return false;
            if (psnlIns != null ? !psnlIns.equals(userInfo.psnlIns) : userInfo.psnlIns != null)
                return false;
            if (creditRecord != null ? !creditRecord.equals(userInfo.creditRecord) : userInfo.creditRecord != null)
                return false;
            if (creditCardLimit != null ? !creditCardLimit.equals(userInfo.creditCardLimit) : userInfo.creditCardLimit != null)
                return false;
            return loanSuccess != null ? loanSuccess.equals(userInfo.loanSuccess) : userInfo.loanSuccess == null;

        }

        @Override
        public int hashCode() {
            int result = gender != null ? gender.hashCode() : 0;
            result = 31 * result + (timeOfBirth != null ? timeOfBirth.hashCode() : 0);
            result = 31 * result + (maritalStatus != null ? maritalStatus.hashCode() : 0);
            result = 31 * result + (culturalLevel != null ? culturalLevel.hashCode() : 0);
            result = 31 * result + (ocp != null ? ocp.hashCode() : 0);
            result = 31 * result + (monthlyIncome != null ? monthlyIncome.hashCode() : 0);
            result = 31 * result + (incomeForm != null ? incomeForm.hashCode() : 0);
            result = 31 * result + (localSocSec != null ? localSocSec.hashCode() : 0);
            result = 31 * result + (localProvidentFund != null ? localProvidentFund.hashCode() : 0);
            result = 31 * result + (property != null ? property.hashCode() : 0);
            result = 31 * result + (car != null ? car.hashCode() : 0);
            result = 31 * result + (psnlIns != null ? psnlIns.hashCode() : 0);
            result = 31 * result + (creditRecord != null ? creditRecord.hashCode() : 0);
            result = 31 * result + (creditCardLimit != null ? creditCardLimit.hashCode() : 0);
            result = 31 * result + (loanSuccess != null ? loanSuccess.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "maritalStatus='" + maritalStatus + '\'' +
                    ", culturalLevel='" + culturalLevel + '\'' +
                    ", ocp='" + ocp + '\'' +
                    ", monthlyIncome='" + monthlyIncome + '\'' +
                    ", incomeForm='" + incomeForm + '\'' +
                    ", localSocSec='" + localSocSec + '\'' +
                    ", localProvidentFund='" + localProvidentFund + '\'' +
                    ", property='" + property + '\'' +
                    ", car='" + car + '\'' +
                    ", psnlIns='" + psnlIns + '\'' +
                    ", creditRecord='" + creditRecord + '\'' +
                    ", creditCardLimit='" + creditCardLimit + '\'' +
                    ", loanSuccess=" + loanSuccess +
                    '}';
        }
    }

    private String address;
    private boolean disabled;
    private boolean zhimaCredit;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean getZhimaCredit() {
        return zhimaCredit;
    }

    public void setZhimaCredit(boolean zhimaCredit) {
        this.zhimaCredit = zhimaCredit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (disabled != user.disabled) return false;
        if (zhimaCredit != user.zhimaCredit) return false;
        if (auth != null ? !auth.equals(user.auth) : user.auth != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null)
            return false;
        if (idNum != null ? !idNum.equals(user.idNum) : user.idNum != null) return false;
        if (realName != null ? !realName.equals(user.realName) : user.realName != null)
            return false;
        if (phoneNum != null ? !phoneNum.equals(user.phoneNum) : user.phoneNum != null)
            return false;
        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        if (createTime != null ? !createTime.equals(user.createTime) : user.createTime != null)
            return false;
        if (userInfo != null ? !userInfo.equals(user.userInfo) : user.userInfo != null)
            return false;
        return address != null ? address.equals(user.address) : user.address == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (auth != null ? auth.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (idNum != null ? idNum.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (phoneNum != null ? phoneNum.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (disabled ? 1 : 0);
        result = 31 * result + (zhimaCredit ? 1 : 0);
        return result;
    }
}
