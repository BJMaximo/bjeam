package wp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjDate.java
 * §File Path: wp.util.BjDate
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2017/12/14
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjDate {
    // 默认时间格式
    static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static final long HOUR_PERIOD = 60 * 60;
    static final long DAY_PERIOD = HOUR_PERIOD * 24;
    static SimpleDateFormat simpleFormat = null;
    static final String s = "秒";
    static final String m = "分";
    static final String h = "小时";
    static final String d = "天";
    static final String p = " ";
    static long day = 0;
    static long hour = 0;
    static long min = 0;
    static long sec = 0;
    static long DAY_TIMES = 0;
    static long HOUR_TIMES = 0;


    static {
        simpleFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        DAY_TIMES = 24 * 60 * 60 * 1000;
        HOUR_TIMES = 60 * 60 * 1000;

    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static Date getDateTimeFormat(String date) {
        try {
            return simpleFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间差（天）
     * 计算方式：第二个时间减除第一个时间
     *
     * @param startTime 第一个时间
     * @param endTimes  第二个时间
     * @return
     */
    public static int differentDays(String startTime, String endTimes) {
        Date startDate = getDateTimeFormat(startTime);
        Date endDate = getDateTimeFormat(endTimes);
        Calendar calS = Calendar.getInstance();
        calS.setTime(startDate);
        Calendar calE = Calendar.getInstance();
        calE.setTime(endDate);
        int dayS = calS.get(Calendar.DAY_OF_YEAR);
        int dayE = calE.get(Calendar.DAY_OF_YEAR);
        int yearS = calS.get(Calendar.YEAR);
        int yearE = calE.get(Calendar.YEAR);
        if (yearS == yearE)   //同一年
        {
            int timeDistance = 0;
            for (int i = yearS; i < yearE; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (dayE - dayS);
        } else    //不同年
        {
            return dayE - dayS;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔（天）
     *
     * @param startTime
     * @param endTimes
     * @return
     */
    public static int differentDaysByMillisecond(String startTime, String endTimes) {
        Date startDate = getDateTimeFormat(startTime);
        Date endDate = getDateTimeFormat(endTimes);
        int days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public String diffTimeHourAndMins(String startTime, String endTimes) {
        Date startDate = getDateTimeFormat(startTime);
        Date endDate = getDateTimeFormat(endTimes);
        long st = startDate.getTime();
        long et = endDate.getTime();
        return toDifferenceDate(et - st);
    }

    public static String diffTimeHourAndMins(Date startDate, Date endDate) {
        long st = startDate.getTime();
        long et = endDate.getTime();
        return toDifferenceDate(et - st);
    }

    public static long getLongTims(Date startTimes, Date entTims) {
        long st = startTimes.getTime();
        long et = entTims.getTime();
        return et - st;
    }

    public static long getMins(long times) {
        return times / (1000 * 60);
    }

    public static long getMins(Date startTimes, Date entTims) {
        long timeMin = 0;
        long st = startTimes.getTime();
        long et = entTims.getTime();
        timeMin = (et - st) / (1000 * 60);
        return timeMin;
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getTimeMin(String startDate, String endDate) {
        long timeMin = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
            long stratTimeL = sdf.parse(startDate).getTime();
            long endTimeL = sdf.parse(endDate).getTime();
            timeMin = (endTimeL - stratTimeL) / (1000 * 60);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeMin;
    }

    /**
     * long 转换为多少小时多少分多少秒
     *
     * @param times
     * @return
     */
    public static String getDistanceTimes(long times) {
        final StringBuilder timeVal = new StringBuilder();
        day = times / DAY_TIMES;
        hour = (times / (60 * 60 * 1000) - day * 24);
        min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) {
            timeVal.append(day).append(d).append(p).append(hour).append(h).append(p).append(min).append(m).append(p).append(sec).append(s);
        } else if (day == 0 && hour != 0) {
            timeVal.append(hour).append(h).append(p).append(min).append(m).append(p).append(sec).append(s);
        } else if (day == 0 && hour == 0 && min != 0) {
            timeVal.append(min).append(m).append(p).append(sec).append(s);
        } else if (day == 0 && hour == 0 && min == 0 && sec != 0) {
            timeVal.append(sec).append(s);
        } else {
            timeVal.append("0");
        }
        return timeVal.toString();
    }

    /**
     * 把dur转为具体化的时间,比如1小时23分
     *
     * @param dur
     * @return
     */
    public static String toDifferenceDate(long dur) {
        dur = dur / 1000;

        final StringBuilder sb = new StringBuilder();
        if (dur < 60) {
            sb.append(dur).append(p).append(s);
        } else if (dur < HOUR_PERIOD) {
            final long ii = dur / 60;
            final long jj = dur % 60;
            sb.append(ii).append(p).append(m).append(p).append(jj).append(p)
                    .append(s);
        } else if (dur < DAY_PERIOD) {
            final long ii = dur / HOUR_PERIOD;
            final long jj = dur % HOUR_PERIOD / 60;
            sb.append(ii).append(p).append(h).append(p).append(jj).append(p)
                    .append(m);
        } else {
            final long ii = dur / DAY_PERIOD;
            final long jj = dur % DAY_PERIOD / HOUR_PERIOD;
            sb.append(ii).append(p).append(d).append(p).append(jj).append(p)
                    .append(h);
        }
        return sb.toString();
    }

    /**
     * 根据时间比较转换为时间long
     *
     * @param valTimes
     * @return
     */
    public long stringToLong(String valTimes) {
        if (null != valTimes && !"".equals(valTimes)) {
            // valTimes=valTimes.replaceAll(" ","").replace("")
        }
        return 0;
    }

}
