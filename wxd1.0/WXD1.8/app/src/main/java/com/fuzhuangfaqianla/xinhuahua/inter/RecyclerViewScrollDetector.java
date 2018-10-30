package com.fuzhuangfaqianla.xinhuahua.inter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by xiongchang on 2017/9/28.
 */

public abstract class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {

    private int mScrollThreshold = 20;

    public abstract void onScrollUp(int scrollY);

    public abstract void onScrollDown(int scrollY);

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        int scrollY = recyclerView.computeVerticalScrollOffset();
        if (isSignificantDelta) {
            if (dy > 0) {
                onScrollUp(scrollY);
            } else {
                onScrollDown(scrollY);
            }
        }
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }

}
