package com.commonlib.loadsir;


import com.commonlib.R;
import com.kingja.loadsir.callback.Callback;


public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.inflate_error_view;
    }
}
