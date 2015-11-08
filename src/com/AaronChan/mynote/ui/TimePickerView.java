package com.AaronChan.mynote.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.AaronChan.mynote.R;
import com.AaronChan.mynote.utils.DateUtils;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TimePickerView extends LinearLayout {
	 public ArrayList<String> realDateList=new ArrayList<String>();
	public TimePickerView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	public TimePickerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public TimePickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
	
	}

	private void init() {
		View mytimepicker = LayoutInflater.from(getContext()).inflate(R.layout.ui_timepicker, this);
		WheelView wv_timepicker_date =(WheelView) mytimepicker.findViewById(R.id.wv_timepicker_date);
		WheelView wv_timepicker_hour =(WheelView) mytimepicker.findViewById(R.id.wv_timepicker_hour);
		WheelView wv_timepicker_min = (WheelView) mytimepicker.findViewById(R.id.wv_timepicker_min);
		ArrayList<String> hourData = getHourData();
		ArrayList<String> minData = getMinData();
		wv_timepicker_hour.setData(hourData);
		wv_timepicker_min.setData(minData);
		wv_timepicker_date.setData(getDayData());
		Calendar calendar = Calendar.getInstance();
		Date date=new Date();
		calendar.setTime(date);
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);
		String hour="";
		if (h<10) {
			hour="0"+h;
		}else{
			hour=h+"";
		}
		int hourIndexOf = hourData.indexOf(hour);
		int minIndexOf=0;
		for (int i = 0; i < minData.size(); i++) {
			if (Integer.parseInt(minData.get(i).substring(1))>m) {				
				minIndexOf=i;
				break;
			}
		}
		
		if (hourIndexOf!=-1&&minIndexOf!=-1) {
			wv_timepicker_hour.setDefault(hourIndexOf);
			wv_timepicker_min.setDefault(minIndexOf);
		}
		wv_timepicker_date.setDefault(0);
	//	wv_date.
	}

	
	private ArrayList<String> getDayData() {
        ArrayList<String> list = new ArrayList<String>();
       
        String strDate;
        String chineseDate;
        String week;
         strDate = DateUtils.getDateTime(DateUtils.DATE);
        String nextYearFirst = DateUtils.getNextYearFirst();
        Date date = DateUtils.strToDate(nextYearFirst);
        String yearEnd = DateUtils.getYearEnd(date);
        long nextYearDays = DateUtils.getDays(yearEnd, strDate);
        for (int i = 0; i < nextYearDays; i++) {
        	if (i==0) {
        		strDate = DateUtils.getNextDay(i);
        		realDateList.add(strDate);
        		 list.add("今天");
			}
        	else if (i==1) {
        		strDate = DateUtils.getNextDay(i);
        		realDateList.add(strDate);
        		list.add("明天");
			}else{
				strDate = DateUtils.getNextDay(i);
				realDateList.add(strDate);
				 chineseDate = DateUtils.getChineseDate(strDate);
				 week = DateUtils.getWeek(strDate);
				 list.add(chineseDate+" "+week);
			}
        	
		}
        
        
        return list;
    }

    private ArrayList<String> getHourData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
        	if (i<10) {
        		list.add("0"+i);
			}else{
				  list.add(i+"");
			}      
        }
        return list;
    }
    private ArrayList<String> getMinData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 60; i=i+5) {
        	if (i<10) {
        		list.add("0"+i);
			}else{
				  list.add(i+"");
			}   
        }
        return list;
    }
}
