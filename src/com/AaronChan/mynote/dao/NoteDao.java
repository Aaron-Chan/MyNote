package com.AaronChan.mynote.dao;

import com.AaronChan.mynote.data.Note;
import com.AaronChan.mynote.data.NotesTable;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;
import com.AaronChan.mynote.utils.Logger;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class NoteDao {

	private ContentResolver contentResolver;

	public NoteDao(Context context) {
		contentResolver = context.getContentResolver();
	}

	// insert
	public int insert(long createDate, int bgColor, String content, long updateDate, long alarmTime) {
		int _id = 0;
		ContentValues values = new ContentValues();
		values.put(NoteColumns.CREATE_DATE, createDate);
		values.put(NoteColumns.BACKGROUND_COLOR_ID, bgColor);
		values.put(NoteColumns.CONTENT, content);
		values.put(NoteColumns.UPDATE_DATE, updateDate);
		values.put(NoteColumns.ALARM_TIME, alarmTime);
		Uri insert = contentResolver.insert(NotesTable.CONTENT_URL_NOTES, values);
		_id = (int) ContentUris.parseId(insert);

		return _id;
	}

	// delete
	public int delete(int _id) {
		int delete = contentResolver.delete(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, _id), null, null);
		return delete;
	}
	
	// update
	public int update(int _id, int bgColor, String content, long updateDate, long alarmTime) {
		ContentValues values = new ContentValues();
		values.put(NoteColumns.BACKGROUND_COLOR_ID, bgColor);
		values.put(NoteColumns.CONTENT, content);
		values.put(NoteColumns.UPDATE_DATE, updateDate);
		values.put(NoteColumns.ALARM_TIME, alarmTime);
		int update = contentResolver.update(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, _id), values, null,
				null);
		return update;
	}
	
	// queryAll
	public Cursor queryAll() {
		Cursor cursor = contentResolver.query(NotesTable.CONTENT_URL_NOTES, null, null, null, null);
		return cursor;
	}
	
	// queryByID
	public Note queryByID(int id) {
		Cursor cursor = contentResolver.query(NotesTable.CONTENT_URL_NOTES, null, "_id=?", new String[] { id + "" },
				null);
		Note note=null;
		if (cursor.moveToNext()) {
			int _id = cursor.getInt(cursor.getColumnIndex(NoteColumns.ID));
			String content = cursor.getString(cursor.getColumnIndex(NoteColumns.CONTENT));
			int bg_color_id =cursor.getInt(cursor.getColumnIndex(NoteColumns.BACKGROUND_COLOR_ID));
			long update_date = cursor.getLong(cursor.getColumnIndex(NoteColumns.UPDATE_DATE));
			long create_date = cursor.getLong(cursor.getColumnIndex(NoteColumns.CREATE_DATE));
			long alarm_time = cursor.getLong(cursor.getColumnIndex(NoteColumns.ALARM_TIME));
			 note=new Note(_id, content, bg_color_id, update_date, alarm_time, create_date);
		}
		
		return note;
	}
}
