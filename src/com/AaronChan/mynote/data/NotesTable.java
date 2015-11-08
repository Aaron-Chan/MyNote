package com.AaronChan.mynote.data;

import android.net.Uri;

public class NotesTable {
	// 便签信息的authority
	public static final String AUTHORITY="com.Aaron.mynote";
	// 存放便签信息的表格名称
	public static final String TABLE_NAME="notes";
	
	public static final Uri CONTENT_URL_NOTES=Uri.parse("content://"+NotesTable.AUTHORITY+"/"+NotesTable.TABLE_NAME);
	
	
	
	
	
	public interface NoteColumns{
		// 便签的id
		public static final String ID="_id";
		// 便签的内容
		public static final String CONTENT="content";
		// 便签的创建日期
		public static final String CREATE_DATE="create_date";
		// 便签的修改日期
		public static final String UPDATE_DATE="update_date";
		// 便签的背景颜色id
		public static final String BACKGROUND_COLOR_ID="bg_color_id";
		// 便签的闹钟时间
		public static final String ALARM_TIME="alarm_time";
		// 默认的排序方式。
		public static final String DEFAULT_SORT_ORDER =  ID+ "  DESC , "
						+ UPDATE_DATE + " DESC ," + CREATE_DATE + " DESC";
	}

}
