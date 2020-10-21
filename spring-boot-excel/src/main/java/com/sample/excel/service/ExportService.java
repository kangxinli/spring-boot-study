package com.sample.excel.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.sample.excel.utils.ExcelUtil;

@Service
public class ExportService {
	
	public void export(HttpServletResponse response) {
		
		// 读取Excel文档
		File finalXlsxFile = ExcelUtil.copyTemplateFile("template-1.xls");// 复制模板
		
		if (finalXlsxFile == null) {
			System.out.println("获取模板文件失败！");
			return ;
		}
		
		Workbook workBook = null;
		try {
			workBook = ExcelUtil.getWorkbok(finalXlsxFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// sheet 对应一个工作页 插入数据开始 ------
		Sheet sheet = workBook.getSheetAt(0);
		Row row1 = sheet.createRow(1);		// 获取到第2行
		Cell cell00 = row1.createCell(0);	// 2行 1列
		cell00.setCellValue(1);
		Cell cell01 = row1.createCell(1);	// 2行 2列
		cell01.setCellValue(2);
		Cell cell02 = row1.createCell(2);	// 2行 3列
		cell02.setCellValue(3);
		
		Row row2 = sheet.createRow(2);		// 获取到第3行
		Cell cell10 = row2.createCell(0);	// 3行 1列
		cell10.setCellValue(4);
		Cell cell11 = row2.createCell(1);	// 3行 2列
		cell11.setCellValue(5);
		Cell cell12 = row2.createCell(2);	// 3行 3列
		cell12.setCellValue(6);
		// 插入数据结束
		
		//强制执行该sheet中所有公式
		// sheet.setForceFormulaRecalculation(true);  
		
		// 导出文件
		ExcelUtil.export(response, finalXlsxFile.getName(), workBook);
	}

}
