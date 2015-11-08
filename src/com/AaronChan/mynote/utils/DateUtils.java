package com.AaronChan.mynote.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.bool;

public class DateUtils {
	public static final String DATE = "yyyy-MM-dd";
	public static final String TIME = "HH:mm";
	public static final String DETAIL_TIME = "HH:mm:ss";
	public static final String DATE_CHINESE = "MM��dd��";
	public static final String ALARM_TIME = "yyyy-MM-dd HH:mm";

	public static String detail2SimpleTime(String update_time) {
		int lastIndexOf = update_time.lastIndexOf(":");
		update_time = update_time.substring(0, lastIndexOf);
		return update_time;
	}
	public static boolean isInSameDay(long time1,long time2){
		boolean flag=false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date date1 = new Date(time1);
		Date date2 = new Date(time2);
		String dateFormat1 = sdf.format(date1);
		String dateFormat2 = sdf.format(date2);
		if (dateFormat1.equals(dateFormat2)) {
			flag=true;
		}
		return flag;
		
	}

	public static String getChineseDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_CHINESE);// ���Է�����޸����ڸ�ʽ
		String date = dateFormat.format(strtodate);
		return date;
	}

	public static String getDetailTime(long milliseconds ) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date(milliseconds);
		str = sdf.format(date);
		return str;
	}
	public static String getTime(long milliseconds){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = new Date(milliseconds);
		str = sdf.format(date);
		return str;

	}

	// ���ָ������������
	public static String getNextDay(int day) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DAY_OF_MONTH, day);// ��һ����
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// ��������һ�������
	public static String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// ��һ����
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// ���㵱�����һ��,�����ַ���
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.MONTH, 1);// ��һ���£���Ϊ���µ�1��
		lastDate.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * ����ʱ��֮�������
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// ת��Ϊ��׼ʱ��
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// ����¸��µ�һ�������
	public static String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// ��һ����
		lastDate.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * ����һ�����ڣ����������ڼ����ַ���
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// ��ת��Ϊʱ��
		Date date = DateUtils.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour�д�ľ������ڼ��ˣ��䷶Χ 1~7
		// 1=������ 7=����������������
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static Date strToDate(String strDate, String dateformat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	// ��ȡ����ʱ��
	public static String getDateTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// ���Է�����޸����ڸ�ʽ
		String date = dateFormat.format(now);
		return date;
	}

	// ��ñ������һ������� *
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// ���ĳ�����һ������� *
	public static String getYearEnd(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
		String years = dateFormat.format(date);
		return years + "-12-31";
	}
}
