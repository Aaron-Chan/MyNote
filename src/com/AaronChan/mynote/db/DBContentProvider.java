package com.AaronChan.mynote.db;

import com.AaronChan.mynote.data.NotesTable;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;
import com.AaronChan.mynote.utils.Logger;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DBContentProvider extends ContentProvider {
	public static final String TAG= "DBContentProvider";
	public static final UriMatcher uriMatcher;
	private SQLiteDatabase db;
	  private static final int URI_NOTE            = 0;
	    private static final int URI_NOTE_ITEM       = 1;
	  //新建UriMatcher，加入需要匹配的uri
	static{
		 uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
		 uriMatcher.addURI(NotesTable.AUTHORITY, "notes", URI_NOTE);
		 uriMatcher.addURI(NotesTable.AUTHORITY, "notes/#", URI_NOTE_ITEM);
	}
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		DBHelper dbHelper=new DBHelper(getContext());
		db = dbHelper.getWritableDatabase();
		return true;
	}
	//查询记录
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
	
	SQLiteQueryBuilder builder=new SQLiteQueryBuilder();
	builder.setTables(NotesTable.TABLE_NAME);
	String orderBy="";
		switch (uriMatcher.match(uri)) {
		case URI_NOTE_ITEM:
			long id = ContentUris.parseId(uri);
			builder.appendWhere(NoteColumns.ID+"="+id);
		case URI_NOTE:
			if (TextUtils.isEmpty(orderBy)) {
				orderBy=NoteColumns.DEFAULT_SORT_ORDER;
			}
		
			break;

		default:
			break;
		}
		Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, orderBy);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	//插入记录
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		long id=0;
		if (uriMatcher.match(uri)==URI_NOTE) {
			 id = db.insert(NotesTable.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(ContentUris.withAppendedId(uri, id), null);
			
		}else{
			 throw new IllegalArgumentException("Unknown URI " + uri);
		}
		return ContentUris.withAppendedId(uri, id);
	}
	
	
	//删除记录
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int delete=0;
		if (uriMatcher.match(uri)==URI_NOTE_ITEM) {
			long _id = ContentUris.parseId(uri);
			 delete = db.delete(NotesTable.TABLE_NAME, NoteColumns.ID+"=?", new String[]{Long.toString(_id)});
		}
		getContext().getContentResolver().notifyChange(ContentUris.withAppendedId(uri, delete), null);
		
		return delete;
	}
	//更新记录
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int update=0;
		if (uriMatcher.match(uri)==URI_NOTE_ITEM) {
			long id = ContentUris.parseId(uri);
		 update = db.update(NotesTable.TABLE_NAME, values, NoteColumns.ID+"=?", new String[]{Long.toString(id)});
		}
		
		getContext().getContentResolver().notifyChange(ContentUris.withAppendedId(uri, update), null);
		
		
		return update;
	}

}
