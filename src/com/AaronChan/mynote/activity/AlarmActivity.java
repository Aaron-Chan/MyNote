package com.AaronChan.mynote.activity;

import java.io.IOException;

import com.AaronChan.mynote.dao.NoteDao;
import com.AaronChan.mynote.data.Note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class AlarmActivity extends Activity implements DialogInterface.OnClickListener{
	 private static final int CONTENT_MAX_LEN = 60;
	private Note note;
	private MediaPlayer mediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		NoteDao dao=new NoteDao(this);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
		
		PowerManager pm=(PowerManager) getSystemService(POWER_SERVICE);

		if (!pm.isScreenOn()) {
			window.addFlags(
					WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
							| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
							| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
							| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
		}
		
		Intent intent = getIntent();
		Uri data = intent.getData();
		int id = (int) ContentUris.parseId(data);
		
		note = dao.queryByID(id);
		
		if (note!=null) {
		
			playAlarmSound();
			showActionDialog();
		}else{
			finish();
		}
		
		
		

	}
	private void showActionDialog() {
		// TODO Auto-generated method stub
		String content = note.getContent();
		String contentShow=content.length()>CONTENT_MAX_LEN?content.substring(0, CONTENT_MAX_LEN)+"...":content;
		new AlertDialog.Builder(this)
		.setMessage(contentShow)
		.setPositiveButton("知道了", this)
		.setNegativeButton("查看", this)
		.show();
	}
	private void playAlarmSound() {
		// TODO Auto-generated method stub
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		RingtoneManager manager=new RingtoneManager(this);
		Uri alarmRingtoneUri = manager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);
	     int silentModeStreams = Settings.System.getInt(getContentResolver(),
	                Settings.System.MODE_RINGER_STREAMS_AFFECTED, 0);
	     if ((silentModeStreams & (1 << AudioManager.STREAM_ALARM)) != 0) {
	    	 mediaPlayer.setAudioStreamType(silentModeStreams);
	        } else {
	        	mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
	        }
	     try {
			mediaPlayer.setDataSource(this, alarmRingtoneUri);
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		  switch (which) {
          case DialogInterface.BUTTON_NEGATIVE:
              Intent intent = new Intent(this, NoteEditActivity.class);
              Bundle extras=new Bundle();
              extras.putParcelable("notedata", note);
              intent.putExtras(extras);
              startActivity(intent);
              break;
          default:
              break;
      }
		  stopAlarmSound();
		  finish();
	}
	private void stopAlarmSound() {
		// TODO Auto-generated method stub
		if (mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer=null;
		}
	}
	
}
