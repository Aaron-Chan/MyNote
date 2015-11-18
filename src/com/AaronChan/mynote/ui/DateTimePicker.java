package com.AaronChan.mynote.ui;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.AaronChan.mynote.R;
import com.AaronChan.mynote.utils.MyDateUtils;

import android.content.Context;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

public class DateTimePicker extends FrameLayout {
	Calendar initDateTime;
	private   int dateMaxVaule = 29;
	private static  final int DATE_MIN_VAULE = 0;
	private int dayDisPlayed=0;
	
	private   int hourMaxVaule = 23;
	private static final  int NORMAL_HOUR_MAX_VALUE = 23;
	private static  final int HOUR_MIN_VAULE = 0;
	
	private   int minutMaxVaule = 11;
	private static final  int NORMAL_MINUT_MAX_VALUE = 11;
	private static  final int MINUT_MIN_VAULE = 0;
	
	private static final int DAYS_ONE_MONTH =30;
	private static final int BEGIN_INDEX = 0;
	
	private NumberPicker np_date;
	private NumberPicker np_hour;
	private NumberPicker np_min;
	private Calendar pickerDateTime;
	private ArrayList<String> dateDisplayedValuesList;
	private int dateLastIndex;
	private String[] normalHourData;
	private String[] normalMinData;
	private String[] currentHourData;
	private String[] currentMinData;
	private ArrayList<String> normalHourList;
	private ArrayList<String> currenHourList;
	private ArrayList<String> normalMinList;
	private ArrayList<String> currenMinList;
	
    public interface OnDateTimeChangedListener {
        void onDateTimeChanged(DateTimePicker view, long DateTime);
    }
    private OnDateTimeChangedListener onDateTimeChangedListener;
/*    private int getCurrentYear(){
    	return date_time.get(Calendar.YEAR);
    }
    private int getCurrentDa(){
    	return date_time.get(Calendar.YEAR);
    }
    private int getCurrentYear(){
    	return date_time.get(Calendar.YEAR);
    }*/
    private OnScrollListener date_onScrollListener=new OnScrollListener() {
		
		@Override
		public void onScrollStateChange(NumberPicker view, int scrollState) {
			// TODO Auto-generated method stub
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
				if (view.getValue()==dateMaxVaule) {
					Log.i("date_onScrollListener", "����");
					updateDateNumPicker();
				}
				
				break;
			case OnScrollListener.SCROLL_STATE_FLING:

				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

				break;

			default:
				break;
			}
		}
	};
	private OnValueChangeListener date_onValueChangeListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			Log.i("date_onValueChangeListener", "newVal " + newVal + " oldVal " + oldVal);
			// &&
			if (newVal == 0) {
				// ��Ҫ�ı�hour��min��ֵ
				int hourIndex = currenHourList.indexOf(np_hour.getDisplayedValues()[np_hour.getValue()]);
				if (hourIndex != -1) { // ����ѡ���hour,currentHour���������
					// ���ĵ�ʱ�����ԭ��max���ڸ��µ����ݣ���Ҫ������max����setDisplayedValue
					np_hour.setMaxValue(currentHourData.length - 1);
					np_hour.setDisplayedValues(currentHourData);
					np_hour.setValue(hourIndex);
					np_hour.setWrapSelectorWheel(false);
					np_hour.invalidate();
					
					//hour��0λ������min���ݣ����¶�λ
					if (hourIndex == 0) {
						
						int minIndex = currenMinList.indexOf(np_min.getDisplayedValues()[np_min.getValue()]);
						np_min.setMaxValue(currentMinData.length - 1);
						np_min.setDisplayedValues(currentMinData);
						
						if (minIndex != -1) {
							np_min.setValue(minIndex);
						} else {
							np_min.setValue(BEGIN_INDEX);
						}
						np_min.setWrapSelectorWheel(false);
						np_min.invalidate();
						
						updatePickerDateTime();
						
					}

				} else { // ����ѡ���hour,currentHour����������� hour min����Ϊ0
					np_hour.setMaxValue(currentHourData.length - 1);
					np_hour.setDisplayedValues(currentHourData);
					np_hour.setValue(BEGIN_INDEX);
					np_hour.setWrapSelectorWheel(false);
					np_hour.invalidate();

					np_min.setMaxValue(currentMinData.length - 1);
					np_min.setDisplayedValues(currentMinData);
					np_min.setValue(BEGIN_INDEX);
					np_min.setWrapSelectorWheel(false);
					np_min.invalidate();
					
					updatePickerDateTime();
				}
			} else if (newVal != 0 && oldVal == 0) {

				// �������hour����
				int hourIndex = normalHourList.indexOf(np_hour.getDisplayedValues()[np_hour.getValue()]);
				np_hour.setDisplayedValues(normalHourData);
				np_hour.setMaxValue(normalHourData.length - 1);
				np_hour.setValue(hourIndex);
				np_hour.setWrapSelectorWheel(false);
				np_hour.invalidate();

				// �����ǰСʱΪ�����0λ��minҲ�ı�����
				if (np_hour.getDisplayedValues()[np_hour.getValue()].equals(currentHourData[0])) {
					int minIndex = normalMinList.indexOf(np_min.getDisplayedValues()[np_min.getValue()]);
					np_min.setDisplayedValues(normalMinData);
					np_min.setMaxValue(normalMinData.length - 1);
					np_min.setValue(minIndex);
					np_min.setWrapSelectorWheel(false);
					np_min.invalidate();
				}
			} 
			
			//����ʱ��
			pickerDateTime.add(Calendar.DAY_OF_YEAR, newVal - oldVal);
			//֪ͨʱ�����
			onDateTimeChanged();
			

		}
		
		
	};
	
	private OnValueChangeListener hour_onValueChangeListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			Log.i("hour_onValueChangeListener", "newVal " + newVal + " oldVal " + oldVal);
			// �ж��ǽ���
			if (np_date.getDisplayedValues()[np_date.getValue()].equals(dateDisplayedValuesList.get(0))) {
				// �ж���hour��һλ
				if (newVal == 0) {
					// ��Ҫ�ı�min��ֵ
					int minIndex = currenMinList.indexOf(np_min.getDisplayedValues()[np_min.getValue()]);
					np_min.setMaxValue(currentMinData.length - 1);
					np_min.setDisplayedValues(currentMinData);
					if (minIndex != -1) { // ����ѡ���min,currentMin���������
						np_min.setValue(minIndex);
					} else { // ����ѡ���min,currentMin�����������
						np_min.setValue(BEGIN_INDEX);
					}
					np_min.setWrapSelectorWheel(false);
					np_min.invalidate();
					
					//����ʱ��
					updatePickerDateTime();

				} else {
					if (newVal != 0 && oldVal == 0) {// �ж���hour�ӵ�һλתΪ����λ
						int minIndex = normalMinList.indexOf(np_min.getDisplayedValues()[np_min.getValue()]);
						np_min.setDisplayedValues(normalMinData);
						np_min.setMaxValue(normalMinData.length - 1);
						np_min.setValue(minIndex);
						np_min.setWrapSelectorWheel(false);
						np_min.invalidate();

					}
					pickerDateTime.add(Calendar.HOUR_OF_DAY, newVal - oldVal);
				}

			} else {
				pickerDateTime.add(Calendar.HOUR_OF_DAY, newVal - oldVal);
			}
			//֪ͨʱ�����
			onDateTimeChanged();
			
		}
	};
	private OnValueChangeListener min_onValueChangeListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			pickerDateTime.add(Calendar.MINUTE, (newVal - oldVal)*5);
			onDateTimeChanged();
		}
	};
	
	
	public DateTimePicker(Context context) {
		this(context,System.currentTimeMillis());
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param context
	 * @param date
	 */
	public DateTimePicker(Context context,long date) {
		super(context);
		// TODO Auto-generated constructor stub
		initDateTime=Calendar.getInstance();
		initDateTime.setTimeInMillis(date);
		Log.i("date", date+"");
		View dateTimePicker = inflate(context, R.layout.date_time_picker, this);
		
		np_date = (NumberPicker) dateTimePicker.findViewById(R.id.np_date);
		np_hour = (NumberPicker) dateTimePicker.findViewById(R.id.np_hour);
		np_min = (NumberPicker) dateTimePicker.findViewById(R.id.np_min);
		//���ɱ༭
		np_date.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		np_hour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		np_min.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		
		np_date.setMaxValue(dateMaxVaule);
		np_date.setMinValue(DATE_MIN_VAULE);
		np_date.setValue(BEGIN_INDEX);
		np_date.setOnValueChangedListener(date_onValueChangeListener);
		np_date.setOnScrollListener(date_onScrollListener);
		//����
		dateDisplayedValuesList = new ArrayList<String>();
		updateDateNumPicker();
		// list
		normalMinList = getMinData();
		currenMinList = getCurrentMinData(date);

		// String[]
		normalMinData = getMinData().toArray(new String[normalMinList.size()]);
		currentMinData = getCurrentMinData(date).toArray(new String[currenMinList.size()]);

		Log.i("currenMinList", currenMinList.size() + "");
		Log.i("currentMinData", currentMinData.length + "");
		minutMaxVaule = currentMinData.length - 1;
		np_min.setMaxValue(minutMaxVaule);
		np_min.setMinValue(MINUT_MIN_VAULE);
		np_min.setDisplayedValues(currentMinData);
		np_min.setOnValueChangedListener(min_onValueChangeListener);
		np_min.setWrapSelectorWheel(false);
		
		
		
		//list
		normalHourList = getHourData();
		currenHourList = getCurrentHourData(date);
		
		//String[]
		normalHourData = getHourData().toArray(new String[normalHourList.size()]);		
		currentHourData =getCurrentHourData(date).toArray(new String[currenHourList.size()]);
		
		 Log.i("normalHourList", currenHourList.size()+"");
		 
		Log.i("currentHourData", currentHourData.length+"");
		
		hourMaxVaule=currentHourData.length-1;
		np_hour.setMaxValue(hourMaxVaule);
		np_hour.setMinValue(HOUR_MIN_VAULE);
		np_hour.setDisplayedValues(currentHourData);
		np_hour.setOnValueChangedListener(hour_onValueChangeListener);
		np_hour.setWrapSelectorWheel(false);
		
		
		
		pickerDateTime=Calendar.getInstance();
		
		updatePickerDateTime();
		
	}
	private void updateDateNumPicker() {
		// TODO Auto-generated method stub
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(initDateTime.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		int temp=dayDisPlayed;
		dayDisPlayed+=DAYS_ONE_MONTH;
		
		np_date.setDisplayedValues(null);
		//��ȡ���date��λ��
		if (dateDisplayedValuesList.size()==0) {
			dateLastIndex=0;
		}else{
			dateLastIndex = dateDisplayedValuesList.size()-1;
			String string = dateDisplayedValuesList.get(dateMaxVaule);
			Date date = MyDateUtils.strToDate(string, MyDateUtils.DATE_WEEK);
			calendar.setTime(date);
		}
		Log.i("temp", temp+"");
		Log.i("dayDisPlayed", dayDisPlayed+"");
		Log.i("dateLastIndex", dateLastIndex+"");
		for (int i = temp; i < dayDisPlayed; i++) {
			Log.i("calendar", DateUtils.formatDateTime(getContext(), calendar.getTimeInMillis(), DateUtils.FORMAT_NO_YEAR));
			calendar.add(Calendar.DAY_OF_YEAR, 1);	
			long dateRange = MyDateUtils.getDateRange(initDateTime.getTimeInMillis(), calendar.getTimeInMillis());
			Log.i("dateRange", dateRange+"");
			if (dateRange==0) {
				dateDisplayedValuesList.add("����");
			}else if (dateRange==1) {
				dateDisplayedValuesList.add("����");
			}else{
				dateDisplayedValuesList.add(MyDateUtils.getDateTime(calendar.getTimeInMillis(), MyDateUtils.DATE_WEEK));
			}	
		}
		String[] dateDisplayedValues =  dateDisplayedValuesList.toArray(new String[dateDisplayedValuesList.size()]);
		Log.i("dateDisplayedValues", ""+dateDisplayedValues.length);
		dateMaxVaule=dateDisplayedValues.length-1;
		Log.i("lastValues", ""+dateDisplayedValues[dateLastIndex]);
		np_date.setDisplayedValues(dateDisplayedValues);
		np_date.setMaxValue(dateMaxVaule);
		np_date.setMinValue(DATE_MIN_VAULE);
		np_date.setWrapSelectorWheel(false);
		np_date.setValue(dateLastIndex);
		Log.i("thread", Thread.currentThread()+"");
		np_date.invalidate();
		
	}
	/**
	 * ����PickerDateTime
	 */
	private void updatePickerDateTime() {
		// TODO Auto-generated method stub..
		String datePicker = np_date.getDisplayedValues()[np_date.getValue()];
		String hourPicker = np_hour.getDisplayedValues()[np_hour.getValue()];
		String minPicker = np_min.getDisplayedValues()[np_min.getValue()];
		Log.i("minPicker", minPicker);
		Date date;
		if (datePicker.equals("����")) {
			 date=new Date(initDateTime.getTimeInMillis());
		}else if (datePicker.equals("����")) {
			 date=new Date(initDateTime.getTimeInMillis()+24*60*60*1000);
		}else{
			 date = MyDateUtils.strToDate(datePicker, MyDateUtils.DATE_WEEK);
		}
		
		if (hourPicker.indexOf(0)==0) {
			hourPicker=hourPicker.substring(1);
		}
		if (minPicker.indexOf(0)==0) {
			minPicker=minPicker.substring(1);
		}
		pickerDateTime.setTime(date);
		pickerDateTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourPicker));
		pickerDateTime.set(Calendar.MINUTE, Integer.parseInt(minPicker));
		Toast.makeText(getContext(), "pickerDateTime"+MyDateUtils.getDateTime(pickerDateTime.getTimeInMillis(),MyDateUtils.DETAIL_DATE_TIME), 0).show();
		Log.i("pickerDateTime", MyDateUtils.getDateTime(pickerDateTime.getTimeInMillis(),MyDateUtils.DETAIL_DATE_TIME));
		
		
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
    /**
     * ��ȡ��ǰСʱ��min����
     * @param date
     * @return
     */
    private ArrayList<String> getCurrentMinData(long date) {
		// TODO Auto-generated method stub
	    ArrayList<String> list = new ArrayList<String>();
	    Calendar calendar=Calendar.getInstance();
	    calendar.setTimeInMillis(date);
	    int i = calendar.get(Calendar.MINUTE);
			i=5*(i/5)+5;

	    if (i==60) {
			return getMinData();
		}else{
			Log.i("CurrentMin", i+"");
		    for (int j = i; j <60; j=j+5) {
		    	if (j<10) {
	        		list.add("0"+j);
				}else{
					  list.add(j+"");
				} 
		    	
			}
		}
	    
	    
	    return list;
	}
    /**
     * ��ȡ�����hour����
     * @param date
     * @return
     */
	private ArrayList<String> getCurrentHourData(long date) {
		// TODO Auto-generated method stub
	    ArrayList<String> list = new ArrayList<String>();
	    Calendar calendar=Calendar.getInstance();
	    calendar.setTimeInMillis(date);
	    int i = calendar.get(Calendar.HOUR_OF_DAY);
	    Log.i("CurrentHour", i+"");
		if (minutMaxVaule == 11) {
			i+=1;
		}
		for (int j = i; j < 24; j++) {
			if (j < 10) {
				list.add("0" + j);
			} else {
				list.add(j + "");
			}
		}  
	    return list;
	}
	/**
	 * ���� callback
	 * @param callback
	 */
	public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) {
		onDateTimeChangedListener = callback;
		onDateTimeChanged();
    }
	
	/**
	 * ÿ�ε��ô˷�����֪ͨʱ����£����û������onDateTimeChangedListener���÷���û��ִ���κζ�����
	 */
    private void onDateTimeChanged() {
        if (onDateTimeChangedListener != null) {
        	onDateTimeChangedListener.onDateTimeChanged(this, getDateTime());
        }
    }
    /**
     * ��ȡdateTimePickerѡ���ʱ��
     * @return
     */
    private long getDateTime() {
		// TODO Auto-generated method stub..
    	Log.i("pickerDateTime", MyDateUtils.getDateTime(pickerDateTime.getTimeInMillis(),MyDateUtils.DETAIL_DATE_TIME));
		return pickerDateTime.getTimeInMillis();
	}
    
}
