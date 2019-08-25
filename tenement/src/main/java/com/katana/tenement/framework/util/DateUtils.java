package com.katana.tenement.framework.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 * @date 17/4/5
 */
public class DateUtils {


    public static final String STANDARD_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_TIME_PATTERN_NO_SEPARATOR = "yyyyMMddHHmmss";
    public static final String STANDARD_DATE_PATTERN = "yyyy-MM-dd";
    public static final String STANDARD_DAY_TIME_PATTERN = "HH:mm:ss";
    public static final String STANDARD_CHINESE_DATE_PATTERN = "yyyy年MM月dd日";


    /**
     * 获取日期字符串
     *
     * @param date
     * @param datePattern
     * @return
     */
    public static String getDateStr(Date date, String datePattern) {
        String dateStr = "";

        if (date == null) {
            return dateStr;
        }
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateStr = dateFormat.format(date);
        return dateStr;
    }

    public static Date getDate(String dateStr, String datePattern) {

        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static LocalDate getLocalDate(String dateStr, String datePattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(dateStr, dateTimeFormatter);
    }

    public static LocalDateTime getLocalDateTime(String dateStr, String datePattern) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDateTime.parse(dateStr, dateTimeFormatter);
    }


    /**
     * 时间格式转换：  yyyyMMddHHmmss 转换成 yyyy-MM-dd HH:mm:ss
     *
     * @param timeStr
     * @return
     */
    public static String format(String timeStr) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(STANDARD_TIME_PATTERN_NO_SEPARATOR);
            Date date = sdf1.parse(timeStr);
            SimpleDateFormat sdf2 = new SimpleDateFormat(STANDARD_TIME_PATTERN);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Date --> String
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(STANDARD_TIME_PATTERN);
        return formatter.format(date);
    }

    /**
     * 时间戳转换成日期格式字符串
     * 精确到秒的字符串(yyyy-MM-dd HH:mm:ss)
     *
     * @param seconds
     * @return
     */
    public static String timeStampToDate(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_TIME_PATTERN);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    /**
     * 日期格式字符串转换成时间戳
     * yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static Integer transformation(String dateTime) {
        try {
            String tempStart = Timestamp.valueOf(dateTime).toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_TIME_PATTERN);
            Date date = simpleDateFormat.parse(tempStart);
            int result = (int) (date.getTime() / 1000);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 日期格式字符串转换成时间戳
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date_str
     * @return
     */
    public static String dateTimeStamp(String date_str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_TIME_PATTERN);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * LocalDate格式化 yyyy-MM-dd
     *
     * @param localDate
     * @return
     */
    public static String getLocalDateStr(LocalDate localDate) {
        return DateUtils.getLocalDateStr(localDate, DateUtils.STANDARD_DATE_PATTERN);
    }

    public static String getLocalDateStr(LocalDate localDate,String datePattern){

        if (localDate == null) {
            return "";
        }
        return localDate.format(DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * 获取日期时间字符串
     * yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String getDateTimeStr(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return DateUtils.getDateStr(new Date(timestamp.getTime()), DateUtils.STANDARD_TIME_PATTERN);
    }

    /**
     * 获取当天日期时间最后一秒---传入Date类型
     *
     * @param endDate
     * @return
     */
    public static Date getDateLastSecond(Date endDate) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strEndDate = format.format(endDate);
        return DateUtils.getDateLastSecond(strEndDate);
    }

    /**
     * 获取当天日期时间最后一秒---输出Date类型
     * yyyy-MM-dd HH:mm:ss
     *
     * @param endDate
     * @return
     */
    public static Date getDateLastSecond(String endDate) {
        Date finalEndDate = null;
        DateFormat format = new SimpleDateFormat(STANDARD_TIME_PATTERN);
        if (endDate != null) {
            try {
                endDate = endDate + " 23:59:59";
                finalEndDate = format.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            finalEndDate = null;
        }
        return finalEndDate;
    }

    /**
     * 获取当天日期时间初始状态---输出Date类型
     * yyyy-MM-dd HH:mm:ss
     *
     * @param startDate
     * @return
     */

    public static Date getFirstDate(String startDate) {
        Date finalStartDate = null;
        DateFormat format = new SimpleDateFormat(STANDARD_TIME_PATTERN);
        if (startDate != null) {
            try {
                startDate = startDate + " 00:00:00";
                finalStartDate = format.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            finalStartDate = null;
        }
        return finalStartDate;
    }

    /**
     * Date --> LocalDateTime
     */
    public static LocalDateTime getDateToLocalDateTime(Date date) {
        if (date != null) {
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }
        return null;
    }

    /**
     * String --> Date
     */
    public static Date stringToDate(String DateStr) {
        Date finalDate = null;
        DateFormat format = new SimpleDateFormat(STANDARD_TIME_PATTERN);
        if (DateStr != null) {
            try {
                finalDate = format.parse(DateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            finalDate = null;
        }
        return finalDate;
    }

    /**
     * LocalDateTime --> Date
     */
    public static Date getLocalDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            return Date.from(instant);
        }
        return null;
    }

    /**
     * 获取当前日期的前一天
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYesteDayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_TIME_PATTERN);
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -1);    //得到前一天
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当天日期时间最后一秒---传入LocalDateTime类型
     *
     * @param endDate
     * @return
     */
    public static LocalDateTime getLocalDateTimeLastSecond(LocalDateTime endDate) {

        if (endDate != null) {
            Date date = DateUtils.getDateLastSecond(DateUtils.getLocalDateTimeToDate(endDate));

            int dayMis = 1000 * 60 * 60 * 24;//一天的毫秒-1
//            System.out.println("一天的毫秒-1:" + dayMis);

            //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            long curMillisecond = date.getTime();//当天的毫秒
//            System.out.println("curMillisecond:" + new Date(curMillisecond));

//            long resultMis = curMillisecond + (dayMis - 1); //当天最后一秒
//            System.out.println("resultMis:" + resultMis);

            //得到须要的时间    当天最后一秒
            return DateUtils.getDateToLocalDateTime(new Date(curMillisecond));
        }

        return endDate;
    }

    /**
     * LocalDateTime格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime
     * @return
     */
    public static String getLocalDateTimeStr(LocalDateTime localDateTime) {
        return DateUtils.getLocalDateTimeStr(localDateTime, DateUtils.STANDARD_TIME_PATTERN);
    }

    /**
     * Loacldatetime格式化
     *
     * @param localDateTime
     * @param datePattern
     * @return
     */
    public static String getLocalDateTimeStr(LocalDateTime localDateTime, String datePattern) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * 获取日期时间字符串
     *
     * @param timestamp
     * @param dateFormat
     * @return
     */
    public static String getDateTimeStr(Timestamp timestamp, String dateFormat) {
        return DateUtils.getDateStr(new Date(timestamp.getTime()), dateFormat);
    }

    /**
     * 获取时间戳日期字符串
     * yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static synchronized String getTimestampStr(long time) {
        return DateUtils.getDateStr(new Date(time * 1000L), DateUtils.STANDARD_TIME_PATTERN);
    }

    /**
     * 获取日期时间字符串
     *
     * @param timestamp
     * @return
     */
    public static String getDayTimeStr(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        return DateUtils.getDateStr(new Date(timestamp.getTime()), DateUtils.STANDARD_DAY_TIME_PATTERN);
    }

    public static Timestamp getCurentTimestamp() {
        Timestamp realTimeStamp =
                new Timestamp(Instant.now().toEpochMilli());
        return realTimeStamp;
    }

    public static String getCurentTimeString() {
        return DateUtils.getDateStr(new Date(), STANDARD_TIME_PATTERN);
    }


    public static List<Date> getIntervalIDates(Date startDate, Date endDate) {

        List<Date> dates = new ArrayList<>();

        LocalDateTime start = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

        if (start.isEqual(end)) {
            dates.add(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()));
            return dates;
        }

        if (!start.isBefore(end)) {
            return dates;
        }

        dates.add(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()));
        while (true) {
            LocalDateTime transitionTime = start.plusDays(1);
            if (transitionTime.isBefore(end) || transitionTime.isEqual(end)) {
                dates.add(Date.from(transitionTime.atZone(ZoneId.systemDefault()).toInstant()));
                start = transitionTime;
            } else {
                break;
            }
        }
        return dates;
    }

    public static String getStandardDateStr(Date date) {
        return getDateStr(date, DateUtils.STANDARD_DATE_PATTERN);
    }

    /**
     * 获取昨天半夜00:00:00
     *
     * @return
     */
    public static Date getYesterdayStart() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.MINUTE, 0);
        todayEnd.set(Calendar.SECOND, 0);
        todayEnd.set(Calendar.MILLISECOND, 0);
        todayEnd.add(Calendar.DATE, -1);
        todayEnd.set(Calendar.HOUR_OF_DAY, 0);
        return todayEnd.getTime();
    }

    /**
     * 获取昨天半夜23:59:59
     *
     * @return
     */
    public static Date getYesterdayEnd() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 0);
        todayEnd.add(Calendar.DATE, -1);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        return todayEnd.getTime();
    }

    /**
     * 判断时间字符串的格式是否是 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static boolean isLongFormatStringDate(String time) {

        String regEx = "[0-9]{4}-[01][0-9]-[0123][0-9] [012][0-9]:[0-5][0-9]:[0-5][0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(time);
        boolean rs = matcher.matches();
        return rs;

    }

//    public static void main(String[] args) {
//        System.out.println(DateUtils.getLocalDateTimeLastSecond(DateUtils.getDateToLocalDateTime(new Date())));
//        System.out.println(DateUtils.getDateToLocalDateTime(DateUtils.getDateLastSecond(DateUtils.getYesteDayDate())));
//        int startDate = DateUtils.getSecondTimestamp(DateUtils.getYesterdayStart());
//        int endDate = DateUtils.getSecondTimestamp(DateUtils.getYesterdayEnd());
//        System.out.println(startDate);
//        System.out.println(endDate);
//    }
}
