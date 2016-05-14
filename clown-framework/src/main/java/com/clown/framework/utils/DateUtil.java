package com.clown.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 11/12/2015.
 * 简单日期处理工具类
 */
public class DateUtil {

    public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
    public static final String YM = "yyyyMM";
    public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-mm-dd hh24:mi:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_ALL = "yyyyMMddHHmmssS";


    /**
     * 获得当前日期格式化串
     * @return
     * @throws Exception
     */
    public static String currentDateToString() throws Exception{
        return currentDateToString(DATETIME_FORMAT);
    }

    /**
     * 指定日期格式化字符串
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static String dateToString(Date date,String format) throws Exception{
        return formatDate(date, format);
    }

    public static String currentDateToString(String format) throws Exception{
        Date date = new Date();
        return formatDate(date, format);
    }

    public static String crrentDateYearName() throws Exception{
        Date date = new Date();
        return formatDate(date, "yyyy");
    }

    /**
     * 字符串转日期
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date stringToDate(String dateString) throws Exception{
       return stringToDate(dateString, DATETIME_FORMAT);
    }

    /**
     * 字符串日期转时间戳
     * @param dateString
     * @return
     * @throws Exception
     */
    public static long stringDateToTimeInMillis(String dateString) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate(dateString));
        return calendar.getTimeInMillis();
    }

    public static Date stringToDate(String dateString,String format) throws Exception{
        return parseDate(dateString, format);
    }

    private static Date parseDate(String date,String format) throws Exception{

        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(format);
        localSimpleDateFormat.setLenient(false);
        return localSimpleDateFormat.parse(date);
    }

    private static String formatDate(Date date,String format) throws Exception{

        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(format);
        localSimpleDateFormat.setLenient(false);
        return localSimpleDateFormat.format(date);
    }



    /**
     * 两个日期相差多少个月
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static int compareDateForMonth(String startTime,String endTime) throws Exception{
        Calendar startD = Calendar.getInstance();
        startD.setTime(parseDate(startTime, NORMAL_DATE_FORMAT));
        startD.set(Calendar.DATE, 1);


        Calendar endD = Calendar.getInstance();
        endD.setTime(parseDate(endTime,NORMAL_DATE_FORMAT));
        endD.set(Calendar.DATE, 1);
        long result = (startD.getTimeInMillis() - endD.getTimeInMillis())/(1000*24*3600);
        int dx =Math.abs(new Long(result).intValue() / 30);
        return dx;
    }

    /**
     * 两个日期相差多少天
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static double compareDateForDay(String startTime,String endTime) throws Exception{
        Calendar startD = Calendar.getInstance();
        startD.setTime(parseDate(startTime, DATETIME_FORMAT));

        Calendar endD = Calendar.getInstance();
        endD.setTime(parseDate(endTime, DATETIME_FORMAT));

        double result = (startD.getTimeInMillis() - endD.getTimeInMillis())/ (double)(1000*24*3600);
        return Math.abs(result);
    }

    /**
     * 获得某年的第一天
     * @param year
     * @return
     * @throws Exception
     */
    public static String getFirstDateByYear(int year) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return dateToString(calendar.getTime(), NORMAL_DATE_FORMAT);
    }

    /**
     * 获得某年的最后一天
     * @param year
     * @return
     * @throws Exception
     */
    public static String getLashDateByYear(int year) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return dateToString(calendar.getTime(), NORMAL_DATE_FORMAT);
    }

    public static String getFirstDateByYearForCurrent() throws Exception{
        Calendar calendar = Calendar.getInstance();
        return getFirstDateByYear(calendar.get(Calendar.YEAR));
    }

    public static String getLashDateByYearForCurrent() throws Exception{
        Calendar calendar = Calendar.getInstance();
        return getLashDateByYear(calendar.get(Calendar.YEAR));
    }

    /**
     * 获得上一个月的最后一天的时间
     * @return
     * @throws Exception
     */
    public static String getLastMonthMaxDay() throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int MaxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set( calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), MaxDay, 23, 59, 59);
        return dateToString(calendar.getTime(), DATETIME_FORMAT);
    }

    /**
     * 获得当前月向前推agoNumber 个月的第一天
     * @param agoNumber
     * @return
     * @throws Exception
     */
    public static String getMonthAgo(Integer agoNumber) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, agoNumber*-1);
        int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set( calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), minDay, 0, 0, 0);
        return dateToString(calendar.getTime(), DATETIME_FORMAT);
    }


    public static void main(String[] args) throws Exception{
    }



}
