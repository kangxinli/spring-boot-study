package com.sample.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

/**
 * 日期工具类
 *
 */
public class DateUtil {
	
	public static final String FMT_DATE_YEAR  = "yyyy";
    public static final String FMT_DATE_MONTH = "yyyy-MM";
    public static final String FMT_DATE_DAY   = "yyyy-MM-dd";
    public static final String FMT_DATE_TIME_HOUR   = "yyyy-MM-dd HH";
    public static final String FMT_DATE_TIME_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FMT_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_TIME_MILLISECOND = "yyyy-MM-dd HH:mm:ss SSS";
    
    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     * 
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    // System.out.println("put new sdf of pattern " + pattern + " to map");

                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {

                        @Override
                        protected SimpleDateFormat initialValue() {
                            // System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }

        return tl.get();
    }
    
    /**
     * 将 String 类型转为 Date 类型
     * @param sourceString
     * @param formatModle
     * @return
     */
    public static Date stringToDate(String sourceString, String formatModle) {
		if (sourceString == null) {
			return null;
		}
		try {
			return getSdf(formatModle).parse(sourceString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    /**
     * 将 Date 类型转为 String 类型
     * @param date
     * @param formatModle
     * @return
     */
    public static String dateToString(Date date, String formatModle) {
		if (date != null) {
			return getSdf(formatModle).format(date);
		} else {
			return null;
		}
	}

    public static Date parse(String date) {
        Date returnValue = null;
        try {
            SimpleDateFormat format = null;
            if (date.matches("^[0-9]{4}$")) {
                format = getSdf(FMT_DATE_YEAR);
            } else if (date.matches("^[0-9]{4}-[0-9]{2}$")
            		|| date.matches("^[0-9]{4}-[0-9]{1}$")) {
                format = getSdf(FMT_DATE_MONTH);
            } else if (date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
            		|| date.matches("^[0-9]{4}-[0-9]{1}-[0-9]{1}$")) {
                format = getSdf(FMT_DATE_DAY);
            } else if (date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}$")) {
                format = getSdf(FMT_DATE_TIME_HOUR);
            } else if (date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}$")) {
                format = getSdf(FMT_DATE_TIME_MINUTE);
            } else if (date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) {
                format = getSdf(FMT_DATE_TIME_SECOND);
            } else if (date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} [0-9]{3}$")) {
                format = getSdf(FMT_DATE_TIME_MILLISECOND);
            }
            if (format != null) {
                returnValue = format.parse(date);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }
	
	public static void main(String[] args) {
	   	Date date1 = now();
	   	Date date2 = addMinutes(date1, 1);
		if(date2.compareTo(date1) >0)
			System.out.println("date2大");
	}
	
	/**
	 * 获取系统当前时间
	 */
	public static Date now() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 计算时间差 (时间单位,开始时间,结束时间)
	 * @param unit 时间单位 s - 秒,m - 分,h - 时,d - 天 ,M - 月 y - 年
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static long howLong(String unit,Date begin,Date end) {
		long ltime = begin.getTime() - end.getTime() < 0 ? end.getTime() - begin.getTime() : begin.getTime() - end.getTime();
		if (unit.equals("s")) {
			return ltime / 1000;// 返回秒
		} else if (unit.equals("m")) {
			return ltime / 60000;// 返回分钟
		} else if (unit.equals("h")) {
			return ltime / 3600000;// 返回小时
		} else if (unit.equals("d")) {
			return ltime / 86400000;// 返回天数
		} else if (unit.equals("y")) {
			long res = ltime / 86400000;
			return res / 365;
		} else if (unit.equals("M")) {
			long res = ltime / 86400000;
			return res / 30;
		} else {
			return 0;
		}
	}
	
	/**
	 * 输出时间转换计算时间差
	 * 
	 * @param convert_dt
	 *            转换的日期
	 * @param outString
	 *            输出的消息体
	 * @param flag
	 *            消息体标示
	 * @return
	 */
	public static String visitDateState(Date convert_dt) {
		// 用户最新操作信息 如刚刚操作
		if (convert_dt == null) {
			return "刚刚";
		}
		Calendar c_now = Calendar.getInstance();
		Calendar dt = Calendar.getInstance();
		dt.setTime(convert_dt);
		long c_min = c_now.getTimeInMillis() - dt.getTimeInMillis();
		long c_check_min = c_min / (60 * 1000);
		long c_check_hour = c_min / (60 * 60 * 1000);
		long c_check = c_min / (24 * 60 * 60 * 1000);
		if (c_check_min == 0){
			return "刚刚";
		} else if (c_check_min > 0 && c_check_min <= 60) {
			return c_check_min + "分钟前";
		} else if (c_check_hour > 0 && c_check_hour <= 24) {
			return c_check_hour + "小时前";
		} else {
			return c_check + "天前";
		}
	}
	
	/**
	 * 时间戳转换为date
	 * @param timestampString 位数为13位 ，不满13为*倍数
	 * @return
	 */
	public static String timeToDate(String timestampString){  
		Long timestamp = Long.parseLong(timestampString);  
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timestamp));  
		return date;  
	} 
	
	/**
	 * 返回周几
	 * @param date
	 * @return
	 */
	public static String getWeekDayString(Date date) {
		String weekString = "";
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		Calendar calendar = Calendar.getInstance();
		if (date == null)
			date = new Date();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		weekString = dayNames[dayOfWeek - 1];
		return weekString;

	}
	
	/**
	 * 增加天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date, int day) {
		return DateUtils.addDays(date, day);
	}

	/**
	 * 增加月
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date addMonths(Date date, int months) {
		return DateUtils.addMonths(date, months);
	}

	/**
	 * 增加年
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date addYears(Date date, int years) {
		return DateUtils.addYears(date, years);
	}

	/**
	 * 增加小时
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date, int hours) {
		return DateUtils.addHours(date, hours);
	}

	/**
	 * 增加分钟
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutes(Date date, int minutes) {
		return DateUtils.addMinutes(date, minutes);
	}
	
	/**
	 * 获取季度
	 * @param date
	 * @return 1 第一季度 2 第二季度 3 第三季度 4 第四季度 
	 */
    public static int getQuarter(Date date) {  
        int season = 0;  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
	        case Calendar.JANUARY:  
	        case Calendar.FEBRUARY:  
	        case Calendar.MARCH:  
	            season = 1;  
	            break;  
	        case Calendar.APRIL:  
	        case Calendar.MAY:  
	        case Calendar.JUNE:  
	            season = 2;  
	            break;  
	        case Calendar.JULY:  
	        case Calendar.AUGUST:  
	        case Calendar.SEPTEMBER:  
	            season = 3;  
	            break;  
	        case Calendar.OCTOBER:  
	        case Calendar.NOVEMBER:  
	        case Calendar.DECEMBER:  
	            season = 4;  
	            break;  
	        default:  
	            break;  
        }  
        return season;  
    }
    
    /**
     * 获取季度开始结束日期
     * @param year 年份
     * @param quarter  1:第一季度 ,2:第二季度,3:第三季度 ,4:第四季度
     * @return  开始日期，结束日期
     */
     public static Date[] getQuarterDate(String year,int quarter) {
     	SimpleDateFormat sdf= new SimpleDateFormat("yyyy");
     	Date date = null;
 		try {
 			date = sdf.parse(year);
 		} catch (ParseException e) {
 			e.printStackTrace();
 		}
     	Calendar calendar = Calendar.getInstance();
     	calendar.setTime(date);
 	    Date[] dates = new Date[2];
 	    switch (quarter) {
 		    case 1:
 			    calendar.set(Calendar.MONTH, Calendar.JANUARY);
 			    calendar.set(Calendar.DAY_OF_MONTH, 1);
 			    dates[0] = calendar.getTime();
 			    calendar.set(Calendar.MONTH, Calendar.MARCH);
 			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
 			    dates[1] = calendar.getTime();
 		    break;
 		    case 2:
 			    calendar.set(Calendar.MONTH, Calendar.APRIL);
 			    calendar.set(Calendar.DAY_OF_MONTH, 1);
 			    dates[0] = calendar.getTime();
 			    calendar.set(Calendar.MONTH, Calendar.JUNE);
 			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
 			    dates[1] = calendar.getTime();
 		    break;
 		    case 3:
 			    calendar.set(Calendar.MONTH, Calendar.JULY);
 			    calendar.set(Calendar.DAY_OF_MONTH, 1);
 			    dates[0] = calendar.getTime();
 			    calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
 			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
 			    dates[1] = calendar.getTime();
 		    break;
 		    case 4:
 			    calendar.set(Calendar.MONTH, Calendar.OCTOBER);
 			    calendar.set(Calendar.DAY_OF_MONTH, 1);
 			    dates[0] = calendar.getTime();
 			    calendar.set(Calendar.MONTH, Calendar.DECEMBER);
 			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
 			    dates[1] = calendar.getTime();
 		    break;
 		    default:
 		    break;
 	    }
 	    return dates;
     }
}

