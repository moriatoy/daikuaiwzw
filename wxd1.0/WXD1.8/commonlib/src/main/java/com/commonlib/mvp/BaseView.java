
package com.commonlib.mvp;

import android.content.Context;

public interface BaseView {
    Context _getContext();
    void showNetworkErrorView();
    void showEmptyView();
    void showSuccessfulView();
    void showErrorView();
}
