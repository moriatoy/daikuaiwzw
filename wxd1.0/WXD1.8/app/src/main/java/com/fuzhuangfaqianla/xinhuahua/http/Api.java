package com.fuzhuangfaqianla.xinhuahua.http;

import com.commonlib.http.BaseModel;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.Carousel;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoanWrap;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.RecommandTag;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.TodayRecommend;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.BottomInfo;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.Message;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.StartBanner;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.UpdateApk;
import com.fuzhuangfaqianla.xinhuahua.ui.me.bean.UploadImage;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by xiongchang on 2017/9/21.
 */

public interface Api {

    /**
     * 密码登录
     */
    @POST("loginByPassword")
    Observable<BaseModel<User>> loginByPassword(@QueryMap Map<String,String> map);

    /**
     * 获取验证码
     */
    @POST("sendCheckCode")
    Observable<BaseModel<String>> getCheckCode(@QueryMap Map<String,String> map);

    /**
     * 验证码登录    注册
     */
    @POST("userLogin")
    Observable<BaseModel<User>> loginByDynamic(@QueryMap Map<String,String> map);

    /**
     * 注册
     */
    @POST("signUpByPassword")
    Observable<BaseModel<Object>> regist(@QueryMap Map<String, String> map);

    /**
     * 退出登录
     */
    @POST("logout")
    Observable<BaseModel<Object>> logout();

    /**
     * 修改密码
     */
    @POST("signUpByPassword")
    Observable<BaseModel<Object>> modifyPassword(@QueryMap Map<String,String> map);

    /**
     * 设置deviceToken
     */
    @POST("setDeviceToken")
    Observable<BaseModel<String>> setDeviceToken(@QueryMap Map<String,String> map);

    /**
     * 上传图片
     */
    @Multipart
    @POST("uploadPic")
    Observable<UploadImage> uploadpic(@Part MultipartBody.Part file);

    /**
     * 修改头像
     */
    @POST("setAvatar")
    Observable<BaseModel<String>> modifyAvatar(@QueryMap Map<String, String> map);

    /**
     * 获取轮播图
     */
    @POST("getCarouselPics")
    Observable<BaseModel<List<Carousel>>> getCarouselPic();

    /**
     * 获取推荐tag
     */
    @POST("getNotNullRecommendTags")
    Observable<BaseModel<List<RecommandTag>>> getRecommandTag();

    /**
     * 获取小程序
     */
//    @POST("getApplets")
//    Observable<BaseModel<Applet>> getApplets();

    /**
     * 获取贷款产品
     */
    @POST("getOnlineLoanProduct")
    Observable<BaseModel<OnlineLoanWrap>> getOnlineLoanList(@QueryMap Map<String,String> map);

    /**
     * 获取推荐的贷款产品
     */
    @POST("getOnlineLoanProductsByRecommendTag")
    Observable<BaseModel<List<OnlineLoan>>> getLoanProductsByRecommendTag(@QueryMap Map<String,String> map);

    /**
     * 获取系统相关信息
     */
    @POST("getSystemInfoByKey")
    Observable<BaseModel<String>> getSystemInfoByKey(@QueryMap Map<String,String> map);

    /**
     * 反馈信息
     */
    @POST("userFeedback")
    Observable<BaseModel<String>> setUserFeedBack(@QueryMap Map<String,String> map);

    /**
     * 获取已申请的产品
     */
    @POST("getApplyLoan")
    Observable<BaseModel<Map<String,Object>>>getApplyLoan(@QueryMap Map<String,String> map);

    /**
     * 获取已浏览的产品
     */
    @POST("getVisitedLoan")
    Observable<BaseModel<Map<String,Object>>> getBrowsedLoan(@QueryMap Map<String,String> map);

    /**
     * 修改昵称
     */
    @POST("setUserName")
    Observable<BaseModel<String>> modifyNickname(@QueryMap Map<String, String> map);

    /**
     * 增加轮播图点击量
     */
    @POST("clickOnCarouselPic")
    Observable<BaseModel<String>> clickOnCarouselPic(@QueryMap Map<String,String> map);

    /**
     * 增加小程序点击量
     */
    @POST("clickOnApplet")
    Observable<BaseModel<String>> clickOnApplet(@QueryMap Map<String,String> map);

    /**
     * 增加小程序浏览量
     */
    @POST("hitApplet")
    Observable<BaseModel<String>> setClickAppletCount(@QueryMap Map<String,String> map);

    /**
     * 点击贷款产品
     */
    @POST("clickOnLoanProduct")
    Observable<BaseModel<String>> clickOnLoanProduct(@QueryMap Map<String,String> map);

    /**
     * 增加贷款产品的浏览量
     */
    @POST("visitLoan")
    Observable<BaseModel<String>> addLoanProductBrowseCount(@QueryMap Map<String,String> map);

    /**
     * 申请贷款
     */
    @POST("applyLoan")
    Observable<BaseModel<String>> setApplyLoan(@QueryMap Map<String,String> map);

    /**
     * 浏览贷款
     */
    @POST("visitLoan")
    Observable<BaseModel<String>> setVisitLaon(@QueryMap Map<String,String> map);

    /**
     * 获取相应分类下的信息
     */
    @POST("getTagsByType")
    Observable<BaseModel<List<String[]>>> getTagsByType(@QueryMap Map<String,String> map);

    /**
     * 获取筛选结果
     */
    @POST("selectLoanProductByTag")
    Observable<BaseModel<OnlineLoanWrap>> selectLoanProductByTag(@QueryMap Map<String,String> map);

    /**
     * 设置姓名和身份证号码
     */
    @POST("verfRealName")
    Observable<BaseModel<String>> verfRealName(@QueryMap Map<String, String> map);

    /**
     * 修改用户信息
     */
    @POST("setUserInfo")
    Observable<BaseModel<String>> setUserInfo(@QueryMap Map<String,Object> map);

    /**
     * 获取公告详情
     */
    @POST("getNoticeDetail")
    Observable<BaseModel<Message>> getNoticeDetail(@QueryMap Map<String,String> map);

    /**
     * 通过id获取贷款产品的详情
     */
    @POST("getLoanProductById")
    Observable<BaseModel<OnlineLoan>> getLoanProductDetail(@QueryMap Map<String,String> map);

    /**
     * 获取开屏广告
     */
    @POST("getOpenScreenAd")
    Observable<BaseModel<List<StartBanner>>> getStartBanner();

    /**
     * 点击开屏广告
     */
    @POST("clickOnOpenAd")
    Observable<BaseModel<String>> clickOnOpenAd(@QueryMap Map<String,String> map);

    /**
     * app自动更新
     */
    @POST("getApkAddress")
    Observable<BaseModel<List<UpdateApk>>> getApkAddress(@QueryMap Map<String,String> map);

    /**
     * 今日推荐
     */
    @POST("getAdSpace")
    Observable<BaseModel<List<TodayRecommend>>> getTodayRecommend();

    /**
     * 获取bottom的title
     */
    @POST("getWXDMenuButton")
    Observable<BaseModel<List<BottomInfo>>> getWXDMenuButton();

    /**
     * 统计发现和我要借款的点击量
     */
    @POST("addButtonChecks")
    Observable<BaseModel<Object>> addButtonChecks(@QueryMap Map<String,String> map);
}
