package com.AaronChan.mynote.utils;

import java.io.File;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
/**
 * ��˵��:SDCard������
 * 
 * @author xiaocheng
 * @date 2015.10.28
 *@version 1.0
 */
public class SDCardUtils {
	private static final String TYPE_CACHE = "cache";
	
	private static final String TYPE_FILES = "files";
	
	
	
	
	/**
	 *  SD��ʣ��ռ��Ƿ����
	 *  ����  boolean
	 * @param needSizeM
	 * @return
	 */
	public static boolean isEnough(Context context,int needSizeM) {
		if (context==null) {
			return false;
		}
		File SDCard = Environment.getExternalStorageDirectory();
		StatFs statFs = new StatFs(SDCard.getAbsolutePath());
		long blockSize;
		long availableBlocks;
		//long blockCount;

		// ����ϵͳ���ڵ���18��С��18���ò�ͬ����

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			blockSize = statFs.getBlockSizeLong();
			availableBlocks = statFs.getAvailableBlocksLong();
			//blockCount = statFs.getBlockCountLong();
		} else {
			blockSize = statFs.getBlockSize();
			availableBlocks = statFs.getAvailableBlocks();
			// = statFs.getBlockCount();
		}

		// ����ֵΪxxM �� "19M";

		String sdavail = Formatter.formatFileSize(context, availableBlocks * blockSize);
		//String sdtotal = Formatter.formatFileSize(context, blockCount * blockSize);
		int availSizeM = Integer.parseInt(sdavail.substring(0, sdavail.length() - 1));
		if (needSizeM > availSizeM) {
			return false;
		} else {
			return true;
		}

	}
	/**
	 * SDCard�Ƿ�װ���ҿɶ���д 
	 * ���ڴ洢
	 * @return
	 */
	public static boolean isMountedAndCanWrite() {
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED)?true:false;
	}
	/**
	 * SDCard�Ƿ�װ���ҿɶ�
	 * ���ڶ�ȡ
	 * @return
	 */
	public static boolean isMountedAndCanRead() {
		String state = Environment.getExternalStorageState();
		return (state.equals(Environment.MEDIA_MOUNTED)||state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))?true:false;
	}
	public static String getExternalFileDir(Context context, String type){
		return type;
		
	}
	/**��ȡ��չ�洢file�ľ���·��
	 * ���أ�sdcard/Android/data/{package_name}/files/ 
	 * @param context
	 * @return
	 */
	public static String getExternalFileDir(Context context){
		return getExternalDir( context, TYPE_FILES);
	}
	/**
	 * ��ȡ��չ�洢cache�ľ���·��
	 * ���أ�sdcard/Android/data/{package_name}/cache/ 
	 * @param context
	 * @return
	 */
	public static String getExternalCacheDir(Context context){
		return getExternalDir( context, TYPE_CACHE);
	}
	
	
	/**
	 * ��ȡ��չ�洢cache/files�ľ���·��
	 * 
	 * ����:sdcard/Android/data/{package_name}/cache(files)/ 
	 * @param context
	 * @param type
	 * @return
	 */
	public static String getExternalDir(Context context, String type) {
		StringBuilder sb = new StringBuilder();
		if (context == null) {
			return null;
		}
		if (!isMountedAndCanRead()) {
			/*
			 * if (TYPE_CACHE.equals(type)) {
			 * sb.append(context.getCacheDir()).append(File.separator); }else if
			 * (TYPE_FILES.equals(type)) {
			 * sb.append(context.getFilesDir()).append(File.separator); } return
			 * sb.toString();
			 */
			return null;
		} else {
			File file = null;
			if (TYPE_CACHE.equals(type)) {
				file = context.getExternalCacheDir();
			} else if (TYPE_FILES.equals(type)) {
				file = context.getExternalFilesDir(null);

			}
			if (file != null) {
				sb.append(file.getAbsolutePath()).append(File.separator);
			} else {
				sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/")
						.append(context.getPackageName()).append("/").append(type).append("/").append(File.separator);
			}
		}
		return sb.toString();
	}
}
