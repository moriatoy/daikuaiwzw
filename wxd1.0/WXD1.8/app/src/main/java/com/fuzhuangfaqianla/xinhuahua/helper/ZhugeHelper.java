package com.fuzhuangfaqianla.xinhuahua.helper;

import android.content.Context;

import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by xiongchang on 2017/10/12.
 */

public class ZhugeHelper {
    //from
    public static String banner_from = "弹窗";
    public static String recomm_from = "为你推荐";
    public static String today_recomm_from = "今日推荐";
    public static String hot_loan_from = "热门贷款";
    public static String loan_books_from = "贷款大全";
    public static String already_apply_from = "浏览足迹已申请";
    public static String already_browse_from = "浏览足迹已浏览";
    public static String carousel_from = "轮播图";
    public static String product_detail_from = "产品详情";

    //点击事件
    public static String push_from = "推送";
    public static String homepage = "首页";
    public static String loan = "贷款";
    public static String mine = "我的";
    public static String login_with_password = "登录";
    public static String login_page_register = "注册";
    public static String password_retrieval = "找回密码";
    public static String login_page_get_identifying_code = "获取验证码";
    public static String login_with_identifying_code = "登录";
    public static String register_page_get_identifying_code = "获取验证码";
    public static String register_page_register = "注册";
    public static String password_retrieval_get_identifying_code = "获取验证码";
    public static String password_retrieval_submit = "提交";
    public static String take_the_money_immediately = "马上拿钱";
    public static String essential_information = "基本信息";
    public static String identify_information = "身份信息";
    public static String asset_information = "资产信息";
    public static String personal_info_save = "保存";
    public static String applied = "已申请";
    public static String visited = "已浏览";
    public static String logout = "退出登录";
    public static String cancel_clear_cache = "取消";
    public static String confirm_clear_cache = "确定";
    public static String alter_password_get_identifying_code = "获取验证码";
    public static String alter_password_submit = "提交";

    //事件名称
    public static String browse_product_details_event_name = "product_details";
    public static String browse_product_apply_event_name = "product_apply";
    public static String browse_other_event_name = "other";
    public static String click_recommend = "click_recommend";
    public static String click_search = "click_search";
    public static String click_button = "click_button";


    /**
     * 页面浏览
     */
    public static void zhugeEvent(Context context, String eventName, Map<String,String> map){
        //定义与事件相关的属性信息
        JSONObject eventObject = new JSONObject();
        if(map!=null){
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                try {
                    eventObject.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //记录事件
        ZhugeSDK.getInstance().track(context, eventName,  eventObject);
    }

    /**
     * 浏览产品详情
     */
    public static void browseProductDetail(Context context,Map<String,String> map){
        zhugeEvent(context,browse_product_details_event_name,map);
    }

    /**
     * 浏览产品申请页
     */
    public static void browseProductApply(Context context,Map<String,String> map){
        zhugeEvent(context,browse_product_apply_event_name,map);
    }

    /**
     * 其他页面
     */
    public static void browseOther(Context context,Map<String,String> map){
//        zhugeEvent(context,browse_other_event_name,map);
    }

    /**
     * 点击为你推荐
     */
    public static void clickRecommend(Context context, Map<String,String> map){
        zhugeEvent(context, click_recommend,map);
    }

    /**
     * 点击查询条件
     */
    public static void clickSearch(Context context, Map<String,String> map){
        zhugeEvent(context,click_search,map);
    }

    /**
     * 点击按钮
     */
    public static void clickButton(Context context,Map<String,String> map){
//        zhugeEvent(context,click_button,map);
    }

    /**
     * 跟踪用户
     */
    public static void trackUser(Context context){
        String identityId;
        if(LoginHelper.isLogin()){
            User user = LoginHelper.getUserInfo();
            identityId = user.getUserId()+"";
            JSONObject personObject = new JSONObject();

        }
    }

}
