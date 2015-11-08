package com.AaronChan.mynote.service;


import com.AaronChan.mynote.R;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.TextView;

public class AlarmService extends Service {

	private MediaPlayer mediaPlayer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String alarm_type = intent.getStringExtra("alarm_type");
		if (TextUtils.isEmpty(alarm_type)) {
			mediaPlayer = MediaPlayer.create(AlarmService.this, R.raw.sanbao);
			
		}
		
		
		
		
		return super.onStartCommand(intent, flags, startId);
	}

}
