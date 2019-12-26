package com.llf.ssm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/***
 * 这是一个时间帮助类
 * @author G
 *  
 */
public class DateHelp {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();
	public final static char[] upper = "〇一二三四五六七八九十".toCharArray();
	
	/***
	 * 获取当前时间的指定格式的字符串
	 * @param format
	 * @return
	 */
	public static String getCurrTime(String format){
		Date date = new Date(); 
		dateFormat.applyPattern(format);
		return dateFormat.format(date);
	}
	
	/***
	 * 当前毫秒数
	 * @return
	 */
	public static String getCurrMillisecond(){
		
		return String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * 字符串转化为日期格式
	 */
	public static Date StringForDate(String dateString) throws ParseException{
	    return dateFormat.parse(dateString);
	}
	
	/**
	 * 对比两个日期相差天数
	 * @param fDate
	 * @param oDate
	 * @return
	 * @throws ParseException 
	 */
	public static int daysOfTwo(String date1, String date2) throws ParseException {
		int days = 0;
		Date fDate = StringForDate(date1);
		Date oDate = StringForDate(date2);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        days = day2 - day1;
        return days;
	}
	
	
	
	public static String getTime(String format,Date date){
		dateFormat.applyPattern(format);
		return dateFormat.format(date);
	}
	
	public static String doTime(String date,int num,String format){
		try {
			dateFormat.applyPattern(format);
			Date d = dateFormat.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, num);
			return dateFormat.format(c.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static int compareDate(String date1,String date2,String format){
		dateFormat.applyPattern(format);
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = dateFormat.parse(date1);
			    d2 = dateFormat.parse(date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return d1.compareTo(d2);
	}
	
	/**
	 * 检测当前时间与timestamp间隔是否大于30分钟
	 * @param time
	 * @return true:可用连接 false:过期连接
	 */
	public static boolean compareDate(String time){
		long time1 = Long.parseLong(time);
		long time2 = System.currentTimeMillis();
		long time3 = time2-time1;
		if(time3/1000/60<=30){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取N天前/后日期
	 * @throws ParseException 
	 */
	public static String getCalculateDate(Integer n) throws ParseException {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String oldDate = getCurrTime("yyyy-MM-dd");
		Date oldFormatDate = dateFormat.parse(oldDate);
		cal.setTime(oldFormatDate);
		cal.add(Calendar.DAY_OF_MONTH, n);
		date = cal.getTime();
		String newDate = dateFormat.format(date);
		return newDate;
	}
	
	/**
	 * 获取星期
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate() {
		Date date = new Date(); 
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	/**
	 * 获取本月天数
	 * @return
	 */
	public static int getDayOfMonth(){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		int day = cal.getActualMaximum(Calendar.DATE);
		return day;
	}
	
    /**
     * 根据小写数字格式的日期转换成大写格式的日期
     * @param date
     * @return
     */
    public static String getUpperDate(String date) {
        //支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
        if(date == null) return null;
        //非数字的都去掉
        date = date.replaceAll("\\D", "");
        if(date.length() != 8) return null;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<4;i++) {//年
            sb.append(upper[Integer.parseInt(date.substring(i, i+1))]);
        }
        sb.append("年");//拼接年
        int month = Integer.parseInt(date.substring(4, 6));
        if(month <= 10) {
            sb.append(upper[month]);
        } else {
            sb.append("十").append(upper[month%10]);
        }
        sb.append("月");//拼接月

        int day = Integer.parseInt(date.substring(6));
        if (day <= 10) {
            sb.append(upper[day]);
        } else if(day < 20) {
            sb.append("十").append(upper[day % 10]);
        } else {
            sb.append(upper[day / 10]).append("十");
            int tmp = day % 10;
            if (tmp != 0) sb.append(upper[tmp]);
        }
        sb.append("日");//拼接日
        return sb.toString();
    }
}
