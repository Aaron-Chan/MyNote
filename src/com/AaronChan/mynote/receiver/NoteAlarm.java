package com.AaronChan.mynote.receiver;

import com.AaronChan.mynote.service.AlarmService;
import com.AaronChan.mynote.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NoteAlarm extends BroadcastReceiver {
	private static final String TAG="OneShotAlarm";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Logger.i(TAG, "onReceive");
		context.startService(new Intent(context, AlarmService.class));
	}

}
