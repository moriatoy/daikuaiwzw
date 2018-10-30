package com.fuzhuangfaqianla.xinhuahua.manager;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.http.SchedulerTransformer;
import com.commonlib.imageloader.ImageLoaderManager;
import com.commonlib.rxbus.RxBus;
import com.commonlib.rxbus.RxBusEvent;
import com.commonlib.util.MeasureUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ProductClickHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by xiongchang on 2017/8/12.
 */

public class DialogManager {

    public static void showStartBannerDialog(final Context context,List<StartBanner> startBanners){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.inflate_start_banner_dialog,null);

        final Dialog dialog = new Dialog(context, R.style.Theme_Light_Dialog);
        dialog.setContentView(dialogView);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = MeasureUtil.dip2px(context,250);
        lp.height = MeasureUtil.dip2px(context,400);
        window.setAttributes(lp);

        final TextView tvTitle = (TextView) dialogView.findViewById(R.id.inflate_start_banner_dialog_tv_title);
        final ImageView ivBanner = (ImageView) dialogView.findViewById(R.id.inflate_start_banner_dialog_iv_banner);

        //绑定数据
        final StartBanner startBanner = startBanners.get(0);
        tvTitle.setText(startBanner.getTitle());
        if(startBanner.getPicUrl() == null){
            ivBanner.setImageResource(R.drawable.logo);
        }else {
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(ivBanner,startBanner.getPicUrl()));
        }

        final Subscription subscription = RxBus.getDefault().toObservable(RxBusEvent.class)
                .compose(new SchedulerTransformer<RxBusEvent>())
                .subscribe(new Subscriber<RxBusEvent>() {
                    @Override
                    public void onCompleted() {
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RxBusEvent rxBusEvent) {
                        switch (rxBusEvent.getName()){
                            case "start_banner_login_successful":
                                ivBanner.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        User user = LoginHelper.getUserInfo();
                                        //统计点击量
                                        ProductClickHelper.addClickStartBannerCount(context,user.getUserId()+"");

                                        ProductClickHelper.startH5FromStartBanner(context, startBanner);

                                        dialog.dismiss();
                                    }
                                });
                                break;
                        }
                    }
                });

        ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductClickHelper.clickStartBanner(context,startBanner,subscription,dialog);
            }
        });

    }

}
