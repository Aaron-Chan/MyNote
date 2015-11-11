package com.AaronChan.mynote.adapter;


import java.util.Date;

import com.AaronChan.mynote.R;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;
import com.AaronChan.mynote.utils.Logger;
import com.AaronChan.mynote.utils.MyDateUtils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteCursorAdapter extends CursorAdapter {

	private static final String TAG="NoteCursorAdapter";
	private LayoutInflater mNoteContainer;

	public NoteCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		// TODO Auto-generated constructor stub
	}

	public NoteCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mNoteContainer = LayoutInflater.from(context);
		
		
		
		
	}
	
	//显示视图
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=null;
		//新建内部类 ViewHolder
		ViewHolder holder=new ViewHolder();
		//加载listview中每个item的view
		 view = (LinearLayout) mNoteContainer.inflate(R.layout.note_main_listitem, null);
		
		 holder. tv_main_listitemcontent=(TextView) view.findViewById(R.id.tv_main_listitemcontent);
		 holder. tv_main_listitemtime=(TextView) view.findViewById(R.id.tv_main_listitemtime);
		 holder. ll_main_listitemcontainer=(LinearLayout) view.findViewById(R.id.ll_main_listitemcontainer);
		 view.setTag(holder);
		return view;
	}

	//加载数据到视图
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		ViewHolder holder=(ViewHolder) view.getTag();
		 int _id = cursor.getInt(cursor.getColumnIndex(NoteColumns.ID));
		String content = cursor.getString(cursor.getColumnIndex(NoteColumns.CONTENT));
		int bg_color_id =cursor.getInt(cursor.getColumnIndex(NoteColumns.BACKGROUND_COLOR_ID));
		long update_date = cursor.getLong(cursor.getColumnIndex(NoteColumns.UPDATE_DATE));
		long create_date = cursor.getInt(cursor.getColumnIndex(NoteColumns.CREATE_DATE));
		long alarm_time = cursor.getInt(cursor.getColumnIndex(NoteColumns.ALARM_TIME));
		holder.ll_main_listitemcontainer.setBackgroundResource(bg_color_id);;
		Logger.i(TAG, update_date+"");
		long currentTimeMillis = System.currentTimeMillis();
		long dateRange = MyDateUtils.getDateRange(update_date, currentTimeMillis);
		if (dateRange==0) {
			holder.tv_main_listitemtime.setText("今天  "+MyDateUtils.getDateTime(update_date, MyDateUtils.SIMPLE_TIME));
		}else {
			holder.tv_main_listitemtime.setText(MyDateUtils.getDateTime(update_date, MyDateUtils.DETAIL_DATE_TIME));
		}
		holder.tv_main_listitemcontent.setText(content);
		
	}
	class ViewHolder {
		TextView tv_main_listitemtime;
		TextView tv_main_listitemcontent;
		LinearLayout ll_main_listitemcontainer;
	}
}
