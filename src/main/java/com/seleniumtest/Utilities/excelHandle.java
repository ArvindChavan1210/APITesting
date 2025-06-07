package com.seleniumtest.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelHandle {

    public static XSSFSheet getSheet(String sheetName) {
        try {

            File file = new File(
                    "D:\\JAVA\\VS-CodePRactice\\selenium-testng-demo\\src\\main\\resources\\testData.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = null;
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (workbook.getSheetName(i).toLowerCase().equalsIgnoreCase(sheetName)) {
                    sheet = workbook.getSheetAt(i);
                    break;
                }
            }
            workbook.close();
            return sheet;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static int getRowCount(String sheetName) {
        Iterator<Row> rows = getSheet(sheetName).rowIterator();
        int rowcount = -1;
        while (rows.hasNext()) {
            rows.next();
            rowcount++;
        }
        return rowcount;
    }

    public static int getColumnCount(String sheetName) {
        Row firstRow = getSheet(sheetName).getRow(0);
        Iterator<Cell> cells = firstRow.cellIterator();
        int columnCount = -1;
        while (cells.hasNext()) {
            cells.next();
            columnCount++;
        }
        return columnCount;
    }

    public static HashMap<String, Integer> getColumnNames(String sheetName) {
        XSSFSheet sheet = getSheet(sheetName);
        Row row = sheet.getRow(0);
        Iterator<Cell> cell = row.cellIterator();
        HashMap<String, Integer> columnNumName = new HashMap<>();
        int columNumber = -1;
        while (cell.hasNext()) {
            String columnName = cell.next().getStringCellValue();
            columNumber++;
            columnNumName.put(columnName, columNumber);
        }
        return columnNumName;
    }

    public static List<String> getRowDataBasedonColumnName(String sheetName, String columnName) {
        int columIndex = getColumnNames(sheetName).get(columnName);
        Iterator<Row> rows = getSheet(sheetName).iterator();
        List<String> rowdata = new ArrayList<>();
        rows.next();
        while (rows.hasNext()) {
            Cell cell = rows.next().getCell(columIndex);
            switch (cell.getCellType()) {
                case STRING:
                    rowdata.add(cell.getStringCellValue());
                    break;

                case NUMERIC:
                    rowdata.add(String.valueOf(cell.getNumericCellValue()));
                    break;

                case BOOLEAN:
                    rowdata.add(String.valueOf(cell.getBooleanCellValue()));
                    break;

                case FORMULA:
                    rowdata.add(String.valueOf(cell.getCellFormula()));
                    break;

                case BLANK:
                    rowdata.add(null);
                    break;

                default:
                    System.out.println("Unknown or unsupported cell type");
                    break;
            }

        }
        return rowdata;
    }

    public static void updateExcellData(String sheetName, String columnName) {
        File file = new File("D:\\JAVA\\VS-CodePRactice\\selenium-testng-demo\\src\\main\\resources\\testData.xlsx");

        try (FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            for (int i = 0; i < getRowDataBasedonColumnName("sheet1", "customerName").size(); i++) {
                String address = dbUtility.getTableData("customers").get(i).get("salesRepEmployeeNumber");
                Row row = sheet.getRow(i + 1);
                Integer colIndex = getColumnNames(sheetName).get(columnName);

                if (colIndex == null) {
                    System.out.println("Column '" + columnName + "' not found!");
                    return;
                }

                Cell cell = row.getCell(colIndex);
                if (cell == null)
                    cell = row.createCell(colIndex);

                cell.setCellValue(address);
            }

            // Now write after everything is done
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        updateExcellData("sheet1", "employeeNumber");
        System.out.println(getRowDataBasedonColumnName("sheet1", "employeeNumber"));
        // System.out.println(getColumnNames("sheet1").get("addressLine1"));
    }
}
