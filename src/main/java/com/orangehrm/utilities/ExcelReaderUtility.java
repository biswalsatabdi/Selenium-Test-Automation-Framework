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

        DataFormatter formatter = new DataFormatter();

        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        // Temporary list to store valid rows
        java.util.List<Object[]> validData = new java.util.ArrayList<>();

        for (int i = 1; i <= rowCount; i++) {

            Row row = sheet.getRow(i);
            if (row == null) continue;

            Object[] rowData = new Object[colCount];
            boolean isEmptyRow = true;

            for (int j = 0; j < colCount; j++) {

                Cell cell = row.getCell(j);
                String value = formatter.formatCellValue(cell).trim();

                rowData[j] = value;

                if (!value.isEmpty()) {
                    isEmptyRow = false;
                }
            }

            // Only add non-empty rows
            if (!isEmptyRow) {
                validData.add(rowData);
            }
        }

        // Convert List to Object[][]
        return validData.toArray(new Object[0][]);
    }


    public void close() {
        try {
            if (workbook != null) workbook.close();
        } catch (IOException e) {
            System.out.println("Failed to close workbook");
        }
    }
}
