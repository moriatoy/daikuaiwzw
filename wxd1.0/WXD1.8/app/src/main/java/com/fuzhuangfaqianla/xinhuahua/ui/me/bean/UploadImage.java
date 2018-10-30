package com.fuzhuangfaqianla.xinhuahua.ui.me.bean;

/**
 * Created by xiongchang on 17/6/21.
 */

public class UploadImage {
    int state;
    String url;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UploadImage{" +
                "state=" + state +
                ", url='" + url + '\'' +
                '}';
    }
}
