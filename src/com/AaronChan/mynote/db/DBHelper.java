package com.AaronChan.mynote.db;

import com.AaronChan.mynote.data.NotesTable;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String TAG="DBHelper";
	
	private static final String DB_NAME="notes.db";
	private static final int VERSION=1;
	
	/*private static final String ISFOLDER="background_color";
	private static final String SUPERFOLDER="superfolder";*/
	//创建table表格
	private static final String CREATE_NOTES="create table "+NotesTable.TABLE_NAME+" ("
			+ NoteColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ NoteColumns.CONTENT+" TEXT,"
			+NoteColumns.CREATE_DATE+" INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000)," 
			+NoteColumns.UPDATE_DATE+" INTEGER NOT NULL DEFAULT (strftime('%s','now') * 1000),"  
			+NoteColumns.BACKGROUND_COLOR_ID+" INTEGER,"
			+NoteColumns.ALARM_TIME+" INTEGER NOT NULL DEFAULT 0" 
			+");";
	
	
	
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
			
	}
	//重载构造器
	public DBHelper(Context context){
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(TAG,CREATE_NOTES);
		db.execSQL(CREATE_NOTES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
