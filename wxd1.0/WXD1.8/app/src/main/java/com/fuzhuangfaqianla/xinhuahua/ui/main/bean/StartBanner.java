package com.fuzhuangfaqianla.xinhuahua.ui.main.bean;

import java.io.Serializable;

/**
 * Created by xiongchang on 2017/8/12.
 */

public class StartBanner implements Serializable {
    private int id;
    private String title;
    private String picUrl;
    private String jumpUrl;
    private long createTime;
    private boolean login;

    public StartBanner(int id, String title, String picUrl, String jumpUrl, long createTime, boolean login) {
        this.id = id;
        this.title = title;
        this.picUrl = picUrl;
        this.jumpUrl = jumpUrl;
        this.createTime = createTime;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
