package com.cdn.utilities;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public FileInputStream fis = null;

	public XSSFRow row = null;
	public XSSFCell cell = null;
	File file = null;
	XSSFWorkbook workbook = null;
	XSSFSheet sheet = null;
	String filename = null;

	public ExcelReader(String filename) {
		try {
			fis = new FileInputStream(filename);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName) {
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() + 1;
		return rowCount;
	}

	public int getColumnCount(String sheetName) {
		sheet = workbook.getSheet(sheetName);
		XSSFRow row = sheet.getRow(0);
		int colCount = row.getLastCellNum();
		return colCount;
	}

	public String getCellData(int rowNumber, int colNumber) {
		String cellData = null;
		try {
			cellData = sheet.getRow(rowNumber).getCell(colNumber).getStringCellValue();
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			t.printStackTrace();
			System.out.println(t.getCause());
		}
		return cellData;
	}

}
