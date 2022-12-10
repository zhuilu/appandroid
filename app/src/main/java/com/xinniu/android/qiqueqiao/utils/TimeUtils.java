package com.xinniu.android.qiqueqiao.utils;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * Created by lzq on 2017/12/18.
 */

public class TimeUtils {
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());


    public static String getStatus(long time) {
        Logger.i("lzq", "time  " + time);
        Logger.i("lzq", "currentTimeMillis  " + System.currentTimeMillis());
        long dValue = System.currentTimeMillis() - time;
        Logger.i("lzq", "dValue  " + dValue);
        int dTime = (int) (dValue / 3600000L);
        Logger.i("lzq", "dTime  " + dTime);
        if (dTime <= 3) {
            return "刚刚活跃";
        }
        if (dTime <= 24 && dTime > 3) {
            return "今日活跃";
        }
        if (dTime <= 72 && dTime > 24) {
            return "1天前活跃";
        }
        if (dTime > 72) {
            return "3天前活跃";
        }
        return "刚刚活跃";
    }

    /**
     *处理时间戳 满足规则
     *  1分钟以内：刚刚
     *  1小时以内：XX分钟前
     *  1天以内：XX小时前
     *  1个月内：XX天前
     *  超过1个月：yyyy-MM-dd
     */
    public static String getTimeStateNew(String long_time) {
        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.valueOf(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    System.out.println("传递过来的时间:"+format.format(time));
//    System.out.println("现在的时间:"+format.format(now));
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
//    System.out.println("天数:"+time_conver/day_conver);
        if ((time_conver / day_conver) < 3) {
            temp_conver = time_conver / day_conver;
            if (temp_conver <= 2 && temp_conver >= 1) {
                return temp_conver + "天前";
            } else {
                temp_conver = (time_conver / hour_conver);
                if (temp_conver >= 1) {
                    return temp_conver + "小时前";
                } else {
                    temp_conver = (time_conver / min_conver);
                    if (temp_conver >= 1) {
                        return temp_conver + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } else {
            return format.format(time);
        }
    }


    /**
     *
     */
    public static String getTimeStateNewTwo(String long_time) {
        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.valueOf(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    System.out.println("传递过来的时间:"+format.format(time));
//    System.out.println("现在的时间:"+format.format(now));
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
//    System.out.println("天数:"+time_conver/day_conver);
        if ((time_conver / day_conver) <= 30) {//超过1个月：yyyy-MM-dd
            temp_conver = time_conver / day_conver;
            if (temp_conver < 1) {
                return "刚刚";
            } else {
                return temp_conver + "天前";
            }
        } else {
            return format.format(time);
        }
    }


    /**
     * 将时间戳转为时间字符串
     * <p>格式为 yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    @SuppressLint("StringFormatInvalid")
    public static String millis2String(final long timeMillis) {
        if (timeMillis == 0L) {
            return "";
        } else {
            String result = null;
            int targetDay = (int) (timeMillis / 86400000L);
            int nowDay = (int) (System.currentTimeMillis() / 86400000L);
            if (targetDay == nowDay) {
                result = fromatDate(timeMillis, "HH:mm");
            } else if (targetDay + 1 == nowDay) {
//                Context context = RongContext.getInstance().getBaseContext();
//                String formatString = context.getResources().getString(io.rong.imkit.R.string.rc_yesterday_format);
//                result = String.format(formatString, new Object[]{fromatDate(timeMillis, "HH:mm")});
            } else {
                result = fromatDate(timeMillis, "yyyy-MM-dd");
            }

            return result;
        }
//        return millis2String(millis, DEFAULT_FORMAT);

    }

    @SuppressLint("StringFormatInvalid")
    public static String millis2Stringx(final long timeMillis) {
        if (timeMillis == 0L) {
            return "";
        } else {
            String result = null;
            int targetDay = (int) (timeMillis / 86400000L);
            int nowDay = (int) (System.currentTimeMillis() / 86400000L);
            if (targetDay == nowDay) {
                result = "今天  " + fromatDate(timeMillis, "HH:mm");
            } else if (targetDay + 1 == nowDay) {
                result = "昨天  " + fromatDate(timeMillis, "HH:mm");
            } else {
                result = fromatDate(timeMillis, "yyyy-MM-dd  HH:mm");
            }

            return result;
        }
//        return millis2String(millis, DEFAULT_FORMAT);

    }

    @SuppressLint("StringFormatInvalid")
    public static String millis2Stringt(final long timeMillis) {
        if (timeMillis == 0L) {
            return "";
        } else {
            String result = null;
            int targetDay = (int) (timeMillis / 86400000L);
            int nowDay = (int) (System.currentTimeMillis() / 86400000L);
            if (targetDay == nowDay) {
                result = "今天  " + fromatDate(timeMillis, "HH:mm");
            } else if (targetDay + 1 == nowDay) {
                result = "昨天  " + fromatDate(timeMillis, "HH:mm");
            } else {
                result = fromatDate(timeMillis, "MM-dd  HH:mm");
            }

            return result;
        }
//        return millis2String(millis, DEFAULT_FORMAT);

    }


    public static String time2Weekt(long time) {
        if (time == 0L) {
            return "";
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(time));
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            String weekString;
            switch (week) {
                case Calendar.SUNDAY:
                    weekString = "周日";
                    break;
                case Calendar.MONDAY:
                    weekString = "周一";
                    break;
                case Calendar.TUESDAY:
                    weekString = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    weekString = "周三";
                    break;
                case Calendar.THURSDAY:
                    weekString = "周四";
                    break;
                case Calendar.FRIDAY:
                    weekString = "周五";
                    break;
                default:
                    weekString = "周六";
                    break;
            }


            return weekString;
        }
    }

    public static String time2monthday(long time) {
        if (time == 0L) {
            return "";
        } else {
            String times = fromatDate(time, "MM/dd");

            return times;
        }
    }

    public static String time2ActTime(long time) {
        if (time == 0L) {
            return "";
        } else {
            String times = fromatDate(time, "yyyy-MM-dd HH:mm");

            return times;
        }
    }

    public static String time22ActTime(long time) {
        if (time == 0L) {
            return "";
        } else {
            String times = fromatDate(time, "yyyy-MM-dd");

            return times;
        }
    }




    public static String time2Hourmm(long time) {
        if (time == 0L) {
            return "";
        } else {
            String times = fromatDate(time, "HH:mm");

            return times;
        }
    }


    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(long date1, long date2) {
        int days = (int) ((date1 - date2) / (1000 * 3600 * 24));
        return days;
    }


    /**
     * 将时间戳转为时间字符串
     * <p>格式为 format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    public static String fromatDate(long timeMillis, String fromat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromat);
        return sdf.format(new Date(timeMillis));
    }
}
