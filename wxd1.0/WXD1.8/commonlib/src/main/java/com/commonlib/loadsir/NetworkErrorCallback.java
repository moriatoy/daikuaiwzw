package com.commonlib.loadsir;


import com.commonlib.R;
import com.kingja.loadsir.callback.Callback;



public class NetworkErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.inflate_network_error_view;
    }
}
