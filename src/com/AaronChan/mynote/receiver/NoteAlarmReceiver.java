package com.AaronChan.mynote.receiver;


import com.AaronChan.mynote.activity.AlarmActivity;
import com.AaronChan.mynote.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class NoteAlarmReceiver extends BroadcastReceiver {
	private static final String TAG="NoteAlarm";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "onReceive");
		intent.setClass(context, AlarmActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		
	}

}
