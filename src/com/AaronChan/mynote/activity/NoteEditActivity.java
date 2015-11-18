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
	//默认背景色代号，默认为bule;
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
		//清除系统标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noteedit);
		//get DB Access Object
		dao = new NoteDao(this);
		et_NoteEditActivity_note_detail = (EditText) findViewById(R.id.et_NoteEditActivity_note_detail);
		ll_NoteEditActivity_title = (LinearLayout) findViewById(R.id.ll_NoteEditActivity_title);
		tv_NoteEditActivity_note_date_time = (TextView) findViewById(R.id.tv_NoteEditActivity_note_date_time);
		 tv_NoteEditActivity_note_alert_time = (TextView)findViewById(R.id.tv_NoteEditActivity_note_alert_time);
	
		//判断是否为新建便签
		isSavedNote = isSavedNote();
		//初始化页面相关内容
		init();
	
	
		//设置背景选择器监听器
		ImageButton ib_NoteEditActivity_bgcolor = (ImageButton) findViewById(R.id.ib_NoteEditActivity_bgcolor);
		ib_NoteEditActivity_bgcolor.setOnClickListener(this);
		//设置闹钟点击监听器
		ImageButton ib_NoteEditActivity_alarm_delete = (ImageButton)findViewById(R.id.ib_NoteEditActivity_alarm_delete);
		 ib_NoteEditActivity_alarm_delete.setOnClickListener(this);
		ib_NoteEditActivity_alarm = (ImageButton) findViewById(R.id.ib_NoteEditActivity_alarm);
		ib_NoteEditActivity_alarm.setOnClickListener(this);
		
		//将bg selector的相关view 加入NoteBGHolder备用
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
	 * 判断是否为新建便签
	 * @return
	 */
	private boolean 	isSavedNote(){
		// 获取bundle ，如果有，则是以前保存的便签，显示上次保存的内容
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
	 * 如果是新建的便签，加载默认内容,否则加载保存的内容
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
				//设置光标的位置
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
			// 获得创建时间，并转换为string设置在title
			createDate=System.currentTimeMillis();
			String titleDateTime=MyDateUtils.getDateTime(createDate, MyDateUtils.DETAIL_DATE_TIME);
			
			//设置title
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
				dateTimePickerDialog.setTitle("设置提醒时间");
			
				dateTimePickerDialog.setOnDateTimeSetListener(new OnDateTimeSetListener() {
					
					@Override
					public void OnDateTimeSet(AlertDialog dialog, long date) {
						// TODO Auto-generated method stub
						if (TextUtils.isEmpty(content)) {
							Toast.makeText(getApplicationContext(), "不能为空便签设置提醒时间", 0).show();
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
			// 如果便签背景选择器可见，则设置不可见，反之，设置可见并设置监听器
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
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNegativeButton("取消", null)
			.setTitle("是否删除提醒")
			.show();
			
			break;
		default:
			break;
		}
		

	}

	// 关于背景选择的图片holder类
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
		//颜色背景代号
		public  static final int BULE = R.drawable.edit_blue;
		public static final int YELLOW = R.drawable.edit_yellow;
		public static final int WHITE = R.drawable.edit_white;
		public static final int RED = R.drawable.edit_red;
		public static final int GREEN = R.drawable.edit_green;

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 关闭原来颜色的selected图标
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

			// 显示点击的颜色 selected图标  改变title 和EditText的背景图片
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
		//如果没有保存 先保存
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
				tv_NoteEditActivity_note_alert_time.setText("提醒已过期");
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
				//判断是已有的便签，此时editText的内容 不 为空，更新便签
				/*values.put(NoteColumns.BACKGROUND_COLOR_ID,bgColor );
				values.put(NoteColumns.CONTENT,content );
				values.put(NoteColumns.UPDATE_DATE,System.currentTimeMillis());
				values.put(NoteColumns.ALARM_TIME, alarmTime);
				int update = getContentResolver().update(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, note.get_id()), values, null, null);*/
				long updateDate = System.currentTimeMillis();
				dao.update(note.get_id(), bgColor, content, updateDate, alarmTime);
			}else{
				/*//保存便签
				values.put(NoteColumns.CREATE_DATE, create_date);
				values.put(NoteColumns.BACKGROUND_COLOR_ID,color );
				values.put(NoteColumns.CONTENT,content );
				
				values.put(NoteColumns.UPDATE_DATE,update_date);
				Logger.i(TAG, System.currentTimeMillis()+"");
				values.put(NoteColumns.ALARM_TIME, alarm_time);
				Uri insertUri = getContentResolver().insert(NotesTable.CONTENT_URL_NOTES, values);
				//有_id主键 返回值为_id
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
			//判断是已有的便签，此时editText的内容为空，删除便签
			if (isSavedNote) {
				int _id = note.get_id();
				dao.delete(_id);
			}
		}
	}
}
