package com.coreclasses.dataobjects;

import com.coreclasses.core.DataTable;
import jxl.read.biff.BiffException;

import java.io.IOException;

public class TestData {
	public static class Credentials {

		public static class LoginDetails {
			public static String username(int rownum) throws BiffException, IOException {
				return DataTable.getCellValue("Username", rownum);
			}

			public static String password(int rownum) throws BiffException, IOException {
				return DataTable.getCellValue("Password", rownum);
			}
		}

	}
}
