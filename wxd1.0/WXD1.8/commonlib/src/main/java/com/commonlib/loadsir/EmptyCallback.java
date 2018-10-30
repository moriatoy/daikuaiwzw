package com.commonlib.loadsir;

import android.content.Context;
import android.view.View;

import com.commonlib.R;
import com.kingja.loadsir.callback.Callback;



public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.inflate_empty_view;
    }

    @Override
    protected boolean onRetry(Context context, View view) {
        return true;
    }
}
