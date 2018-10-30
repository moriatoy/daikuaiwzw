package com.fuzhuangfaqianla.xinhuahua.ui.loan.bean;

/**
 * Created by xiongchang on 17/7/14.
 */

public class Sort {
    String type;
    String id;
    String name;
    boolean isSelectd;

    public Sort(String type, String id, String name, boolean isSelectd) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.isSelectd = isSelectd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelectd() {
        return isSelectd;
    }

    public void setSelectd(boolean selectd) {
        isSelectd = selectd;
    }
}
