package com.AaronChan.mynote.activity;

import java.util.Calendar;
import java.util.Date;

import com.AaronChan.mynote.R;
import com.AaronChan.mynote.data.Note;
import com.AaronChan.mynote.data.NotesTable;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;
import com.AaronChan.mynote.ui.TimePickerView;
import com.AaronChan.mynote.ui.WheelView;
import com.AaronChan.mynote.utils.DateUtils;
import com.AaronChan.mynote.utils.Logger;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteEditActivity extends Activity implements OnClickListener{
	//Ĭ�ϱ���ɫ���ţ�Ĭ��Ϊbule;
	private static int color = R.drawable.edit_blue;
	private NoteBGHolder bgHolder;
	private LinearLayout ll_NoteEditActivity_title;
	private EditText et_NoteEditActivity_note_detail;
	private static final String TAG ="NoteEditActivity";
	private String date;
	private long create_date;
	private String detailTime;
	private Note note;
	private long alarm_time;
	private TextView tv_NoteEditActivity_note_date_time;
	private AlertDialog alertDialog;
	private static final String ALARM_ACTION="com.AaronChan.mynote.intent.alarm";
	private TimePickerView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//���ϵͳ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noteedit);
		et_NoteEditActivity_note_detail = (EditText) findViewById(R.id.et_NoteEditActivity_note_detail);
		ll_NoteEditActivity_title = (LinearLayout) findViewById(R.id.ll_NoteEditActivity_title);
		tv_NoteEditActivity_note_date_time = (TextView) findViewById(R.id.tv_NoteEditActivity_note_date_time);
		
		//��ʼ��ҳ���������
		init();
	
		
		//���ñ���ѡ����������
		ImageButton ib_NoteEditActivity_bgcolor = (ImageButton) findViewById(R.id.ib_NoteEditActivity_bgcolor);
		ib_NoteEditActivity_bgcolor.setOnClickListener(this);
		//�������Ӽ�����
		ImageButton ib_NoteEditActivity_alarm = (ImageButton) findViewById(R.id.ib_NoteEditActivity_alarm);
		ib_NoteEditActivity_alarm.setOnClickListener(this);
		
		//��bg selector�����view ����NoteBGHolder����
		bgHolder = new NoteBGHolder();
		bgHolder.iv_bg_blue = (ImageView) findViewById(R.id.iv_bg_blue);
		bgHolder.iv_bg_yellow = (ImageView) findViewById(R.id.iv_bg_yellow);
		bgHolder.iv_bg_red = (ImageView) findViewById(R.id.iv_bg_red);
		bgHolder.iv_bg_green = (ImageView) findViewById(R.id.iv_bg_green);
		bgHolder.iv_bg_white = (ImageView) findViewById(R.id.iv_bg_white);

		bgHolder.iv_bg_blue_select = (ImageView) findViewById(R.id.iv_bg_blue_select);
		bgHolder.iv_bg_red_select = (ImageView) findViewById(R.id.iv_bg_red_select);
		bgHolder.iv_bg_yellow_select = (ImageView) findViewById(R.id.iv_bg_yellow_select);
		bgHolder.iv_bg_green_select = (ImageView) findViewById(R.id.iv_bg_green_select);
		bgHolder.iv_bg_white_select = (ImageView) findViewById(R.id.iv_bg_white_select);
		
	}
	
	/**
	 * ����bundle�Ļ�ȡ��������Ǳ���ı�ǩ����ʾ�ñ�ǩ�ϴα��������
	 * ��������½��ı�ǩ������Ĭ������
	 */
	private void init() {
		// ��ȡbundle ������У�������ǰ����ı�ǩ����ʾ�ϴα��������
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			note = bundle.getParcelable("notedata");
			if (note != null) {
				switch (note.getBg_color_id()) {
				case R.drawable.edit_blue:
					ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_blue);
					break;
				case R.drawable.edit_green:
					ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_green);
					break;

				case R.drawable.edit_red:
					ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_red);
					break;

				case R.drawable.edit_white:
					ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_white);
					break;
				case R.drawable.edit_yellow:
					ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_yellow);
					break;


				default:
					break;
				}
				et_NoteEditActivity_note_detail.setText(note.getContent());
				//���ù���λ��
				et_NoteEditActivity_note_detail.setSelection(note.getContent().length());
				et_NoteEditActivity_note_detail.setBackgroundResource(note.getBg_color_id());
				String update_time = DateUtils.getDetailTime(note.getUpdate_date());
				tv_NoteEditActivity_note_date_time.setText(update_time);
				
				int _id = note.get_id();
			}
		} else {
			// ��õ�ǰ���ں;���ʱ��
			detailTime = DateUtils.getDateTime(DateUtils.DETAIL_TIME);
			date = DateUtils.getDateTime(DateUtils.DATE);
			create_date=System.currentTimeMillis();
			//����ʱ��
			tv_NoteEditActivity_note_date_time.setText(date+" "+DateUtils.getDateTime(DateUtils.TIME));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_NoteEditActivity_alarm:
			
			view = new TimePickerView(this);
		final WheelView wv_timepicker_date =(WheelView) view.findViewById(R.id.wv_timepicker_date);
		final WheelView wv_timepicker_hour =(WheelView) view.findViewById(R.id.wv_timepicker_hour);
		final WheelView wv_timepicker_min = (WheelView) view.findViewById(R.id.wv_timepicker_min);
			alertDialog = new AlertDialog.Builder(this)
			.setView(view)
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ALARM_ACTION);
					PendingIntent pendingIntent=PendingIntent.getBroadcast(NoteEditActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
					AlarmManager alarmManager=(AlarmManager) NoteEditActivity.this.getSystemService(Context.ALARM_SERVICE);
					 int selectedDate = wv_timepicker_date.getSelected();
					String hour = wv_timepicker_hour.getSelectedText();
					String min = wv_timepicker_min.getSelectedText();
					String date = view.realDateList.get(selectedDate);
					String strDate=date+" "+hour+":"+min;
					Logger.i(TAG, strDate);
					Date strToDate = DateUtils.strToDate(strDate, DateUtils.ALARM_TIME);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(strToDate);
					Logger.i(TAG, strToDate.toString());
					alarm_time=calendar.getTimeInMillis();
					alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
					
					
				}
			})
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			})
			.show();
			
			break;
		case R.id.ib_NoteEditActivity_bgcolor:
			// �����ǩ����ѡ�����ɼ��������ò��ɼ�����֮�����ÿɼ������ü�����
			bgHolder.ll_NodeEditActivity_bg_color_selector = (LinearLayout) findViewById(R.id.ll_NodeEditActivity_bg_color_selector);
			if (bgHolder.ll_NodeEditActivity_bg_color_selector.getVisibility() == View.VISIBLE) {
				bgHolder.ll_NodeEditActivity_bg_color_selector.setVisibility(View.GONE);
			} else {
				bgHolder.ll_NodeEditActivity_bg_color_selector.setVisibility(View.VISIBLE);
				

				NoteBGSelector selectorListener = new NoteBGSelector();
				bgHolder.iv_bg_blue.setOnClickListener(selectorListener);
				bgHolder.iv_bg_yellow.setOnClickListener(selectorListener);
				bgHolder.iv_bg_red.setOnClickListener(selectorListener);
				bgHolder.iv_bg_green.setOnClickListener(selectorListener);
				bgHolder.iv_bg_white.setOnClickListener(selectorListener);
			}
			break;

		default:
			break;
		}
		

	}

	// ���ڱ���ѡ���ͼƬholder��
	class NoteBGHolder {
		ImageView iv_bg_blue;
		ImageView iv_bg_yellow;
		ImageView iv_bg_red;
		ImageView iv_bg_green;
		ImageView iv_bg_white;
		ImageView iv_bg_blue_select;
		ImageView iv_bg_red_select;
		ImageView iv_bg_yellow_select;
		ImageView iv_bg_green_select;
		ImageView iv_bg_white_select;
		LinearLayout ll_NodeEditActivity_bg_color_selector;
	}

	class NoteBGSelector implements OnClickListener {
		//��ɫ��������
		public  static final int BULE = R.drawable.edit_blue;
		public static final int YELLOW = R.drawable.edit_yellow;
		public static final int WHITE = R.drawable.edit_white;
		public static final int RED = R.drawable.edit_red;
		public static final int GREEN = R.drawable.edit_green;

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// �ر�ԭ����ɫ��selectedͼ��
			switch (color) {
			case BULE:
				bgHolder.iv_bg_blue_select.setVisibility(View.GONE);
				break;
			case YELLOW:
				bgHolder.iv_bg_yellow_select.setVisibility(View.GONE);
				break;
			case WHITE:
				bgHolder.iv_bg_white_select.setVisibility(View.GONE);
				break;
			case RED:
				bgHolder.iv_bg_red_select.setVisibility(View.GONE);
				break;
			case GREEN:
				bgHolder.iv_bg_green_select.setVisibility(View.GONE);
				break;

			default:
				break;
			}

			// ��ʾ�������ɫ selectedͼ��  �ı�title ��EditText�ı���ͼƬ
			switch (v.getId()) {
			case R.id.iv_bg_blue:
				bgHolder.iv_bg_blue_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_blue);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_blue);
				color = BULE;
				break;
			case R.id.iv_bg_green:
				bgHolder.iv_bg_green_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_green);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_green);
				color = GREEN;
				break;
			case R.id.iv_bg_red:
				bgHolder.iv_bg_red_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_red);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_red);
				color = RED;
				break;
			case R.id.iv_bg_yellow:
				bgHolder.iv_bg_yellow_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_yellow);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_yellow);
				color = YELLOW;
				break;
			case R.id.iv_bg_white:
				bgHolder.iv_bg_white_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_white);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_white);
				color = WHITE;
				break;

			default:
				break;
			}
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		saveNote();
		
		
		super.onBackPressed();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
		saveNote();
		
		super.onSaveInstanceState(outState);
	}
	private void saveNote() {
		String content = et_NoteEditActivity_note_detail.getText().toString();
		if (!TextUtils.isEmpty(content)) {
			
			ContentValues values=new ContentValues();
			if (note!=null) {
				//�ж������еı�ǩ����ʱeditText������ �� Ϊ�գ����±�ǩ
				values.put(NoteColumns.BACKGROUND_COLOR_ID,color );
				values.put(NoteColumns.CONTENT,content );
				values.put(NoteColumns.UPDATE_DATE,System.currentTimeMillis());
				values.put(NoteColumns.ALARM_TIME, alarm_time);
				int update = getContentResolver().update(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, note.get_id()), values, null, null);
			Logger.i(TAG, ""+update);
			}else{
				//�����ǩ
				Logger.i(TAG, detailTime+"   "+DateUtils.getDateTime(DateUtils.DETAIL_TIME));
				values.put(NoteColumns.CREATE_DATE, create_date);
				values.put(NoteColumns.BACKGROUND_COLOR_ID,color );
				values.put(NoteColumns.CONTENT,content );
				values.put(NoteColumns.UPDATE_DATE,System.currentTimeMillis());
				Logger.i(TAG, System.currentTimeMillis()+"");
				values.put(NoteColumns.ALARM_TIME, alarm_time);
				Uri insertUri = getContentResolver().insert(NotesTable.CONTENT_URL_NOTES, values);
			}
		}else{
			//�ж������еı�ǩ����ʱeditText������Ϊ�գ�ɾ����ǩ
			if (note!=null) {
				int _id = note.get_id();
				int delete = getContentResolver().delete(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, _id), null, null);
				Logger.i(TAG, ""+delete);
			}
		}
	}
}
