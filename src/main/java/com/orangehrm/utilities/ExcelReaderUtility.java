package com.orangehrm.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtility {

    private Workbook workbook;

    public ExcelReaderUtility(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read Excel file: " + filePath, e);
        }
    }

    public Object[][] getSheetData(String sheetName) {

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException(
                    "Sheet '" + sheetName + "' not found");
        }

        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = (row == null) ? null : row.getCell(j);
                data[i - 1][j] = formatter.formatCellValue(cell).trim();
            }
        }
        return data;
    }

    public void close() {
        try {
            if (workbook != null) workbook.close();
        } catch (IOException e) {
            System.out.println("Failed to close workbook");
        }
    }
}
