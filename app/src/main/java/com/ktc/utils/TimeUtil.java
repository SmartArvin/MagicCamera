package com.ktc.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 时间处理相关
 * @author Arvin
 * @date 2019.10.08
 */
public class TimeUtil {

    public static String getCurrentTime(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf;
        if (isCurrent24Hour(context.getApplicationContext())) {
            sf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        } else {
            sf = new SimpleDateFormat("hh:mm", Locale.getDefault());
        }
        return sf.format(calendar.getTime());
    }

    /**
     * 是否是24小时制
     *
     * @param context
     * @return true 是24小时制； false 是12小时制
     */
    public static boolean isCurrent24Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    private static String formatTime (String format, Locale locale) {
        SimpleDateFormat sf = new SimpleDateFormat(format, locale);
        return sf.format(new Date());
    }

    public static String getHour (Context context) {
        return String.valueOf(getCurrentCalendar().get(Calendar.HOUR_OF_DAY));
    }

    private static Calendar getCurrentCalendar () {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        return calendar;
    }

    public static String getMinute () {
        int minute = getCurrentCalendar().get(Calendar.MINUTE);
        StringBuilder builder = new StringBuilder();
        if(minute < 10){
            builder.append(0);
        }
        builder.append(minute);
        return builder.toString();
    }

    public static String getDate (Context context) {
        Locale locale  = getCurrentCountry(context);
        String format;
        if(locale.equals(Locale.CHINA)){
            format = "MM月dd日";
        }else{
            format = "MM/dd";
        }
        return DateFormat.format(format, getCurrentCalendar()).toString();
    }

    public static String getWeek (Context context) {
        return formatTime("EEEE", getCurrentCountry(context));
    }

    public static boolean isInChina (Context context) {
        return getCurrentCountry(context).equals(Locale.CHINA);
    }

    public static Locale getCurrentCountry (Context context) {
        return context.getResources().getConfiguration().locale;
    }

}
