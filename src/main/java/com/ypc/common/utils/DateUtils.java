package com.ypc.common.utils;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author dodo
 */
public class DateUtils {



    /**
     * es start
     */
    public static final String start = " 00:00:00";
    /**
     * es end
     */
    public static final String end = " 23:59:59";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    /**
     * 获取工时
     *
     * @param beginTime 开始
     * @param endTime   结束
     * @return
     */
    public static float getHours(String beginTime, String endTime) {

        // System.out.println(Integer.parseInt(workTime.getBeginTime()));
        Date date = null; // 定义时间类型
        Date date1 = null;
        SimpleDateFormat inputFormat = null;
        if (beginTime.length() < 10) {
            inputFormat = new SimpleDateFormat("HH:mm:ss");
        } else {
            inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            date = inputFormat.parse(beginTime);	//开始时间
            date1 = inputFormat.parse(endTime);		//结束时间
            long ss;
            float hours = 0;
            DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.

            if (date1.getTime() < date.getTime()) {
                ss = (date1.getTime() + 24 * 1000 * 3600 - date.getTime());
                hours = (float) ss / 1000 / 3600;
                String hours1 = decimalFormat.format(hours);// format 返回的是字符串
                return Float.parseFloat(hours1);
            } else {
                ss = (date1.getTime() - date.getTime());
                hours = (float) ss / 1000 / 3600;
                String hours1 = decimalFormat.format(hours);// format 返回的是字符串
                return Float.parseFloat(hours1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转换成日期（常规）
     *
     * @param date 字符串日期
     * @return
     */
    public static Date getDate(String date) {

        Date date1 = new Date();
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 字符串转换成日期格式
     *
     * @param date 字符串日期
     * @return
     */
    public static Date parseDate(String date, String format) {

        Date date1 = new Date();
        try {
            date1 = new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 日期转换成字符串格式
     */
    public static String formatDate(Date date, String format) {

        String dateStr = "";
        dateStr = new SimpleDateFormat(format).format(date);
        return dateStr;
    }

    /**
     * 字符串转换成指定格式字符串
     */
    public static String strToStr(String sourceStr, String sourceFormat, String targetFormat) {
        String targetStr = "";
        try {
            Date sourceDate = new SimpleDateFormat(sourceFormat).parse(sourceStr);
            targetStr = new SimpleDateFormat(targetFormat).format(sourceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetStr;
    }




    /**
     * 获取当前日期是星期几
     *
     * @param dt 当前date
     * @param num 星期数 1-7(星期天-星期六)
     * @return 当前日期是星期几
     */
    public static boolean getIsWeekOfDate(Date dt, int num) {
        int[] weekDays = {1, 2, 3, 4, 5, 6, 7};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w] == num;
    }

    /**
     * 含时区日期格式化
     *
     * @param date
     * @return
     */
    public static String getDateStr(String date) {

        date.replace("T", " ");
        if (date.length() < 10) {
            return date;
        } else {
            return date.substring(0, 19);
        }
    }

    //返回当前年月日
    public static String getNowDate() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        return nowDate;
    }

    //返回当前年月日 :yyyy-MM-dd
    public static String getSimpleNowDate() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return nowDate;
    }

    //返回当前年份  
    public static int getYear() {
        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    //返回当前月份  
    public static int getMonth() {
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    //返回上一年当前日期
    public static String getYesteryearDate(String date1,String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat(format).parse(date1));
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String yesteryearDate = new SimpleDateFormat(format).format(y);
        return yesteryearDate;
    }

    //判断闰年  
    public static boolean isLeap(int year) {
        return ((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0);
    }

    //返回当月天数  
    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year)) {
            FebDay = 29;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    //返回当月星期天数  
    public static int getSundays(int year, int month) {
        int sundays = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Calendar setDate = Calendar.getInstance();
        //从第一天开始  
        int day;
        for (day = 1; day <= getDays(year, month); day++) {
            setDate.set(Calendar.DATE, day);
            String str = sdf.format(setDate.getTime());
            if (str.equals("星期日")) {
                sundays++;
            }
        }
        return sundays;
    }

    /**
     * . 判断dateStr1是否小于dateStr2
     *
     * @param dateStr1
     * @param dateStr2
     * @param format
     * @return
     */
    public static boolean before(String dateStr1, String dateStr2, String format) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = dateFormat.parse(dateStr1);
            date2 = dateFormat.parse(dateStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return before(date1, date2);
    }

    /**
     * . 判断date1是否在date2前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean before(Date date1, Date date2) {
        return date1.before(date2);
    }

    /**
     * 获取日期间相隔月数
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2)
            throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));

        int month = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        int year = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;

        result = Math.abs(month + year);

        return result;

    }

    public static int getDaySpace(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date1));
        Long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(date2));
        Long time2 = cal.getTimeInMillis();
        Long between_days = Math.abs((time2 - time1) / (1000 * 3600 * 24));
        return between_days==null?0:between_days.intValue();
    }


    /**
     * 得到format格式的String类型的日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String getStringDate(Date date, String format) {
        return formatDate(date, format);
    }

    /**
     * 将String类型的日期格式化为其他类型
     *
     * @param dateStr
     * @param format1 传入格式
     * @param format2 输出格式
     * @return
     */
    public static String getStringDateFormat(String dateStr, String format1, String format2) {
        Date date = getDateFormat(dateStr, format1);
        String newDateStr = getStringDate(date, format2);
        return newDateStr;
    }


    /**
     * 将字符串日期时间转换为date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date getDateFormat(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String getMonthBetweenDate(String beginDate, String endDate) {
        if (beginDate.equals(endDate)) {
            return beginDate;
        } else if (!before(beginDate, endDate, "yyyy-MM-dd")) {
            return "";
        }
        String dateStr = "";
        dateStr = dateStr + beginDate + ",";//把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(parseDate(beginDate, "yyyy-MM-dd"));
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后  
            if (parseDate(endDate, "yyyy-MM-dd").after(cal.getTime())) {
                dateStr = dateStr + formatDate(cal.getTime(), "yyyy-MM-dd") + ",";

            } else {
                break;
            }
        }

        dateStr = dateStr + endDate;//把结束时间加入集合
        return dateStr;
    }

    /**
     * 得到系统当前日期
     *
     * @return Date
     */
    public static Date getSystemDate() {
        return new Date();
    }

    /**
     * 根据format得到相应String类型的系统当前时间
     *
     * @param format
     * @return
     */
    public static String getSystemDateForString(String format) {
        return formatDate(getSystemDate(), format);
    }

    /**
     * 字符串的日期格式的计算 两个日期日期之间的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Long time1 = null;
        try {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long time2 = cal.getTimeInMillis();
        Long between_days = Math.abs((time2 - time1) / (1000 * 3600 * 24));
        return between_days==null?0:between_days.intValue();
    }

    /**
     * 字符串的日期格式的计算 两个日期日期之间的天数
     * @param startTime
     * @param endTime
     * @param format
     * @return
     */
    public static int getDaysBetweenFromat(String startTime, String endTime,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        Long time1 = null;
        try {
            cal.setTime(sdf.parse(startTime));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long time2 = cal.getTimeInMillis();
        Long between_days = Math.abs((time2 - time1) / (1000 * 3600 * 24));
        return between_days==null?0:between_days.intValue();
    }

    /**
     * 字符串的日期格式的计算 两个日期日期之间的小时数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int hoursBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 得到日期的下/前n天
     *
     * @param date
     * @param format
     * @param returnFormat
     * @param n
     * @return
     */
    public static String getNextNDayFromDate(String date, String format, String returnFormat, int n) {
        if (StringUtils.isEmpty(date)
                || StringUtils.isEmpty(format)
                || StringUtils.isEmpty(returnFormat)) {
            return null;
        }
        Date date1 = getDateFormat(date, format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, n);

        return getStringDate(cal.getTime(), returnFormat);
    }

    /**
     * 得到日期的下/前n个月
     *
     * @param date
     * @param format
     * @param returnFormat
     * @param n
     * @return
     */
    public static String getNextNMonthFromDate(String date, String format, String returnFormat, int n) {
        if (StringUtils.isEmpty(date)
                || StringUtils.isEmpty(format)
                || StringUtils.isEmpty(returnFormat)) {
            return null;
        }
        Date date1 = getDateFormat(date, format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.MONTH, n);

        return getStringDate(cal.getTime(), returnFormat);
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据日期返回星期数
     *
     * @param date
     * @param format
     * @return 1是星期天
     */
    public static int getWeekDayFromFormatDate(String date, String format) {
        Date date1 = getDateFormat(date, format);
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        return weekDay;
    }


    /**
     * 计算两个日期的小时数 data2-data1
     *
     * @param
     * @param
     * @param format
     * @return
     */
    public static float getHoursFromFormatDate(String startTime, String endTime, String format) {
        Long nh = 1000 * 60 * 60L;//转小时
        Date date1 = getDateFormat(startTime, format);
        Date date2 = getDateFormat(endTime, format);
        Long diff = date2.getTime() - date1.getTime();
        float hour = diff.floatValue() / nh.floatValue();
        return hour;
    }

    /**
     * 得到日期的下/前n小时
     *
     * @param date
     * @param format
     * @param returnFormat
     * @param n
     * @return
     */
    public static String getNextNHoursFromDate(String date, String format, String returnFormat, float n) {
        if (StringUtils.isEmpty(date)
                || StringUtils.isEmpty(format)
                || StringUtils.isEmpty(returnFormat)) {
            return null;
        }
        String returnDate = "";
        Date date1 = getDateFormat(date, format);
        Calendar cal = null;
        if (n != 0) {
            cal = Calendar.getInstance();
            long temp = (long) (date1.getTime() + n * 1000 * 60 * 60);
            cal.setTimeInMillis(temp);
            returnDate = getStringDate(cal.getTime(), returnFormat);
        }else {
            returnDate = getStringDate(date1, returnFormat);
        }
        return returnDate;
    }

    /**
     * 判断给定的字符串是否满足format时间格式
     *
     * @param str
     * @param format yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isDate(String str, String format) {

        if (StringUtils.isEmpty(str)) {
            return false;
        }

        int pos = 0;

        Calendar cal = Calendar.getInstance();

        pos = format.indexOf("yyyy");
        String yyyy = "";
        if (pos != -1) {
            yyyy = str.substring(pos, pos + 4);
            cal.set(Calendar.YEAR, Integer.parseInt(yyyy));
        }

        pos = format.indexOf("MM");
        String mm = "";
        if (pos != -1) {
            mm = str.substring(pos, pos + 2);
            cal.set(Calendar.MONTH, Integer.parseInt(mm) - 1);
        }


        pos = format.indexOf("dd");
        String dd = "";
        if (pos != -1) {
            dd = str.substring(pos, pos + 2);
            cal.set(Calendar.DATE, Integer.parseInt(dd));
        }

        String tmp = "";
        pos = format.indexOf("HH");
        if (pos != -1) {
            tmp = str.substring(pos, pos + 2);
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmp));
        }

        pos = format.indexOf("mm");
        if (pos != -1) {
            tmp = str.substring(pos, pos + 2);
            cal.set(Calendar.MINUTE, Integer.parseInt(tmp));
        }

        pos = format.indexOf("ss");
        if (pos != -1) {
            tmp = str.substring(pos, pos + 2);
            cal.set(Calendar.SECOND, Integer.parseInt(tmp));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Date date = cal.getTime();
        tmp = dateFormat.format(date);

        return tmp.equals(str);

    }

    /**
     * 根据给定天数，生成某月某一天
     */
    public static String getTheDayByDayNumAndMonthNum(int month, int day) {

        int days = DateUtils.getCurrentMonthLastDay();
        if (day > days) {
            day = days;
        }
        if (month < 0 || month > 12) {
            month = 1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String format = sdf.format(new Date());
        format += "-" + month + "-" + day;

        return format;
    }
    
    /**
     * 计算两个日期的秒数 data2-data1
     *
     * @param
     * @param
     * @param format
     * @return
     */
    public static float getSecondFromFormatDate(String startTime, String endTime, String format) {
        Long nh = 1000L;//转秒
        Date date1 = getDateFormat(startTime, format);
        Date date2 = getDateFormat(endTime, format);
        Long diff = date2.getTime() - date1.getTime();
        float second = diff.floatValue() / nh.floatValue();
        return second;
    }

    /**
     * 得到当前时间的毫秒数
     * @return
     */
    public static long getCurrentMills() {
        return System.currentTimeMillis();
    }

    public static long getCurrentMills(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("转换毫秒数失败！");
            e.printStackTrace();
        }
        long time = date.getTime();

        return time;
    }

    public static String getDataStr(long mills, String format) {
        String dateStr = null;
        Date d = new Date();
        d.setTime(mills);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(d);
        } catch (Exception e) {
            System.out.println("转换日期字符串失败！");
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     *
     * @param date 格式："2017-12-14"
     * @return
     */
    public static long startMills(String date) {
        date += start;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = -1L;
        try {
            Date d = sdf.parse(date);
            time = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;

    }

    /**
     *
     * @param date 格式："2017-12-14"
     * @return
     */
    public static long endMills(String date) {
        date += end;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = -1L;
        try {
            Date d = sdf.parse(date);
            time = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;

    }


    /**
     * 格式化日期
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date){
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirstDate(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static String getCurrYearFirstDateString(String format){
        Date result = getCurrYearFirstDate();
        return getStringDate(result,format);
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static Date getCurrYearLastDate(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static String getCurrYearLastDateString(String format){
        Date result = getCurrYearLastDate();
        return getStringDate(result,format);
    }


    /**
     * 获取当年上一年的第一天
     * @return
     */
    public static Date getCurrLastYearFirstDate(){
        Calendar currCal=Calendar.getInstance();
        currCal.add(Calendar.YEAR, -1);
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    public static String getCurrBeforeYearFirstDateString(String format){
        Date result = getCurrLastYearFirstDate();
        return getStringDate(result,format);
    }

    /**
     * 得到某年的最后一天的字符串
     * @param year 某年（字符串）
     * @return
     */
    public static String getOneYearFirstDateString(String year) {
        String result = null;
        try {
            Integer parseYear = Integer.parseInt(year);
            result = parseYear.toString() + "-01-01";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 得到某年的第一天的字符串
     * @param year 某年（字符串）
     * @return
     */
    public static String getOneYearLastDateString(String year) {
        String result = null;
        try {
            Integer parseYear = Integer.parseInt(year);
            result = parseYear.toString() + "-12-31";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取当年上一年的最后一天
     * @return
     */
    public static Date getCurrLastYearLastDate(){
        Calendar currCal=Calendar.getInstance();
        currCal.add(Calendar.YEAR, -1);
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    public static String getCurrBeforeYearLastDateString(String format){
        Date result = getCurrLastYearLastDate();
        return getStringDate(result,format);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 获取当前月份的第一天
     * @return
     */
    public static String getMonthFist(){
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return f.format(cale.getTime());
    }

    /**
     * 获取当前月最后一天
     * @return
     */
    public static String getMonthLastDay(){
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return f.format(cale.getTime());
    }
    /**
     * 获取去年同一天
     * @return
     * @throws ParseException 
     */
    public static String getLastyearSameday(String date) throws ParseException{
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	Date date2 = format.parse(date);
    	c.setTime(date2);
    	c.add(Calendar.YEAR, -1);
    	Date y = c.getTime();
    	String year = format.format(y);
//    	System.out.println("过去一年："+year);
    	return year;
    }
    
    
    
    /**
     * 获取两个日期之间的月份差
     * @return
     * @throws ParseException 
     */
    public static List<String> getMonthBetween(String start,String end) throws ParseException{
    	List<String> list =new ArrayList<>();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    	Calendar c = Calendar.getInstance();
    	Calendar c2 = Calendar.getInstance();
    	Date startDate = format.parse(start);
    	Date endDate = format.parse(end);
    	c.setTime(startDate);
    	c2.setTime(endDate);
    	Date x = c.getTime();
		String startTime = format.format(x);
		list.add(startTime);
		Date y=null;
    	while(x.getTime()!=endDate.getTime()){
    		c.add(Calendar.MONTH,+1);
    		y = c.getTime();
    		x = c.getTime();
    		String year = format.format(y);
    		list.add(year);
    	}
    	return list;
    }
    
    /**
     * 获取某年月份差
     * @return
     * @throws ParseException 
     */
    public static List<String> getMonthBetween2(String date) throws ParseException{
    	List<String> list =new ArrayList<>();
    	String start=date+"-01";
    	String end=date+"-12";
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM");
    	Calendar c = Calendar.getInstance();
    	Calendar c2 = Calendar.getInstance();
    	Date startDate = format.parse(start);
    	Date endDate = format.parse(end);
    	c.setTime(startDate);
    	c2.setTime(endDate);
    	Date x = c.getTime();
    	String startTime = format.format(x);
    	list.add(startTime);
    	Date y=null;
    	while(x.getTime()!=endDate.getTime()){
    		c.add(Calendar.MONTH,+1);
    		y = c.getTime();
    		x = c.getTime();
    		String year = format.format(y);
    		list.add(year);
    	}
    	return list;
    }
    
    /**
     * 获取两个日期月份差
     * @return
     * @throws ParseException 
     */
    public static List<String> getMonthBetween3(String start,String end) throws ParseException{
    	List<String> list =new ArrayList<>();
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM");
    	Calendar c = Calendar.getInstance();
    	Calendar c2 = Calendar.getInstance();
    	Date startDate = format.parse(start);
    	Date endDate = format.parse(end);
    	c.setTime(startDate);
    	c2.setTime(endDate);
    	Date x = c.getTime();
    	String startTime = format.format(x);
    	list.add(startTime);
    	Date y=null;
    	while(x.getTime()!=endDate.getTime()){
    		c.add(Calendar.MONTH,+1);
    		y = c.getTime();
    		x = c.getTime();
    		String year = format.format(y);
    		list.add(year);
    	}
    	return list;
    }
    
    
    /**
     * 获取某月的最后一天
     * @return
     * @throws ParseException 
     */
    public static String getMonthLast(String date) throws ParseException{
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar a = Calendar.getInstance();
    	Date date2 = format.parse(date);
   	 	a.setTime(date2);
//        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
//        int maxDate = a.get(Calendar.DATE);
        Date time = a.getTime();
        String str = format2.format(time);
    	return str;
    }

    /**
     * 获取某月的最早一天
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMonthFirst(String date) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar a = Calendar.getInstance();
        Date date2 = format.parse(date);
        a.setTime(date2);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        Date time = a.getTime();
        String str = format2.format(time);
        return str;
    }
    /**
     * 获取某月的第一天和最后一天
     * @return
     * @throws ParseException 
     */
    public static List<String> getMonthFirstLast(String date) {
    	List<String> list =new ArrayList<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar a = Calendar.getInstance();
            Calendar b = Calendar.getInstance();
            Date date2 = format.parse(date);
            a.setTime(date2);
            a.set(Calendar.DATE, 1);//把日期设置为当月第一天
            Date time = a.getTime();
            String str = format2.format(time);
            list.add(str);
            b.setTime(time);
            b.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
            Date time2 = b.getTime();
            String str2 = format2.format(time2);
            list.add(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 获取上一年
     * @return
     * @throws ParseException 
     */
    public static String getLastYear(String date) throws ParseException{
		SimpleDateFormat format= new SimpleDateFormat("yyyy");
		Date year = format.parse(date);
		Calendar c = Calendar.getInstance();
		c.setTime(year);
		c.add(Calendar.YEAR,-1);
		Date lastYear = c.getTime();
		String lastDate = format.format(lastYear);
		return lastDate;
    }
    
    public static String dateToStamp(String d) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(d);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    
    
    /**
     * 返回两个日期的中的每一天
     * @param
     * @param
     * @param sign
     * @return String [] date 
     * @throws ParseException 
     */
    public  static List<String> displayDay(String startDate, String endDate,String sign) throws ParseException{
    	List<String> list =new ArrayList<>();
/*    	String startDate ="2018-05-09 10:47:06";
    	String endDate ="2018-05-11 12:47:06";
    	String sign ="1";*/
    	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");  
    	
    	Date date1 = df1.parse(startDate);
    	Date date2 = df1.parse(endDate);
    	
    	Calendar cal =Calendar.getInstance();
    	cal.setTime(date1);
		if(sign.equals("1")){
	    	while(cal.getTime().before(date2)){
	    		String format = df1.format(cal.getTime());
	    		list.add(format);
	    		cal.add(cal.DAY_OF_MONTH, 1);
	    	}
	    	String format = df1.format(cal.getTime());
			list.add(format);
		}else if(sign.equals("2")){
	    	while(cal.getTime().before(date2)){
	    		String format = df1.format(cal.getTime());
	    		list.add(format);
	    		cal.add(cal.DAY_OF_MONTH, 1);
	    	}
		}else if(sign.equals("3")){
			while(cal.getTime().before(date2)){
	    		
	    		String format = df1.format(cal.getTime());
	    		list.add(format);
	    		cal.add(cal.DAY_OF_MONTH, 1);
	    	}
	    	String format = df1.format(cal.getTime());
			list.add(format);
			list.remove(list.get(0));
		}else if(sign.equals("4")){
			while(cal.getTime().before(date2)){
	    		String format = df1.format(cal.getTime());
	    		list.add(format);
	    		cal.add(cal.DAY_OF_MONTH, 1);
	    	}
			list.remove(list.get(0));
		}
		/*for (String string : list) {
			System.out.println(string);
		}*/
		return list;
    	
    }

    /**
     * 获取某年某周某天
     * @param year 年
     * @param weekNum 周数
     * @param dayNum 第几天  1表示周日，2表示周一，7表示周六
     * @param format 返回格式
     * @return
     */
    public static String getYearWeekNumDay(String year,int weekNum,int dayNum,String format){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(year));
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        cal.set(Calendar.DAY_OF_WEEK, dayNum);
        Date date = cal.getTime();
        String result = getStringDate(date,format);
        return result;
    }

    // 获得本周一0点时间
    public static String getDateWeekFirst() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getStringDate(cal.getTime(),"yyyy-MM-dd");
    }

    // 获得本周日24点时间
    public static String getDateWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFormat(getDateWeekFirst(),"yyyy-MM-dd"));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return getStringDate(cal.getTime(),"yyyy-MM-dd");
    }

}
