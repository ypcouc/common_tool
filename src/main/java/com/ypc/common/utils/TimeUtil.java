package com.ypc.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static Date date = new Date();

    public static String getCurrentTimeStr(String formatStr){
        DateFormat format = new SimpleDateFormat(formatStr);
        String dateStr = format.format(date);
        return dateStr;
    }

}
