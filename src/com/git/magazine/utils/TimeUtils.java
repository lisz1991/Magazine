package com.git.magazine.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Use:日期工具类
 * @Date:2014-2-25
 * @Time:下午3:50:56
 */
public class TimeUtils {

	public static SimpleDateFormat day1 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat day2 = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat day3 = new SimpleDateFormat("yyyy年MM月dd日");
	public static SimpleDateFormat time1 = new SimpleDateFormat(
			"yyyy-MM-dd HH-mm-ss");
	public static SimpleDateFormat time2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private TimeUtils() {

	}

	/**
	 * 获取系统当前时间，默认yyyy-MM-dd HH:mm:ss格式
	 */
	public static String getTime() {
		return time2.format(new Date());
	}

	/**
	 * 获取系统当前时间，指定当前类中格式
	 */
	public static String getTime(SimpleDateFormat formatType) {
		return formatType.format(new Date());
	}

	/**
	 * 格式化日期(精确到秒)，yyyy-MM-dd HH-mm-ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateFile(Date date) {
		return time1.format(date);
	}

	/**
	 * 格式化日期(精确到秒)，yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateSecond(Date date) {
		return time2.format(date);
	}

	/**
	 * 格式化日期(精确到天)，yyyy-MM-dd 格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateDay(Date date) {
		return day1.format(date);
	}

	/**
	 * 格式化日期(精确到天)，yyyy/MM/dd 格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateExcel(Date date) {
		return day2.format(date);
	}

	/**
	 * 格式化日期(精确到天)，yyyy年MM月dd日 格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateDetail(Date date) {
		return day3.format(date);
	}

	/**
	 * 将字符日期转换成Date，yyyy-MM-dd 格式
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date parseToDate(String date) throws Exception {
		return day1.parse(date);
	}

}
