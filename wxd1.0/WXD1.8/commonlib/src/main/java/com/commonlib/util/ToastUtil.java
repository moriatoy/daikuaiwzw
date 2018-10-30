package com.commonlib.util;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.commonlib.base.BaseApplication;


/**
 * 弹出框工具类
 */
public class ToastUtil {

    /**
     * @param text 提示文字
     */
    public static void toastShortShow(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            toastShow(BaseApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        }
    }

    /**
     * @param textResId 提示文字所在的资源id
     */
    public static void toastShortShow(int textResId) {
        toastShow(BaseApplication.getInstance().getApplicationContext(), textResId, Toast.LENGTH_SHORT);
    }

    /**
     * @param context 内容器实体
     * @param text    提示文字内容
     */
    public static void toastShortShow(Context context, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            toastShow(context, text, Toast.LENGTH_SHORT);
        }
    }

    /**
     * @param context   内容器实体
     * @param textResId 提示文字所在资源id
     */
    public static void toastShortShow(Context context, int textResId) {
        toastShow(context, textResId, Toast.LENGTH_SHORT);
    }

    /**
     * @param context   内容器实体
     * @param textResId 提示文字所在资源id
     * @param duration  提示时间
     */
    public static void toastShow(Context context, int textResId, int duration) {
        String text = context.getString(textResId);
        toastShow(context, text, duration);
    }

    /**
     * @param context  内容器实体
     * @param msg      提示消息内容
     * @param duration 提示时间
     */
    public static void toastShow(Context context, CharSequence msg, int duration) {
        try {
            if (isNotificationEnabled(context)) {
                Toast.makeText(context, msg, duration).show();
            } else {
                DialogUtil.getAlertDialog(context, (String) msg, "确定").show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isNotificationEnabled(Context context) {
        try {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
