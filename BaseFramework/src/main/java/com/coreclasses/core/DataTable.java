package com.coreclasses.core;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.FileInputStream;
import java.io.IOException;

public class DataTable {

	public static String getCellValue(String rowHeader, int rowNumber) throws BiffException, IOException {
		int row = 0;
		while (!getValueBySheetRowColumn(Browser.environment, row, 0).contentEquals(rowHeader)) {
			row += 1;
		}

		return getValueBySheetRowColumn(Browser.environment, row, rowNumber);
	}

	public static String getValueBySheetRowColumn(String sheet, Integer row, Integer column)
			throws BiffException, IOException {
		String FilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\Data\\Datatable.xls";
		FileInputStream fs = new FileInputStream(FilePath);
		Workbook wb = Workbook.getWorkbook(fs);
		Sheet sh = wb.getSheet(sheet);

		return sh.getCell(row, column).getContents();
	}

}