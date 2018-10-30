package com.fuzhuangfaqianla.xinhuahua.ui.main.bean;

/**
 * Created by xiongchang on 2017/9/7.
 */

public class UpdateApk {
    int id;
    String channel;
    String packageName;
    String versionCode;
    String versionName;
    String downloadUrl;
    Boolean forceUpdate;
    Boolean autoUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public Boolean getAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }
}
