package com.fuzhuangfaqianla.xinhuahua.ui.product.bean;


/**
 * Created by Oubowu on 2016/7/21 17:51.
 * <p>
 * 实体类，可以将自己想要填充的数据包装进去，同时附带这个数据对应的类型
 */
public class PinnedHeaderEntity<T>{
    public static int TYPE_PINNED_HEADER = 0;
    public static int TYPE_INTRO = 1;
    public static int TYPE_LOAN_DETAIL = 2;
    public static int TYPE_APPLY = 3;
    public static int TYPE_PRODUCT_INFO = 4;

    private final int itemType;

    private T data;

    private String pinnedHeaderName;

    public PinnedHeaderEntity(T data, int itemType, String pinnedHeaderName) {
        this.data = data;
        this.itemType = itemType;
        this.pinnedHeaderName = pinnedHeaderName;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setPinnedHeaderName(String pinnedHeaderName) {
        this.pinnedHeaderName = pinnedHeaderName;
    }

    public T getData() {
        return data;
    }

    public String getPinnedHeaderName() {
        return pinnedHeaderName;
    }

    public int getItemType() {
        return itemType;
    }
}
