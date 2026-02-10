package com.orangehrm.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	public static final String FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/testdata/TestData.xlsx";

	@DataProvider(name = "LoginData")
	public static Object[][] validLoginData() {
		return getSheetData("validLoginData");
	}

	@DataProvider(name = "inValidLoginData")
	public static Object[][] invalidLoginData() {
		return getSheetData("inValidLoginData");
	}


    private static Object[][] getSheetData(String sheetName) {
        ExcelReaderUtility excel = new ExcelReaderUtility(FILE_PATH);
        Object[][] data = excel.getSheetData(sheetName);
        excel.close();
        return data;
	}
}
