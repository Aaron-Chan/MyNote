package com.AaronChan.mynote.receiver;

import com.AaronChan.mynote.dao.NoteDao;
import com.AaronChan.mynote.data.NotesTable;
import com.AaronChan.mynote.data.NotesTable.NoteColumns;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
	
public class NoteAlarmInitReceiver extends BroadcastReceiver {
	private static final String[] projection={NoteColumns.ID,NoteColumns.ALARM_TIME};
	private static final String ALARM_ACTION="com.AaronChan.mynote.intent.alarm";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
			long currentTime = System.currentTimeMillis();
			Cursor cursor = context.getContentResolver().query(NotesTable.CONTENT_URL_NOTES, projection, NoteColumns.ALARM_TIME+">?", new String[]{String.valueOf(currentTime)}, null);
			
			while (cursor.moveToNext()) {
				Intent alarmIntent=new Intent(ALARM_ACTION);
				int _id = cursor.getInt(0);
				long alarmTime = cursor.getLong(1);
				intent.setData(ContentUris.withAppendedId(NotesTable.CONTENT_URL_NOTES, _id));
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
				
					if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
						alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
					}else{
						alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
					}
			}
			cursor.close();
	}

}
