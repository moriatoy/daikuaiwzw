package com.commonlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

import java.util.List;


public abstract class BaseExpandListAdapter<T,G> extends BaseExpandableListAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext = null;
    protected List<List<T>>childList=null;
    protected List<G>groupList=null;
    protected OnCustomListener listener;

    public BaseExpandListAdapter(Context context, List<List<T>>childList, List<G>groupList) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.childList=childList;
        this.groupList=groupList;
    }
    public boolean areAllItemsEnabled() {
        return false;
    }

    /*
     * 设置子节点对象，在事件处理时返回的对象，可存放一些数据
     */
    public Object getChild(int groupPosition, int childPosition) {
        return (childList == null) ? null : childList.get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /*
            * 返回当前分组的字节点个数
            */
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    /*
         * 返回分组对象，用于一些数据传递，在事件处理时可直接取得和分组相关的数据
         */
    public Object getGroup(int groupPosition) {
        return 0;
    }

    /*
     * 分组的个数
     */
    public int getGroupCount() {
        return groupList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /*
     * 判断分组是否为空，本示例中数据是固定的，所以不会为空，我们返回false
     * 如果数据来自数据库，网络时，可以把判断逻辑写到这个方法中，如果为空
     * 时返回true
     */
    public boolean isEmpty() {
        return false;
    }

    /*
     * 收缩列表时要处理的东西都放这儿
     */
    public void onGroupCollapsed(int groupPosition) {

    }

    /*
     * 展开列表时要处理的东西都放这儿
     */
    public void onGroupExpanded(int groupPosition) {

    }

    /*
     * Indicates whether the child and group IDs are stable across changes to
     * the underlying data.
     */
    public boolean hasStableIds() {
        return false;
    }

    /*
     * Whether the child at the specified position is selectable.
     */
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 设置适配器上，某个控件的监听事件
     *
     * @param listener
     */
    public void setOnCustomListener(OnCustomListener listener) {
        this.listener = listener;
    }
}
