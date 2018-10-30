package com.fuzhuangfaqianla.xinhuahua.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.commonlib.util.DialogUtil;
import com.commonlib.util.SPUtil;
import com.fuzhuangfaqianla.xinhuahua.ui.login.LoginActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;

/**
 * Created by xiongchang on 2017/9/27.
 */

public class LoginHelper {
    public static final String ISLOGIN = "islogin";
    public static final String USER = "user";

    /**
     * 判断是否登录
     */
    public static boolean isLogin(){
        String isLogin = SPUtil.getString(ISLOGIN);
        if(isLogin!=null && isLogin.equals("1") && LoginHelper.getUserInfo()!=null){
            return true;
        }else {
            return false;
        }
    }

    /**
     *  获取用户信息
     */
    public static User getUserInfo(){
        return SPUtil.getObjectFromShare(USER);
    }

    /**
     * 未登录跳转登录页
     */
    public static void unLoginGoToLoginActivity(Context context){
        if(isLogin()==false){
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    /**
     * 未登录弹窗跳登录页
     */
    public static void showLoginDialog(final Context context){
        if(isLogin()==false){
            DialogUtil.getAlertDialog(context, "提醒", "您还未登录，请先登录", "确定", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    OpenHelper.startActivity(context,LoginActivity.class);
                }
            }).show();
        }
    }
}
