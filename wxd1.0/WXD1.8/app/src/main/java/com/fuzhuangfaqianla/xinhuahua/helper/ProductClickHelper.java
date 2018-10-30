package com.fuzhuangfaqianla.xinhuahua.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.commonlib.util.MyLogUtil;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.Carousel;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommend;
import com.fuzhuangfaqianla.xinhuahua.ui.login.LoginActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;
import com.fuzhuangfaqianla.xinhuahua.ui.product.ProductActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.product.ProductH5Activity;
import com.fuzhuangfaqianla.xinhuahua.ui.product.StartBannerActivity;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/29.
 */

public class ProductClickHelper {

    /**
     * 点击轮播图的逻辑
     */
    public static void clickCarousel(Context context, Carousel carousel){
        if(LoginHelper.isLogin()){
            //增加点击量
            addClickCarouselCount(context, carousel);

            Intent intent = new Intent(context, ProductH5Activity.class);
            intent.putExtra("from",ZhugeHelper.carousel_from);
            intent.putExtra("url",carousel.getRedirectUrl());
            intent.putExtra("title",carousel.getCarouselPicNote());
            OpenHelper.startActivity(context,intent);
        }else {
            LoginHelper.unLoginGoToLoginActivity(context);
        }
    }

    /**
     * 增加轮播图点击量
     */
    private static void addClickCarouselCount(Context context, Carousel carousel) {
        Map<String,String> map = new HashMap<>();
        map.put("carouselId",String.valueOf(carousel.getCarouselPicId()));
        map.put("userId",LoginHelper.getUserInfo().getUserId()+"");

        HttpManager.api.clickOnCarouselPic(map)
                .compose(RxHelper.<String>handleResult())
                .subscribe(new DefaultSubscriber<String>(context) {
                    @Override
                    protected void _onNext(String s) {
                        MyLogUtil.i("test","clickOnCarouselPic successful");
                    }

                    @Override
                    protected void _onError(Throwable e) {
                        MyLogUtil.i("test","clickOnCarouselPic failed");
                    }

                    @Override
                    protected void _onCompleted() {

                    }
                });
    }

    /**
     * 点击今日推荐
     */
    public static void clickTodayRecommend(final Context context, final String from, TodayRecommend data){
        if(LoginHelper.isLogin()){
            //TODO  属于贷款产品点击量
            addClickProductCount(context,data.getLoanProduct().getProductId());

            //传递id下个界面获取详情
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("from",from);
            intent.putExtra("productId",data.getLoanProduct().getProductId()+"");
            OpenHelper.startActivity(context,intent);
        }else {
            LoginHelper.unLoginGoToLoginActivity(context);
        }
    }

    /**
     * 点击贷款产品
     */
    public static void clickOnlineProduct(Context context, String from, OnlineLoan data){
        if(LoginHelper.isLogin()){
            addClickProductCount(context,data.getProductId());

            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("from",from);
//            intent.putExtra("data",data);
            intent.putExtra("productId",data.getProductId()+"");
            OpenHelper.startActivity(context,intent);
        }else {
            LoginHelper.unLoginGoToLoginActivity(context);
        }
    }

    /**
     * 增加贷款产品点击量
     */
    public static void addClickProductCount(Context context, int productId) {
        Map<String,String> map = new HashMap<>();
        map.put("productId",productId+"");
        map.put("userId",LoginHelper.getUserInfo().getUserId()+"");

        HttpManager.api.clickOnLoanProduct(map)
                .compose(RxHelper.<String>handleResult())
                .subscribe(new DefaultSubscriber<String>(context) {
            @Override
            protected void _onNext(String s) {
                MyLogUtil.i("test","clickOnLoanProduct successful");
            }

            @Override
            protected void _onError(Throwable e) {
                MyLogUtil.i("test","clickOnLoanProduct failed");
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    /**
     * 点击开屏广告
     */
    public static void clickStartBanner(Context context, StartBanner startBanner, Subscription subscription, Dialog dialog){
        if(startBanner.isLogin()){
            if(LoginHelper.isLogin()){
                User user = LoginHelper.getUserInfo();
                //统计点击量
                addClickStartBannerCount(context,user.getUserId()+"");

                startH5FromStartBanner(context, startBanner);

                subscription.unsubscribe();
                dialog.dismiss();
            }else {
                OpenHelper.startActivity(context, LoginActivity.class);
            }
        }else {
            //统计点击量
            addClickStartBannerCount(context,null);

            startH5FromStartBanner(context, startBanner);

            subscription.unsubscribe();
            dialog.dismiss();
        }
    }

    public static void startH5FromStartBanner(Context context, StartBanner startBanner) {
//        Intent intent = new Intent(context, StartBannerActivity.class);
//        String jumpUrl = startBanner.getJumpUrl();
//        if(jumpUrl == null){
//            jumpUrl = "";
//        }
//        intent.putExtra("title",startBanner.getTitle());
//        intent.putExtra("url",jumpUrl);
//        OpenHelper.startActivity(context,intent);

        //ZhugeIO
        Map<String,String> map = new HashMap<>();
        map.put("pagename","弹窗");
        map.put("name", startBanner.getTitle());
        ZhugeHelper.clickButton(context,map);

        Intent intent = new Intent(context,StartBannerActivity.class);
        intent.putExtra("data",startBanner);
        OpenHelper.startActivity(context,intent);
    }

    /**
     * 增加开屏广告点击量
     */
    public static void addClickStartBannerCount(Context context,String userId){
        Map<String,String> map = new HashMap<>();
        if(userId!=null){
            map.put("userId",userId);
        }

        HttpManager.api.clickOnOpenAd(map).compose(RxHelper.<String>handleResult())
                .subscribe(new DefaultSubscriber<String>(context) {
            @Override
            protected void _onNext(String s) {
                MyLogUtil.i("test","clickOnOpenAd");
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    /**
     * 点击已浏览和已申请
     */
    public static void clickBrowse(final Context context, int position, double productId){
        String from = null;
        if(position==0){
            from = ZhugeHelper.already_apply_from;
        }else if(position==1){
            from = ZhugeHelper.already_browse_from;
        }

        //TODO  属于贷款产品点击量
        addClickProductCount(context,(int)productId);

        //传递id下个界面获取详情
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("from",from);
        intent.putExtra("productId",((int)productId)+"");
        OpenHelper.startActivity(context,intent);
    }

    /**
     * 增加 发现 和 我要借款 的点击量
     */
    public static void addBottomButtonClick(Context context, final int code){
        Map<String,String> map = new HashMap<>();
        map.put("code",code + "");
        HttpManager.api.addButtonChecks(map)
                .compose(RxHelper.handleResult())
                .subscribe(new DefaultSubscriber<Object>(context) {
            @Override
            protected void _onNext(Object o) {
                MyLogUtil.i("test","add " + code + " click successful");
            }

            @Override
            protected void _onError(Throwable e) {
                MyLogUtil.i("test","add " + code + " click failed");
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }
}
