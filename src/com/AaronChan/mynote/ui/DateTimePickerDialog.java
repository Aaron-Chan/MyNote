package com.AaronChan.mynote.ui;

import java.util.Calendar;

import com.AaronChan.mynote.ui.DateTimePicker.OnDateTimeChangedListener;
import com.AaronChan.mynote.utils.MyDateUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;

public class DateTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener{
	Calendar pickerDateTime=Calendar.getInstance();
	 public interface OnDateTimeSetListener {
	        void OnDateTimeSet(AlertDialog dialog, long date);
	    }
	private  OnDateTimeSetListener onDateTimeSetListener;
	 
	 
	public  DateTimePickerDialog(Context context,long date) {
		super(context);
		// TODO Auto-generated constructor stub
		DateTimePicker dateTimePicker=new DateTimePicker(context, date);
		
		
		
				setView(dateTimePicker);
		dateTimePicker.setOnDateTimeChangedListener(new OnDateTimeChangedListener() {
			
			@Override
			public void onDateTimeChanged(DateTimePicker view, long DateTime) {
				// TODO Auto-generated method stub
				pickerDateTime.setTimeInMillis(DateTime);
				pickerDateTime.set(Calendar.SECOND, 0);
			}
		});
		this.setButton(this.BUTTON_POSITIVE, "ȷ��", this);
		this.setButton(this.BUTTON_NEGATIVE, "ȡ��", (OnClickListener)null);
		
	}
	/**
	 * ���� callback
	 * @param callback
	 */
	public void setOnDateTimeSetListener(OnDateTimeSetListener callback) {
		onDateTimeSetListener = callback;
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
    /**
     * �����ʱ�� ����pickerDateTime
     * @param dialog
     * @param which
     */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if (onDateTimeSetListener != null) {
        	onDateTimeSetListener.OnDateTimeSet(this, getDateTime());
        }
	}
}
