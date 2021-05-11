package com.youming.sample.apache.poi;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 单元格类型不同时的内容处理
 * */
public class TypeDealUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String excelFile = "F:\\测试.xlsx";
		try (Workbook workbook = WorkbookFactory.create(new FileInputStream(excelFile))) {
			deal(workbook);
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
	}
	
	public static void deal(Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0); // 只取第一张表
		int rowNum = sheet.getLastRowNum(); // 获取最后一行的编号
		for (int i = 0; i < rowNum + 1; i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(13);		//取特定列
			if (cell == null) {
				continue;
			}
			CellType cellType = cell.getCellTypeEnum();		
			String cellStr = cell.toString();
			//String cellValue = cell.getStringCellValue();
			System.out.println(cellType + ":" + cellStr + "|" );
		}
	}

}
