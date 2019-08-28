package com.mmall.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" , "yyyyMMddHHmmss"};

	
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * @author yuanhailong
	 * @see
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date date,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date timestampToDate(Timestamp timestamp){
		 Date date = new Date(); 
		 date=timestamp;
		 return date;
	}
	
	public static String getDateBy(String a){
		String year=a.substring(0,4);
		String moth=a.substring(4,6);
		String day=a.substring(6,8);
		String hours=a.substring(8,10);
		String minutes=a.substring(10,12);
		String seconds=a.substring(12,14);
		String time=year+"-"+moth+"-"+day+" "+hours+":"+minutes+":"+seconds;
		return time;
	}
	
	public static String getDateBy(){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=format.format(date); 
		return time;
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
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parseDate(String str, String[] parsePatterns)
			throws ParseException {
		if ((str == null) || (parsePatterns == null)) {
			throw new IllegalArgumentException(
					"Date and Patterns must not be null");
		}

		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; ++i) {
			if (i == 0)
				parser = new SimpleDateFormat(parsePatterns[0]);
			else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(str, pos);
			if ((date != null) && (pos.getIndex() == str.length())) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + str, -1);
	}

	/**
     * 将时间字符串转换为10位时间戳  年月日 时分秒
     */
	public static String dateToStamp(String s) throws ParseException{
		String res;
		SimpleDateFormat simpleDateFormat = null;
		if(s.length()==10) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		} else if(s.length() == 16) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if(s.length() > 10) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime()/1000;
		res = String.valueOf(ts);
		return res;
	}

	/**
     * 将时间字符串转换为10位时间戳
     */
	public static int dateToIntStamp(String s) throws ParseException{
		String res = dateToStamp(s);
		return Integer.parseInt(res);
	}

	/**
	 * 将时间戳转换为日期时间
	 * @param s
	 * @param format 日期格式，如： "yyyy-MM-dd HH:mm:ss"  "yyyy-MM-dd"
	 * @return
	 */
	public static String stampToDate(String s, String format){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		long lt = 0L ;
		if(s.length() == 10) {
			lt = new Long(s)*1000;
		} else if(s.length() == 13) {
			lt = new Long(s);
		}

		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 将时间戳转为时间字符串
	 *
	 * @param time
	 * @return
	 */
	public static String timestamp2DateStr(Integer time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(Long.valueOf(time+"000")));
		return date;
	}


}
