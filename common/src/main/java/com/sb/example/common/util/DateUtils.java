package com.sb.example.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jack on 16/9/30.
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 一天的秒数
     */
    public static int DAY_SECONDS = 24 * 60 * 60;

    public final static String YEAR1970 = "1970-01-01 ";

    public static int getHourNumber(Date now) {
        if (null == now) {
            now = new Date();
        }
        return Integer.parseInt(formatDate(now, "yyyyMMddHH"));
    }


    /**
     * 获取分钟数
     *
     * @return
     */
    public static long getMinNumber(Date now) {
        if (null == now) {
            now = new Date();
        }
        String str = DateFormatUtils.format(now, "yyyyMMddHHmm");
        long min = Long.parseLong(str);
        return min;
    }

    /**
     * 获取秒数
     *
     * @param now
     * @return
     */
    public static int getSecondNumber(Date now) {
        if (null == now) {
            now = new Date();
        }
        String str = DateFormatUtils.format(now, "ss");
        int sec = Integer.parseInt(str);
        return sec;
    }


    /**
     * 两个时间相减得到的差（秒数）
     *
     * @param big   大的时间
     * @param small 小的时间
     * @return
     */
    public static Long DifferTime(Date big, Date small) {
        if (null == big || null == small) return 0L;
        Long diff = big.getTime() - small.getTime();
        return diff / 1000;
    }

    public static Long DifferMinutes(Date big, Date small) {
        if (null == big || null == small) return 0L;
        Long ret = DifferTime(big, small) / 60;
        return ret;
    }

    public static Long DifferHour(Date big, Date small) {
        if (null == big || null == small) return 0L;
        Long ret = DifferTime(big, small) / 3600;
        return ret;
    }

    public static int DifferDay(Date big, Date small) {
        if (null == big || null == small) return 0;
        int retInt = DifferTime(big, small).intValue() / DAY_SECONDS;
        return retInt;
    }

    public static int getCurrentTimePart() {
        String str = DateUtils.formatDate(new Date(), "HHmmssSSS");
        return Integer.valueOf(str);
    }

    private static String[] parsePatterns = {"yyyyMMddHHmmss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd", "yyyyMMdd", "yyyyMM", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    public static Date getDayStartTime(Date date) {
        String dt = getDate(date) + " 00:00:00";
        try {
            return parseDate(dt, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获得当前时间的的数字，精确到分钟：yyyyMMddHHmmss  格式
     *
     * @return 例如: 20180201101050
     */
    public static long getCurrentSecondsNumber() {
        long minutes = Long.parseLong(DateUtils.getDate("yyyyMMddHHmmss"));
        return minutes;
    }

    /**
     * 获得当前时间的的数字，精确到分钟：yyyyMMddHHmm  格式
     *
     * @return 例如: 201802011010
     */
    public static long getCurrentMinutesNumber() {
        long minutes = Long.parseLong(DateUtils.getDate("yyyyMMddHHmm"));
        return minutes;
    }

    /**
     * 获得当前日期的数字：yyyyMMdd  格式
     *
     * @return 例如: 20180201
     */
    public static int getCurrentDayNumber() {
        Integer day = Integer.parseInt(DateUtils.getDate("yyyyMMdd"));
        return day;
    }

    public static int getCurrentYearNumber() {
        Integer day = Integer.parseInt(DateUtils.getDate("yyyy"));
        return day;
    }

    /**
     * 获得当前日期的数字
     *
     * @return
     */
    public static int getCurrentMonthNumber() {
        Integer day = Integer.parseInt(DateUtils.getDate("yyyyMM"));
        return day;
    }

    /**
     * 获得给定日期精确到分钟的数字：yyyyMMddHHmm  格式
     *
     * @return 例如: 201802011010
     */
    public static long getMinutesNumber(Date date) {
        if (date == null) {
            return getCurrentDayNumber();
        }
        return Long.parseLong(formatDate(date, "yyyyMMddHHmm"));
    }

    /**
     * 获得给定日期的数字：yyyyMMdd  格式
     *
     * @return 例如: 20180201
     */
    public static int getDayNumber(Date date) {
        if (date == null) {
            return getCurrentDayNumber();
        }
        return Integer.parseInt(formatDate(date, "yyyyMMdd"));
    }

    public static int getMonthNumber(Date date) {
        if (date == null) {
            return getCurrentMonthNumber();
        }
        return Integer.parseInt(formatDate(date, "yyyyMM"));
    }

    public static int getYearNumber(Date date) {
        if (date == null) {
            return Integer.parseInt(formatDate(new Date(), "yyyy"));
        }
        return Integer.parseInt(formatDate(date, "yyyy"));
    }

    /**
     * 根据日期取得星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 该方法在linux环境下可能会得不到中文的 星期一、星期二。。。等，而是得到英文版的,Mon、Fri。。。之类的
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    @Deprecated
    public static String formatDate(Date date, Object... pattern) {
        if (null == date) return "";
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        if (null == date) return "";
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getTime(Date date) {
        return formatDate(date, "HH:mm:ss");
    }


    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到给定时间的年份字符串 格式（yyyy）
     *
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 得到给定时间的月份字符串 格式（MM）
     */
    public static String getMonth(Date date) {
        return formatDate(date, "MM");
    }

    /**
     * 得到给定时间的月份字符串 格式（yyyyMM）
     */
    public static String getLongMonth(Date date) {
        return formatDate(date, "yyyyMM");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到给定时间字符串 格式（dd）
     */
    public static String getDay(Date date) {
        return formatDate(date, "dd");
    }

    /**
     * 得到给定时间字符串 格式（yyyyMMdd）
     */
    public static String getLongDay(Date date) {
        return formatDate(date, "yyyyMMdd");
    }


    /**
     * 获得给定时间的小时数
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        return calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
    }

    public static int getMinute(Date date) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        return calendar.get(Calendar.MINUTE); // gets hour in 24h format
    }

    /**
     * 该方法在linux环境下可能会得不到中文的 星期一、星期二。。。等，而是得到英文版的,Mon、Fri。。。之类的
     * 得到当前星期字符串 格式（E）星期几
     * <p>
     * 推荐使用 getWeek(Date);方法
     */
    @Deprecated
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }


    public static Date getDateStart(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断字符串是否是日期
     *
     * @param timeString
     * @return
     */
    public static boolean isDate(String timeString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(timeString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 是否时间
     *
     * @param timeString
     * @return
     */
    public static boolean isTime(String timeString) {
        if (StringUtils.isEmpty(timeString)) {
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setLenient(false);
        try {
            format.parse(timeString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 格式化时间
     *
     * @param timestamp
     * @return
     */
    public static String dateFormat(Date timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timestamp);
    }

    /**
     * 获取系统时间Timestamp
     *
     * @return
     */
    public static Timestamp getSysTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 获取系统时间Date
     *
     * @return
     */
    public static Date getSysDate() {
        return new Date();
    }

    /**
     * 生成时间随机数
     *
     * @return
     */
    public static String getDateRandom() {
        String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return s;
    }


    public static String getFullLongTime(Date date) {
        if (null == date) {
            return "";
        }
        String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
        return s;
    }



   /* public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strMinute + "分钟 " + strSecond + "秒";
    }*/

    /**
     * 把秒转化为x天x小时x分钟x秒的格式
     *
     * @param ms
     * @return
     */
    public static String formatSecondsTime(Long ms) {
        // Integer ss = 1000;
        Integer ss = 1;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        //Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        /*if (milliSecond > 0) {
            sb.append(milliSecond + "毫秒");
        }*/
        return sb.toString();
    }

    /**
     * 将字符串格式yyyyMMdd的字符串转为日期，格式"yyyy-MM-dd"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     */
    public static String strToDateFormat(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        Date newDate = null;
        try {
            newDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(newDate);
    }

    /**
     * 字符串转成Date类型
     *
     * @param dateStr            时间字符串
     * @param defaultMonthFormat 待转换时间字符串格式，默认为：yyyyMM
     * @return
     */
    public static Date strToDate(String dateStr, String defaultMonthFormat) {
        SimpleDateFormat formatter = null;
        if (StringUtils.isBlank(defaultMonthFormat)) {
            formatter = new SimpleDateFormat("yyyyMM");
        } else {
            formatter = new SimpleDateFormat(defaultMonthFormat);
        }
        Date newDate = null;
        try {
            newDate = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 获取指定月份的最大天数
     *
     * @param date 日期
     * @return
     */
    public static int getMonthMaxDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定月份开始日期
     *
     * @param date 日期
     * @return
     */
    public static Date getMonthOfStartDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtils.getDayStartTime(calendar.getTime());
    }

    /**
     * 获取指定月份结束日期
     *
     * @param date 日期
     * @return
     */
    public static Date getMonthOfEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateUtils.getDateEnd(calendar.getTime());
    }

    /**
     * 比较两个时间字符串
     * @param time1 时间字符串1
     * @param time2 时间字符串2
     * @param timeFormatter 时间字符串格式  默认为 HH:mm:ss
     * @return 时间字串1 > 时间字符串2 则返回1，相反返回-1，返回0表示相等或异常
     */
    public static int compareTime(String time1, String time2, String timeFormatter) {
        SimpleDateFormat sdf = null;
        if (StringUtils.isBlank(timeFormatter)) {
            sdf = new SimpleDateFormat("HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat(timeFormatter);
        }
        try {
            Date parseTime1 = sdf.parse(time1);
            Date parseTime2 = sdf.parse(time2);
            if (parseTime1.after(parseTime2)) {
                return 1;
            } else if (parseTime1.before(parseTime2)) {
                return -1;
            } else {
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        System.out.println(getMonthOfStartDay(DateUtils.getSysDate()));
        System.out.println(getMonthOfEndDay(DateUtils.getSysDate()));
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));

        String ddd = strToDateFormat("20190201") + " 00:00:00";
        System.out.println("ddd = " + ddd);
        System.out.println("dddd = " + parseDate(ddd));

        Date now = new Date();
        Date date = addMinutes(now, -22 * 60);
        System.out.println(now);
        System.out.println(getDateStart(date));

        System.out.println(DifferDay(now, getDateStart(date)));

        System.out.println(String.format("%s期数最后一期截止时间(%s)与参数配置的(%s)不一致，请手动调整!!!", "山东11x5", "22:55:00", "22:55:30"));
//
//        System.out.println(DifferTime(DateUtils.addMinutes(now, 3), now));
//        System.out.println(formatSecondsTime(15878L));
//
        System.out.println(isTime("19:20:20"));

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date xx = format.parse("00:01:00");
        System.out.println(getTime(addMinutes(xx, 1)));


        System.out.println(String.format("当前时间是:%d", getCurrentMinutesNumber()));

        String currentTIme = DateUtils.getTime(new Date());
        System.out.println(compareTime(currentTIme, "15:00:00", null));
        System.out.println(compareTime(currentTIme, "12:00:00", null));
    }

    /**
     * jdk8时间类型转换
     */
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
