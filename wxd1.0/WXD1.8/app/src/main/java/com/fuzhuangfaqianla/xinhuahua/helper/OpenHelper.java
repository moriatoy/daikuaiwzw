package com.fuzhuangfaqianla.xinhuahua.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by xiongchang on 2017/9/22.
 *
 * 此类用于方便统一管理转场动画
 */

public class OpenHelper {

    public static void startActivity(Context context,Class<?> cls){
        context.startActivity(new Intent(context,cls));
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
    }

    public static void startActivity(Context context,Intent intent,int requestCode){
        ((Activity)context).startActivityForResult(intent,requestCode);
    }
}
