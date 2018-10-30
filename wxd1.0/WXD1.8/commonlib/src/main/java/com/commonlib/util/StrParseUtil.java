package com.commonlib.util;

import android.text.TextUtils;

/**
 * 字符串类型转化成其他基本类型，byte、short、char不常用，所以暂时不处理
 *
 * @author lanyan
 */
public class StrParseUtil {
    public static float parseFloat(String string) {
        if (TextUtils.isEmpty(string)) {
            return 0;
        }
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int parseInt(String string) {
        if (TextUtils.isEmpty(string)) {
            return 0;
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double parseDouble(String string) {
        if (TextUtils.isEmpty(string)) {
            return 0;
        }
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long parseLong(String string) {
        if (TextUtils.isEmpty(string)) {
            return 0;
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean parseBoolean(String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        try {
            return Boolean.parseBoolean(string);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean equal(String compare, String obj) {
        if (TextUtils.isEmpty(compare)) {
            return false;
        }

        return compare.equals(obj);
    }

    public static String checkNull(String s) {
        if (TextUtils.isEmpty(s))
            return "";
        else
            return s;
    }

    public static String checkNull(String s, String s2) {
        if (TextUtils.isEmpty(s))
            return s2;
        else
            return s;
    }


    public static String addComma(String str) {        // 将传进数字反转
        String strn = str.substring(str.length() - 3, str.length());
        String strp = str.substring(0, str.length() - 3);
        String reverseStr = new StringBuilder(strp).reverse().toString();
        String strTemp = "";

        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        return resultStr + strn;
    }

    public static String priceChange(String text, int type) {
        String o = String.format("%1$01.2f", Float.parseFloat(text));
        String dw;
        if (o.length() > 11) {
            dw = "亿";
            o = String.format("%1$01.2f", Float.parseFloat(o) / 100000000);
        } else if (o.length() > 7) {
            dw = "万";
            o = String.format("%1$01.2f", Float.parseFloat(o) / 10000);
        } else {
            if (type == 1) {
                dw = " ";
            } else {
                dw = "元";
            }
            o = String.format("%1$01.2f", Float.parseFloat(o));
        }
        return o + dw;
    }
}
