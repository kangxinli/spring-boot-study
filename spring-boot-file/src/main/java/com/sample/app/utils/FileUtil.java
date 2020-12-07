package com.sample.app.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sample.app.entity.FileInfo;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件工具类
 *
 */
@Slf4j
public class FileUtil {
	private FileUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static FileInfo getFileInfo(MultipartFile file) throws Exception {
		//String md5 = fileMd5(file.getInputStream());
		FileInfo fileInfo = new FileInfo();
		// 将文件的md5设置为文件表的id
		fileInfo.setId(IdUtil.fastSimpleUUID());
		fileInfo.setName(file.getOriginalFilename());
		fileInfo.setContentType(file.getContentType());
		fileInfo.setIsImg(fileInfo.getContentType().startsWith("image/"));
		fileInfo.setSize(file.getSize());
		fileInfo.setCreateTime(new Date());
		return fileInfo;
	}

	/**
	 * 文件的md5
	 *
	 * @param inputStream
	 * @return
	 */
	public static String fileMd5(InputStream inputStream) {
		try {
			return DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			log.error("fileMd5-error", e);
		}
		return null;
	}

	public static String saveFile(MultipartFile file, String path) {
		try {
			File targetFile = new File(path);
			if (targetFile.exists()) {
				return path;
			}
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			file.transferTo(targetFile);
			return path;
		} catch (Exception e) {
			log.error("saveFile-error", e);
		}
		return null;
	}

	public static boolean deleteFile(String pathname) {
		File file = new File(pathname);
		if (file.exists()) {
			boolean flag = file.delete();
			if (flag) {
				File[] files = file.getParentFile().listFiles();
				if (files == null || files.length == 0) {
					file.getParentFile().delete();
				}
			}
			return flag;
		}
		return false;
	}
	
	/**
	 * 获取文件目录下的文件
	 * @param strPath
	 * @return
	 */
	public static List<File> getFileList(String strPath) {
		List<File> filelist = new ArrayList<>();
		
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("jpg")) { // 判断文件名是否以 .xxx结尾
                    // String strFileName = files[i].getAbsolutePath();
                    // System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                } else {
                	// files[i].delete();
                    continue;
                }
            }
        }
        return filelist;
	}
}
