package com.commonlib.diff;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by xiongchang on 17/7/21.
 */

public class MTDiffCallBack extends DiffUtil.Callback {
    private List<?> oldList;
    private List<?> newList;

    public MTDiffCallBack(List<?> oldList, List<?> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0: oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0: newList.size();
    }

    //最好通过字段进行判断
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    //定向部分刷新
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
