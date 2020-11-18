package com.sample.crypto.utils;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具
 */
public class FileUtil {
	private FileUtil() {
	}

	/**
	 * Java文件操作 获取文件扩展名
	 * 
	 * @param filename 文件名
	 * @return 扩展名
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * Java文件操作 获取不带扩展名的文件名
	 * 
	 * @param filename 文件名
	 * @return 不带扩展名的文件名
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public static boolean makeSureExistDir(String fileDir) {
		File file = new File(fileDir);
		return makeSureExistDir(file);
	}

	public static boolean makeSureExistDir(File dirFile) {
		if (!dirFile.exists()) {
			return dirFile.mkdirs();
		}
		return true;
	}

	public static boolean makeSureExistFile(String fileName) throws IOException {
		File file = new File(fileName);
		return makeSureExistFile(file);
	}

	public static boolean makeSureExistFile(File file) throws IOException {
		if (file.exists()) {
			return true;
		}
		// 先确保父目录存在
		File parentFile = file.getParentFile();
		makeSureExistDir(parentFile);

		return file.createNewFile();
	}

	/**
	 * 连接目录名+文件名，自动处理分隔符
	 * 
	 * @param dir      目录名
	 * @param fileName 文件名，可以带分隔符
	 * @return 连接后的文件名
	 */
	public static String concat(String dir, String fileName) {
		if (StrUtil.isEmpty(fileName)) {
			throw new IllegalArgumentException("fileName 不允许为空");
		}
		if (StrUtil.isEmpty(dir)) {
			return fileName;
		}
		if (endsWithSplash(dir) && startsWithSplash(fileName)) {
			return dir + fileName.substring(1);
		}
		if (!endsWithSplash(dir) && !startsWithSplash(fileName)) {
			return dir + File.separator + fileName;
		}
		return dir + fileName;
	}

	private static boolean endsWithSplash(String name) {
		// 以/或者\结尾
		return name.endsWith(StrUtil.SLASH) || name.endsWith(StrUtil.BACKSLASH);
	}

	private static boolean startsWithSplash(String name) {
		// 以/或者\开头
		return name.startsWith(StrUtil.SLASH) || name.startsWith(StrUtil.BACKSLASH);
	}
}
