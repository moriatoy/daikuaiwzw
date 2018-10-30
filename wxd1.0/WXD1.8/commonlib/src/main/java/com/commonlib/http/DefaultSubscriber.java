package com.commonlib.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

import com.commonlib.mvp.BaseView;
import com.commonlib.rxbus.RxBus;
import com.commonlib.rxbus.RxBusEvent;
import com.commonlib.util.DialogUtil;
import com.commonlib.util.MyLogUtil;
import com.commonlib.util.ToastUtil;
import com.tapadoo.alerter.Alerter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by xiongchang on 17/6/27.
 */

public abstract class DefaultSubscriber<T> extends Subscriber<T> {
    private Context context;
    private BaseView baseView;
    private boolean showProgressDialog = false;
    private Dialog dialog;

    public DefaultSubscriber(Context context){
        this.context = context;
    }

    //此构造方法用于显示状态图
    public DefaultSubscriber(BaseView baseView){
        this.baseView = baseView;
        this.context = baseView._getContext();
    }

    public DefaultSubscriber(Context context, boolean showProgressDialog){
        this.context = context;
        this.showProgressDialog = showProgressDialog;
        dialog = DialogUtil.getProgressDialog(context,"加载中...");
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        hideProgressDialog();
        _onCompleted();
    }

    @Override
    public void onNext(T t) {
        if(baseView!=null && t instanceof List<?> && (t==null || ((List) t).size()==0)){//列表--recyclerview
            baseView.showEmptyView();
        }else if(baseView!=null && t instanceof List<?>){//不为空
            baseView.showSuccessfulView();
            _onNext(t);
        }else {
            _onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        hideProgressDialog();
        if (e instanceof SocketTimeoutException || e instanceof ConnectException || e instanceof UnknownHostException || e instanceof UnknownServiceException || e instanceof HttpException) {
//            showAlert("网络异常");
            ToastUtil.toastShortShow("网络异常");

            //注册baseview    显示网络异常的view
            if (baseView != null) {
                baseView.showNetworkErrorView();
            }
        } else if(e instanceof APIException){
            if(baseView!=null){
                baseView.showSuccessfulView();
            }

            //服务器状态错误
            int code = ((APIException)e).getCode();
            String message = e.getMessage();
            MyLogUtil.i("test", "APIException: "+ code + ErrorMessage.get(code));
            ToastUtil.toastShortShow(message);
            showAlert(message);

            if(code == 10005){//登录过期
                RxBus.getDefault().post(new RxBusEvent(700,"login_expired_from_default_subscriber"));
            }
        } else {
            if(baseView!=null){
                baseView.showErrorView();
            }
        }
        _onError(e);
    }

    private void showAlert(String message) {
        if(context instanceof Activity){
            Alerter.create((Activity) context)
                    .setText(message)
                    .enableSwipeToDismiss()
                    .setBackgroundColorInt(Color.parseColor("#ff2c55"))
                    .setDuration(2500)
                    .show();
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(Throwable e);

    protected abstract void _onCompleted();

    private void showProgressDialog() {
        if(showProgressDialog && dialog!=null){
            dialog.show();
        }
    }

    private void hideProgressDialog() {
        if(showProgressDialog && dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
