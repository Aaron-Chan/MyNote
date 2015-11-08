package com.AaronChan.mynote.data;

import com.AaronChan.mynote.data.NotesTable.NoteColumns;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable{
	private int _id;
	private String content;
	private int bg_color_id;
	private long update_date;
	private long alarm_time;
	private long create_date;
	public int get_id() {
		return _id;
	}


	public void set_id(int _id) {
		this._id = _id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getBg_color_id() {
		return bg_color_id;
	}


	public Note(int _id, String content, int bg_color_id, long update_date, long alarm_time, long create_date) {
		super();
		this._id = _id;
		this.content = content;
		this.bg_color_id = bg_color_id;
		this.update_date = update_date;
		this.alarm_time = alarm_time;
		this.create_date = create_date;
	}


	public void setBg_color_id(int bg_color_id) {
		this.bg_color_id = bg_color_id;
	}


	



	
	

	public long getUpdate_date() {
		return update_date;
	}


	public void setUpdate_date(long update_date) {
		this.update_date = update_date;
	}


	public long getAlarm_time() {
		return alarm_time;
	}


	public void setAlarm_time(long alarm_time) {
		this.alarm_time = alarm_time;
	}


	public long getCreate_date() {
		return create_date;
	}


	public void setCreate_date(long create_date) {
		this.create_date = create_date;
	}


	public Note() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(_id);
		dest.writeString(content);
		dest.writeInt(bg_color_id);
		dest.writeLong(create_date);
		dest.writeLong(update_date);
		dest.writeLong(alarm_time);
		
	}
	
	public static final Creator<Note> CREATOR=new Creator<Note>() {
		
		@Override
		public Note[] newArray(int size) {
			// TODO Auto-generated method stub
			Note[]  array=new Note[size];
			return array;
		}
		
		@Override
		public Note createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Note note=new Note();
			note.set_id(source.readInt());
			note.setContent(source.readString());
			note.setBg_color_id(source.readInt());
			note.setCreate_date(source.readLong());
			note.setUpdate_date(source.readLong());
			note.setAlarm_time(source.readLong());

			return note;
		}
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
