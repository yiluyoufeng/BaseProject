package com.pro.feng.hf.core.cache;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * 本应用数据清理管理器
 *
 * @author AHF
 *
 */
public class DataCleanManager {

	/**
	 * 清楚本应用内部缓存（/data/data/com.gd.../cache）
	 *
	 * @param context
	 */
	public static void cleanInternalCache(Context context) {
		deleteFileByDirectory(context.getCacheDir());
//		ToastUtils.showMsgLong(context, "清除成功");
	}

	/**
	 * 清除数据库文件
	 *
	 * @param context
	 */
	public static void cleanDatabases(Context context) {
		deleteFileByDirectory(new File("data/data" + context.getPackageName()
				+ "/databases"));
	}

	/**
	 * 清除文件存储数据
	 *
	 * @param context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFileByDirectory(new File("/data/data/" + context.getPackageName()
				+ "shared_prefs"));
	}

	/**
	 * 通过数据库名称清除数据
	 *
	 * @param context
	 * @param dbName
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * 清除/data/data/包名/files下的内容
	 *
	 * @param context
	 */
	public static void cleanFiles(Context context) {
		deleteFileByDirectory(context.getFilesDir());
	}

	/**
	 * 清除外部cacha下的内容（(/mnt/sdcard/android/data/com.xxx.xxx/cache)）
	 *
	 * @param context
	 */
	public static void cleanExtranalChche(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFileByDirectory(context.getExternalCacheDir());
		}
	}

	/**
	 * 清除自定义路径的文件。
	 *
	 * @param
	 */
	public static void cleanCustomCache(String filePath) {
		deleteFileByDirectory(new File(filePath));
	}

	/**
	 * 清除本应用所有的数据
	 *
	 * @param context
	 * @param filePath
	 */
	public static void deleteApplicationData(Context context,
											 String... filePath) {
		cleanInternalCache(context);
		cleanExtranalChche(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		if (filePath == null) {
			return;
		}
		for (String fileStr : filePath) {
			cleanCustomCache(fileStr);
		}
	}

	/**
	 * 删除文件
	 *
	 * @param directory
	 */
	private static void deleteFileByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}

	// 获取文件
	// Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
	// 目录，一般放一些长时间保存的数据
	// Context.getExternalCacheDir() -->
	// SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 删除指定目录下文件及目录
	 *
	 * @param deleteThisPath
	 * @param
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 如果下面还有文件
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 格式化单位
	 *
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}

	public static String getCacheSize(File file) throws Exception {
		return getFormatSize(getFolderSize(file));
	}
}
