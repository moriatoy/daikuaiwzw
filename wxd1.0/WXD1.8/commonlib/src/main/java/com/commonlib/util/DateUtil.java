package com.commonlib.util;

import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期转化工具
 *
 * @author lanyan
 */
public class DateUtil {

    /**
     * 将long型格式转化为所需格式的日期，以字符串返回
     *
     * @param time    需要转化的时间，单位是毫秒
     * @param pattern 待转化的格式
     * @return
     */
    public static String format(long time, String pattern) {
//        if (time < 0 || TextUtils.isEmpty(pattern)) {
//            return "";
//        }
        if (TextUtils.isEmpty(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        Date dt = new Date(time);
        return sdf.format(dt);
    }

    /**
     * 将string型格式转化为所需格式的日期，以字符串返回
     *
     * @param time     需要转化的时间
     * @param previous 转化前的格式
     * @param pattern  待转化的格式
     * @return
     */
    public static String format(String time, String previous, String pattern) {
        if (TextUtils.isEmpty(time) || TextUtils.isEmpty(pattern)) {
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(previous, Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            Date dt = sdf1.parse(time);
            return sdf2.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取与当前时间的间隔毫秒数
     *
     * @param time
     * @param pattern
     * @return
     */
    public static long getIntervalTime(String time, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            Date date = df.parse(time);
            return date.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取与当前时间的间隔毫秒数
     *
     * @param time
     * @param pattern
     * @return
     */
    public static long getFanIntervalTime(String time, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date date = df.parse(time);
            return System.currentTimeMillis() - date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将毫秒数转成天 小时 分 秒
     *
     * @param time
     * @return
     */
    public static String calculateTimeBySec(long time) {
        if (time < 0) {
            return "";
        }
        int days = (int) (time / 86400000);
        int hour = (int) ((time - days * 86400000) / 3600000);
        int minute = (int) ((time - days * 86400000 - hour * 3600000) / 60000);
        int second = (int) ((time - days * 86400000 - hour * 3600000 - minute * 60000) / 1000);
        if (days < 1) {
            if (hour < 1) {
                if (minute < 1) {
                    return second + "秒";
                } else {
                    return minute + "分" + second + "秒";
                }
            } else {
                return hour + "小时" + minute + "分" + second + "秒";
            }
        } else
            return days + "天" + hour + "小时" + minute + "分" + second + "秒";
    }

    /**
     * 比较2个时间的大小
     *
     * @param time1   第一个时间
     * @param format1 第一个时间的时间个时
     * @param time2   第二个时间
     * @param format2 第二个时间的时间格式
     * @return 1 第一个时间迟;-1 第二个时间迟;0 相等
     */
    public static int compareDates(String time1, String format1, String time2,
                                   String format2) {
        if (TextUtils.isEmpty(time1) || TextUtils.isEmpty(time2)
                || TextUtils.isEmpty(format1) || TextUtils.isEmpty(format2)) {
            return 0;
        }
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(format1, Locale.CHINA);
            SimpleDateFormat sdf2 = new SimpleDateFormat(format2, Locale.CHINA);
            Date d1 = sdf1.parse(time1);
            Date d2 = sdf2.parse(time2);
            if (d1.after(d2)) {
                return 1;
            } else if (d1.before(d2)) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strToDateCh(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dateToStr(Date dateDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    public static Long stringToLong(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates.getTime();
    }

    public static Long stringToLong(String date, String formats) {
        SimpleDateFormat format = new SimpleDateFormat(formats);
        Date dates = null;
        try {
            dates = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates.getTime();
    }

    public static Long stringToLongMonth(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date dates = null;
        try {
            dates = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates.getTime();
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 以友好的方式显示时间
     * @param sdate
     * @return
     */
    public static String friendly_time(Context context,String sdate) {
        //定义参数
        String min_str = "";
        String hour_str = "";
        String day_str = "";
        String yesterday_str = "";
        String before_yesterday_str = "";
        if(LanguageUtil.isZH(context)){
            min_str = "分钟前";
            hour_str = "小时前";
            day_str = "天前";
            yesterday_str = "昨天";
            before_yesterday_str = "前天";
        }else {
            min_str = " minutes ago";
            hour_str = " hours ago";
            day_str = " days ago";
            yesterday_str = "yesterday";
            before_yesterday_str = "the day before yesterday";
        }

        Date time = toDate(sdate);
        if(time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if(curDate.equals(paramDate)){
            int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
            if(hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+min_str;
            else
                ftime = hour+hour_str;
            return ftime;
        }
        long lt = time.getTime()/86400000;
        long ct = cal.getTimeInMillis()/86400000;
        int days = (int)(ct - lt);
        if(days == 0){
            int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
            if(hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+min_str;
            else
                ftime = hour+hour_str;
        }
        else if(days == 1){
            ftime = yesterday_str;
        }
        else if(days == 2){
            ftime = before_yesterday_str;
        }
        else if(days > 2 && days <= 7){
            ftime = days+day_str;
        }
        else if(days > 7){
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater2.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * 毫秒转时分秒
     */
    public static String toMHS(long mill){
        long hour = mill/(60*60*1000);
        long minute = (mill - hour*60*60*1000)/(60*1000);
        long second = (mill - hour*60*60*1000 - minute*60*1000)/1000;
        if(second >= 60 )
        {
            second = second % 60;
            minute+=second/60;
        }
        if(minute >= 60)
        {
            minute = minute %60;
            hour += minute/60;
        }
        String sh = "";
        String sm ="";
        String ss = "";
        if(hour <10)
        {
            sh = "0" + String.valueOf(hour);
        }else
        {
            sh = String.valueOf(hour);
        }
        if(minute <10)
        {
            sm = "0" + String.valueOf(minute);
        }else
        {
            sm = String.valueOf(minute);
        }
        if(second <10)
        {
            ss = "0" + String.valueOf(second);
        }else
        {
            ss = String.valueOf(second);
        }
        return sh +":" + sm + ":" + ss;
    }

    /**
     * 当天显示时分  不是当天显示月日时分   不是今年显示年月日时分
     */
    public static String getFriendlyTime(long mills){
        String time="";

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        String format_str = format(mills,"yyyy-MM-dd HH:mm");
        int format_year = Integer.parseInt(format_str.substring(0,4));
        int format_month = Integer.parseInt(format_str.substring(5,7));
        int format_day = Integer.parseInt(format_str.substring(8,10));
        int formate_hour = Integer.parseInt(format_str.substring(11,13));
        int formate_minutes = Integer.parseInt(format_str.substring(14,format_str.length()));

        if(year==format_year){
            if(day==format_day){
                time = format(mills,"HH:mm");
            }else {
                time = format(mills,"MM-dd HH:mm");
            }
        }else {
            time = format(mills,"yyyy-MM-dd HH:mm");
            return time;
        }
        return time;
    }


    /**
     * 一天之内几分钟前 一天之外 yyyy-MM-dd HH:mm:ss
     */
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年
    public static String getAgoTime(long mills){
        String format = "yyyy-MM-dd HH:mm:ss";
        String str_time = format(mills,format);
        Date date = strToDate(str_time,format);

        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();

        //大于一天则显示 yyyy-MM-dd HH:mm:ss
        if(diff>day){
            return format(mills,"yyyy-MM-dd HH:mm");
        }

        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }
}
