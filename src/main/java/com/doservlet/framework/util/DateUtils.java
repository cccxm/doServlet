package com.doservlet.framework.util;

import static com.doservlet.framework.util.DateType.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 会的当前时间所在区间，今天，昨天，前天，本月，本年，以前
	 * 
	 * @param time
	 * @return
	 */
	public static DateType duration(long time) {
		DateType duration = BEFORE_THIS_YEAR;
		if (time >= getDate(DAY).getTime()) {
			duration = TODAY;
		} else if (time >= getDate(DAY).getTime() - getLongTime(DAY)) {
			duration = YESTERDAY;
		} else if (time >= getDate(DAY).getTime() - getLongTime(DAY) * 2) {
			duration = BEFORE_YESTERDAY;
		} else if (time >= getDate(MONTH).getTime()) {
			duration = THIS_MONTH;
		} else if (time >= getDate(YEAR).getTime()) {
			duration = THIS_YEAR;
		}
		return duration;
	}

	/**
	 * 获得当前日期对象
	 * 
	 * @param timeType
	 * @return
	 */
	public static Date getDate(DateType timeType) {
		Date date = null;
		try {
			switch (timeType) {
			case YEAR:
				date = getYear();
				break;
			case MONTH:
				date = getMonth();
				break;
			case DAY:
				date = getDay();
				break;
			default:
				date = new Date();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

	public static Date getDay() throws ParseException {
		return DateFormat.getDateInstance().parse(
				new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	}

	/**
	 * 以现在为标准格式化日期（ 昨天14:15）
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatString(Date date) {
		return getFormatString(date.getTime());
	}

	/**
	 * 以现在为标准格式化日期（ 昨天14:15）
	 * 
	 * @param time
	 * @return
	 */
	public static String getFormatString(long time) {
		String format = null;
		Date date = new Date(time);
		switch (duration(time)) {
		case TODAY:
			format = getFormatString(date, "今天HH:mm");
			break;
		case YESTERDAY:
			format = getFormatString(date, "昨天HH:mm");
			break;
		case BEFORE_YESTERDAY:
			format = getFormatString(date, "前天HH:mm");
			break;
		case THIS_MONTH:
		case THIS_YEAR:
			format = getFormatString(date, "MM-dd HH:mm");
			break;
		default:
			format = getFormatString(date, "yyyy-MM-dd HH:mm");
			break;
		}
		return format;
	}

	/**
	 * 自定义格式化方式输出当前日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormatString(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获得字符串所表示的时间
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String time, String pattern)
			throws ParseException {
		return new SimpleDateFormat(pattern).parse(time);
	}

	/**
	 * 以2008-08-08 20:00:00格式输出时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getSimpleDateString(Date date) {
		return getFormatString(date, "yyyy-MM-dd HH-mm-ss");
	}

	/**
	 * 以2008-08-08 20:00:00格式输出时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getSimpleDateString(long date) {
		return getSimpleDateString(new Date(date));
	}

	/**
	 * 获得一个时间段的毫秒长度
	 * 
	 * @param type
	 * @return
	 */
	public static long getLongTime(DateType type) {
		long time = 0;
		switch (type) {
		case DAY:
			time = 1000l * 60 * 60 * 24;
			break;
		case MONTH:
			time = 1000l * 60 * 60 * 24 * 30;
			break;
		case YEAR:
			time = 1000l * 60 * 60 * 24 * 365;
			break;
		default:
			break;
		}

		return time;
	}

	public static Date getMonth() throws ParseException {
		return DateFormat.getDateInstance().parse(
				new SimpleDateFormat("yyyy-MM").format(new Date()) + "-01");
	}

	public static Date getYear() throws ParseException {
		return DateFormat.getDateInstance().parse(
				new SimpleDateFormat("yyyy").format(new Date()) + "-01-01");
	}

	/**
	 * 获得年龄
	 * 
	 * @param time
	 * @return
	 */
	public static int getAge(long time) {
		Date birth = new Date(time);
		Date now = new Date();
		int age = getBirthYear(now) - getBirthYear(birth);
		if ((getBirthMonth(birth) > getBirthMonth(now))
				|| ((getBirthMonth(birth) == getBirthMonth(now)) && (getBirthDay(birth) > getBirthDay(now)))) {
			age--;
		}
		return age;
	}

	/**
	 * 获得出生年份的int值
	 * 
	 * @param time
	 * @return
	 */
	public static int getBirthYear(Date time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		return Integer.parseInt(simpleDateFormat.format(time));
	}

	/**
	 * 获得出生月份的int值
	 * 
	 * @param time
	 * @return
	 */
	public static int getBirthMonth(Date time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
		return Integer.parseInt(simpleDateFormat.format(time));
	}

	/**
	 * 获得出生日期的int值
	 * 
	 * @param time
	 * @return
	 */
	public static int getBirthDay(Date time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
		return Integer.parseInt(simpleDateFormat.format(time));
	}
}
