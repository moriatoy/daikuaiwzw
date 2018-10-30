package com.fuzhuangfaqianla.xinhuahua.ui.index.bean;

/**
 * Created by xiongchang on 2017/8/24.
 */

public class RecommandTag {
    int recommendTagId;
    String picUrl;
    String title;
    String intro;
    long createTime;
    String priority;

    public int getRecommendTagId() {
        return recommendTagId;
    }

    public void setRecommendTagId(int recommendTagId) {
        this.recommendTagId = recommendTagId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
