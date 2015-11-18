package com.AaronChan.mynote.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.bool;
import android.text.format.DateUtils;

public class MyDateUtils {
	public static final String DETAIL_DATE_TIME = "yyyy-MM-dd HH:mm";
	public static final String DATE_WEEK = "MM-dd EEEE";
	public static final String SIMPLE_TIME="HH:mm";
	// ��ȡ��ʱ�����ַ���
	public static String getDateTime(long milliseconds, String dateformat) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date date = new Date(milliseconds);
		str = sdf.format(date);
		return str;

	}

	// ��ȡ����ʱ�����������
	public static long getDateRange(long start, long end) {
		long dateRang = -1;
		dateRang = (end - start) / (24 * 60 * 60 * 1000);

		return dateRang;

	}

	

	// ���ַ�������ʱ��ת��ΪDate
	public static Date strToDate(String strDate, String dateformat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	// ��ȡʱ����
	public static String getRelativeTimeSpanString(long alarm_time) {
		String str = "";
		CharSequence relativeTimeSpanString = android.text.format.DateUtils.getRelativeTimeSpanString(alarm_time,
				System.currentTimeMillis(), android.text.format.DateUtils.MINUTE_IN_MILLIS);
		str = relativeTimeSpanString.toString();

		return str;

	}
}
