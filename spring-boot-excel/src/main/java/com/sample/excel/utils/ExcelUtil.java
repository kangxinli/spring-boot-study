package com.sample.excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

public class ExcelUtil {

	/**
	 * 判断excel格式版本
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbok(File file) throws IOException {
		Workbook wb = null;
		FileInputStream in = new FileInputStream(file);
		if (file.getName().endsWith(".xls")) { // Excel&nbsp;2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith("xlsx")) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		} else if (file.getName().endsWith("xlsm")) {
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}
	
	/**
	 * 复制模板文件
	 * @param fileName
	 * @return
	 */
	public static File copyTemplateFile(String fileName) {
		// 模板路径
		String finalXlsxPath = "template" + File.separator + fileName;
		return createNewFile(finalXlsxPath);
	}

	/**
	 * 复制创建新文件
	 * @param path
	 * @return
	 */
	public static File createNewFile(String path) {
		// 读取模板，并赋值到新文件************************************************************
		// 文件模板路径

		// 写入到新的excel
		File newFile = null;
		try {
			File file = ResourceUtils.getFile("classpath:" + path);
			if (!file.exists()) {
				System.out.println("原模板文件不存在");
			}
			// 保存文件的路径
			String realPath = file.getParent();
			
			System.out.println("生成文件路径 : " + realPath);
			// 新的文件名
			String newFileName = "报表-" + System.currentTimeMillis() + path.substring(path.indexOf("."));
			
			// 判断路径是否存在
			File dir = new File(realPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			newFile = new File(realPath, newFileName);
			// 复制模板到新文件
			FileUtils.copyFile(file, newFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newFile;
	}

	/**
	 * 导出文件
	 * @param response
	 * @param fileName
	 * @param workBook
	 */
	public static void export(HttpServletResponse response, String fileName, Workbook workBook) {
		try {
			fileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            OutputStream os = response.getOutputStream();
            workBook.write(os);
            os.flush();
            os.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
