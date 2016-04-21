package com.dawnlightning.ucqa.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class SdCardUtil {

	
	//项目文件根目录
	public static final String FILEDIR="/lightup";
	
	//照相机照片目录
	public static final String FILEPHOTO="/photos";
	
	//应用程序图片存放
	public static final String FILEAPK="apk";
	
	//应用程序缓存
	public static final String FILECACHE="cache";
	
	//用户信息目录
	public static final String FILEUSER="user";
	
	/**
	 * 检测sd卡是否可以用
	 * 
	 * @return
	 */
	public static boolean checkSdCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sd card 可用
			return true;
		} else {
			// 当前不可用
			return false;
		}
	}

	/**
	 * 获取sd卡文件路径
	 * 
	 * @return
	 */
	public static String getSdPath() {
		return Environment.getExternalStorageDirectory()+"";
	}

	/**
	 * 创建一个项目文件夹
	 * 
	 * @param fileDir
	 *            文件目录名
	 */
	public static void createFileDir(String fileDir) {
		String path = getSdPath() + fileDir;
		File path1 = new File(path);
		if (!path1.exists()) {
			path1.mkdirs();
			
		}
	}
	public static File updateDir = null;
	public static File updateFile = null;
	public static boolean isCreateFileSucess;

	/**
	 * ����������createFile����
	 * @param
	 * @return
	 * @see FileUtil
	 */
	public static void createFile(String app_name) {

		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			isCreateFileSucess = true;

			updateDir = new File(Environment.getExternalStorageDirectory()+"/lightup"+ "/" + FILEAPK +"/");
			updateFile = new File(updateDir + "/" + app_name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		}else{
			isCreateFileSucess = false;
		}
	}
	
	
}
