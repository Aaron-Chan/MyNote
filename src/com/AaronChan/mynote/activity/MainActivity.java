package com.AaronChan.mynote.activity;

import com.AaronChan.mynote.R;
import com.AaronChan.mynote.adapter.NoteCursorAdapter;
import com.AaronChan.mynote.data.Note;
import com.AaronChan.mynote.data.NotesTable;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;
import com.AaronChan.mynote.utils.Logger;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;


public class MainActivity extends Activity implements OnClickListener,OnItemClickListener{
	private static final String TAG="MainActivity";
	private ListView lv_notes_MainActivity;
	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		ImageButton ib_add_note_MainActivity=(ImageButton) findViewById(R.id.ib_MainActivity_addnote);
		lv_notes_MainActivity = (ListView) findViewById(R.id.lv_MainActivity_notes);
		ib_add_note_MainActivity.setOnClickListener(this);	
		
		//更新视图
		refreshListView();
		lv_notes_MainActivity.setOnItemClickListener(this);
		//注册contentProvider监听器
		getContentResolver().registerContentObserver(NotesTable.CONTENT_URL_NOTES, true, new NoteDBContentObserver(new Handler()));
	}
	class NoteDBContentObserver extends ContentObserver{

		public NoteDBContentObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			//发生变化，更新视图
			refreshListView();
		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Logger.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Logger.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Logger.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Logger.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Logger.i(TAG, "onDestroy");
	}
	//更新listview
	private void refreshListView() {
		cursor = getContentResolver().query(NotesTable.CONTENT_URL_NOTES, null, null, null, null);
		lv_notes_MainActivity.setAdapter(new NoteCursorAdapter(this, cursor, true));								
		Logger.i(TAG, cursor.getCount()+"");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//跳转进入编辑页面
		case R.id.ib_MainActivity_addnote:
			Intent intent=new Intent(this, NoteEditActivity.class);
			startActivityForResult(intent, 100);
			break;

		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode==100&&resultCode==1) {
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		//将cursor的note信息保存在bundle中传给NoteEditActivity
		Bundle bundle=new Bundle();
		Logger.i(TAG, position+"");
		cursor.moveToPosition(position);
		int _id = cursor.getInt(cursor.getColumnIndex(NoteColumns.ID));
		String content = cursor.getString(cursor.getColumnIndex(NoteColumns.CONTENT));
		int bg_color_id =cursor.getInt(cursor.getColumnIndex(NoteColumns.BACKGROUND_COLOR_ID));
		long update_date = cursor.getLong(cursor.getColumnIndex(NoteColumns.UPDATE_DATE));
		long create_date = cursor.getLong(cursor.getColumnIndex(NoteColumns.CREATE_DATE));
		long alarm_time = cursor.getLong(cursor.getColumnIndex(NoteColumns.ALARM_TIME));
	
		Note value=new Note(_id, content, bg_color_id, update_date, alarm_time, create_date);
		bundle.putParcelable("notedata", value);
		Intent intent=new Intent(MainActivity.this, NoteEditActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
}
