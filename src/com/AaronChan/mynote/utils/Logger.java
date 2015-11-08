package com.AaronChan.mynote.utils;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
/**
 * 类说明:日志打印操作类
 * 
 * @author xiaocheng
 * @date 2015.10.28
 *@version 1.0
 */
public class Logger {
	private static final int Verbose=0;
	private static final int Debug=1;
	private static final int Info=2;
	private static final int Warn=3;
	private static final int Error=4;
	private static final int Nothing=5;
	private static  int level=Verbose;
	public static void v(String tag,String msg) {
		if (level<=Verbose) {
			Log.v(tag, msg);
		}
	}
	public static void d(String tag,String msg) {
		if (level<=Debug) {
			Log.d(tag, msg);
		}
	}
	public static void i(String tag,String msg) {
		if (level<=Info) {
			Log.i(tag, msg);
		}
	}
	public static void w(String tag,String msg) {
		if (level<=Warn) {
			Log.w(tag, msg);
		}
	}
	public static void e(String tag,String msg) {
		if (level<=Error) {
			Log.e(tag, msg);
		}
	}
	
}
