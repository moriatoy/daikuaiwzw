package com.commonlib.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by xiongchang on 2017/9/13.
 */

public class LanguageUtil {

    public static String getLocale(Context context){
        String str = context.getResources().getConfiguration().locale.getLanguage();
        return str;
    }

    public static boolean isZH(Context context){
        if(getLocale(context).equals("zh")){
            return true;
        }
        return false;
    }

    public static void updateAppLanguage(Context context,Locale locale){
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        DisplayMetrics dm = res.getDisplayMetrics();
        res.updateConfiguration(config, dm);
    }
}
