package com.AaronChan.mynote.data;

import android.net.Uri;

public class NotesTable {
	// ��ǩ��Ϣ��authority
	public static final String AUTHORITY="com.Aaron.mynote";
	// ��ű�ǩ��Ϣ�ı������
	public static final String TABLE_NAME="notes";
	
	public static final Uri CONTENT_URL_NOTES=Uri.parse("content://"+NotesTable.AUTHORITY+"/"+NotesTable.TABLE_NAME);
	
	
	
	
	
	public interface NoteColumns{
		// ��ǩ��id
		public static final String ID="_id";
		// ��ǩ������
		public static final String CONTENT="content";
		// ��ǩ�Ĵ�������
		public static final String CREATE_DATE="create_date";
		// ��ǩ���޸�����
		public static final String UPDATE_DATE="update_date";
		// ��ǩ�ı�����ɫid
		public static final String BACKGROUND_COLOR_ID="bg_color_id";
		// ��ǩ������ʱ��
		public static final String ALARM_TIME="alarm_time";
		// Ĭ�ϵ�����ʽ��
		public static final String DEFAULT_SORT_ORDER =  ID+ "  DESC , "
						+ UPDATE_DATE + " DESC ," + CREATE_DATE + " DESC";
	}

}
