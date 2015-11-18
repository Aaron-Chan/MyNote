package com.AaronChan.mynote.activity;

import java.util.Calendar;
import java.util.Date;

import com.AaronChan.mynote.R;
import com.AaronChan.mynote.dao.NoteDao;
import com.AaronChan.mynote.data.Note;
import com.AaronChan.mynote.data.NotesTable;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;
import com.AaronChan.mynote.ui.DateTimePickerDialog;
import com.AaronChan.mynote.ui.DateTimePickerDialog.OnDateTimeSetListener;
import com.AaronChan.mynote.utils.Logger;
import com.AaronChan.mynote.utils.MyDateUtils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ActionBar.LayoutParams;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class NoteEditActivity extends Activity implements OnClickListener{
	//Ĭ�ϱ���ɫ���ţ�Ĭ��Ϊbule;
	private static int bgColor = R.drawable.edit_blue;
	private NoteBGHolder bgHolder;
	private LinearLayout ll_NoteEditActivity_title;
	private EditText et_NoteEditActivity_note_detail;
	private static final String TAG ="NoteEditActivity";
	private long createDate;
	private Note note;
	private long alarmTime;
	private TextView tv_NoteEditActivity_note_date_time;
	private AlertDialog alertDialog;
	private static final String ALARM_ACTION="com.AaronChan.mynote.intent.alarm";
	private boolean isSavedNote;
	private TextView tv_NoteEditActivity_note_alert_time;
	private ImageButton ib_NoteEditActivity_alarm;
	private PopupWindow popupWindow;
	private NoteDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//���ϵͳ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noteedit);
		//get DB Access Object
		dao = new NoteDao(this);
		et_NoteEditActivity_note_detail = (EditText) findViewById(R.id.et_NoteEditActivity_note_detail);
		ll_NoteEditActivity_title = (LinearLayout) findViewById(R.id.ll_NoteEditActivity_title);
		tv_NoteEditActivity_note_date_time = (TextView) findViewById(R.id.tv_NoteEditActivity_note_date_time);
		 tv_NoteEditActivity_note_alert_time = (TextView)findViewById(R.id.tv_NoteEditActivity_note_alert_time);
	
		//�ж��Ƿ�Ϊ�½���ǩ
		isSavedNote = isSavedNote();
		//��ʼ��ҳ���������
		init();
	
	
		//���ñ���ѡ����������
		ImageButton ib_NoteEditActivity_bgcolor = (ImageButton) findViewById(R.id.ib_NoteEditActivity_bgcolor);
		ib_NoteEditActivity_bgcolor.setOnClickListener(this);
		//�������ӵ��������
		ImageButton ib_NoteEditActivity_alarm_delete = (ImageButton)findViewById(R.id.ib_NoteEditActivity_alarm_delete);
		 ib_NoteEditActivity_alarm_delete.setOnClickListener(this);
		ib_NoteEditActivity_alarm = (ImageButton) findViewById(R.id.ib_NoteEditActivity_alarm);
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
	 * �ж��Ƿ�Ϊ�½���ǩ
	 * @return
	 */
	private boolean 	isSavedNote(){
		// ��ȡbundle ������У�������ǰ����ı�ǩ����ʾ�ϴα��������
		boolean flag=false;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			note = bundle.getParcelable("notedata");
			if (note != null) {
				flag=true;
			}else{
				note=new Note();
			}
		}
		return flag;
		
	}
	/**
	 * 
	 * ������½��ı�ǩ������Ĭ������,������ر��������
	 * @param isSavedNote 
	 */
	private void init() {
		if(isSavedNote){
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
				String update_time = MyDateUtils.getDateTime(note.getUpdate_date(), MyDateUtils.DETAIL_DATE_TIME);
				tv_NoteEditActivity_note_date_time.setText(update_time);
				
				if (note.getAlarm_time()!=0) {
					alarmTime=note.getAlarm_time();
					showAlarmDate(true);
				}
				bgColor=note.getBg_color_id();
				
			
			
		} else {
			// ��ô���ʱ�䣬��ת��Ϊstring������title
			createDate=System.currentTimeMillis();
			String titleDateTime=MyDateUtils.getDateTime(createDate, MyDateUtils.DETAIL_DATE_TIME);
			
			//����title
			tv_NoteEditActivity_note_date_time.setText(titleDateTime);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_NoteEditActivity_alarm:
			
			if (popupWindow!=null) {
				popupWindow.dismiss();
			}
			
			if (isSavedNote&&note.getAlarm_time()!=0) {
				
					View view = View.inflate(this, R.layout.popup_alarm, null);
					popupWindow = new PopupWindow(view, ib_NoteEditActivity_alarm.getWidth()*2,  ib_NoteEditActivity_alarm.getHeight()*2);
					
					popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					
					ImageView iv_popup_edit = (ImageView) view.findViewById(R.id.iv_popup_edit);
					ImageView iv_popup_delete = (ImageView) view.findViewById(R.id.iv_popup_delete);
					/*iv_popup_edit.setOnClickListener();
					iv_popup_delete.setOnClickListener();*/
					
					int[] location = new int[2];
					view.getLocationInWindow(location);
					
					popupWindow.showAtLocation(ib_NoteEditActivity_alarm, Gravity.LEFT|Gravity.TOP, location[0]+( int)ib_NoteEditActivity_alarm.getX(),location[1]+( int)ib_NoteEditActivity_alarm.getY());
					AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
					alphaAnimation.setDuration(500);				
	 				
					ScaleAnimation scaleAnimation = new ScaleAnimation(
							0, 1, 0, 1, 
							Animation.RELATIVE_TO_SELF, 0, 
							Animation.RELATIVE_TO_SELF, 0.5f);
					scaleAnimation.setDuration(500);
					
					
					AnimationSet as = new AnimationSet(true);
					as.addAnimation(alphaAnimation);
					as.addAnimation(scaleAnimation);
					
					view.startAnimation(as);
				
			}else{
				final String content = et_NoteEditActivity_note_detail.getText().toString();
				
				DateTimePickerDialog dateTimePickerDialog=new DateTimePickerDialog(this,System.currentTimeMillis());
				dateTimePickerDialog.setTitle("��������ʱ��");
			
				dateTimePickerDialog.setOnDateTimeSetListener(new OnDateTimeSetListener() {
					
					@Override
					public void OnDateTimeSet(AlertDialog dialog, long date) {
						// TODO Auto-generated method stub
						if (TextUtils.isEmpty(content)) {
							Toast.makeText(getApplicationContext(), "����Ϊ�ձ�ǩ��������ʱ��", 0).show();
						} else {
							alarmTime = date;
							
							onAlarmTimeChanged(true);
							
						
						}
					}

					
				});
				dateTimePickerDialog.show();
			}
			
			
		
		
			
			
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
		case R.id.ib_NoteEditActivity_alarm_delete:
			new AlertDialog.Builder(this)
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNegativeButton("ȡ��", null)
			.setTitle("�Ƿ�ɾ������")
			.show();
			
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
			switch (bgColor) {
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
				bgColor = BULE;
				break;
			case R.id.iv_bg_green:
				bgHolder.iv_bg_green_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_green);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_green);
				bgColor = GREEN;
				break;
			case R.id.iv_bg_red:
				bgHolder.iv_bg_red_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_red);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_red);
				bgColor = RED;
				break;
			case R.id.iv_bg_yellow:
				bgHolder.iv_bg_yellow_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_yellow);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_yellow);
				bgColor = YELLOW;
				break;
			case R.id.iv_bg_white:
				bgHolder.iv_bg_white_select.setVisibility(View.VISIBLE);
				ll_NoteEditActivity_title.setBackgroundResource(R.drawable.edit_title_white);
				et_NoteEditActivity_note_detail.setBackgroundResource(R.drawable.edit_white);
				bgColor = WHITE;
				break;

			default:
				break;
			}
		}

	}
	private void onAlarmTimeChanged(boolean isValidate) {
		// TODO Auto-generated method stub
		//���û�б��� �ȱ���
		if (isSavedNote) {
			note.setAlarm_time(alarmTime);
		} else {
			saveNote();
		}
		if (note.get_id() > 0) {
			Intent intent = new Intent(ALARM_ACTION);

			intent.setData(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, note.get_id()));
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			if (isValidate) {
				if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
					alarmManager.setExact(AlarmManager.RTC_WAKEUP, note.getAlarm_time(), pendingIntent);
				}else{
					alarmManager.set(AlarmManager.RTC_WAKEUP, note.getAlarm_time(), pendingIntent);
				}
				
			} else {
				alarmManager.cancel(pendingIntent);
			}
			
			showAlarmDate(isValidate);
			
			
		}

	}
	private void showAlarmDate(boolean isValidate) {
		// TODO Auto-generated method stub
		if (isValidate) {
			long now = System.currentTimeMillis();
			if (now>note.getAlarm_time()) {
				tv_NoteEditActivity_note_alert_time.setText("�����ѹ���");
			}else{
				String relativeTimeSpan = MyDateUtils.getRelativeTimeSpanString(note.getAlarm_time());
				tv_NoteEditActivity_note_alert_time.setText(relativeTimeSpan);
				tv_NoteEditActivity_note_alert_time.setVisibility(View.VISIBLE);
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
			if (isSavedNote) {
				//�ж������еı�ǩ����ʱeditText������ �� Ϊ�գ����±�ǩ
				/*values.put(NoteColumns.BACKGROUND_COLOR_ID,bgColor );
				values.put(NoteColumns.CONTENT,content );
				values.put(NoteColumns.UPDATE_DATE,System.currentTimeMillis());
				values.put(NoteColumns.ALARM_TIME, alarmTime);
				int update = getContentResolver().update(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, note.get_id()), values, null, null);*/
				long updateDate = System.currentTimeMillis();
				dao.update(note.get_id(), bgColor, content, updateDate, alarmTime);
			}else{
				/*//�����ǩ
				values.put(NoteColumns.CREATE_DATE, create_date);
				values.put(NoteColumns.BACKGROUND_COLOR_ID,color );
				values.put(NoteColumns.CONTENT,content );
				
				values.put(NoteColumns.UPDATE_DATE,update_date);
				Logger.i(TAG, System.currentTimeMillis()+"");
				values.put(NoteColumns.ALARM_TIME, alarm_time);
				Uri insertUri = getContentResolver().insert(NotesTable.CONTENT_URL_NOTES, values);
				//��_id���� ����ֵΪ_id
				int _id = (int) ContentUris.parseId(insertUri);*/
				long updateDate = System.currentTimeMillis();
				int _id = dao.insert(createDate, bgColor, content, updateDate, alarmTime);
				note.set_id(_id);
				note.setCreate_date(createDate);
				note.setBg_color_id(bgColor);
				note.setContent(content);
				note.setUpdate_date(updateDate);
				note.setAlarm_time(alarmTime);
				isSavedNote=false;
			}
		}else{
			//�ж������еı�ǩ����ʱeditText������Ϊ�գ�ɾ����ǩ
			if (isSavedNote) {
				int _id = note.get_id();
				dao.delete(_id);
			}
		}
	}
}
