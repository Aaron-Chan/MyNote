package com.AaronChan.mynote.utils;

import java.io.File;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
/**
 * 类说明:SDCard操作类
 * 
 * @author xiaocheng
 * @date 2015.10.28
 *@version 1.0
 */
public class SDCardUtils {
	private static final String TYPE_CACHE = "cache";
	
	private static final String TYPE_FILES = "files";
	
	
	
	
	/**
	 *  SD卡剩余空间是否充足
	 *  返回  boolean
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

		// 根据系统大于等于18和小于18调用不同方法

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			blockSize = statFs.getBlockSizeLong();
			availableBlocks = statFs.getAvailableBlocksLong();
			//blockCount = statFs.getBlockCountLong();
		} else {
			blockSize = statFs.getBlockSize();
			availableBlocks = statFs.getAvailableBlocks();
			// = statFs.getBlockCount();
		}

		// 返回值为xxM 如 "19M";

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
	 * SDCard是否装载且可读可写 
	 * 用于存储
	 * @return
	 */
	public static boolean isMountedAndCanWrite() {
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED)?true:false;
	}
	/**
	 * SDCard是否装载且可读
	 * 用于读取
	 * @return
	 */
	public static boolean isMountedAndCanRead() {
		String state = Environment.getExternalStorageState();
		return (state.equals(Environment.MEDIA_MOUNTED)||state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))?true:false;
	}
	public static String getExternalFileDir(Context context, String type){
		return type;
		
	}
	/**获取扩展存储file的绝对路径
	 * 返回：sdcard/Android/data/{package_name}/files/ 
	 * @param context
	 * @return
	 */
	public static String getExternalFileDir(Context context){
		return getExternalDir( context, TYPE_FILES);
	}
	/**
	 * 获取扩展存储cache的绝对路径
	 * 返回：sdcard/Android/data/{package_name}/cache/ 
	 * @param context
	 * @return
	 */
	public static String getExternalCacheDir(Context context){
		return getExternalDir( context, TYPE_CACHE);
	}
	
	
	/**
	 * 获取扩展存储cache/files的绝对路径
	 * 
	 * 返回:sdcard/Android/data/{package_name}/cache(files)/ 
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
