package com.laptech.restapi.service.export.impl;

import com.laptech.restapi.service.export.ExcelService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExcelServiceImpl implements ExcelService {
//    public void writeDataToXlsx(HttpServletResponse response) {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        // sheet -> header -> records
//
//        Sheet sheet = workbook.createSheet("Product");
//        Sheet sheet2 = workbook.createSheet("Test");
//
//        // header
//        Row header = sheet.createRow(0);
//
//        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//        XSSFFont font = workbook.createFont();
//        font.setFontName("Tahoma");
//        font.setFontHeightInPoints((short) 14);
//        font.setBold(true);
//        headerStyle.setFont(font);
//
//        String[] headerText = {"ID", "Name", "Price", "Discount price"};
//        for (int i = 0; i < headerText.length; i++) {
//            Cell headerCell = header.createCell(i);
//            headerCell.setCellValue(headerText[i]);
//            headerCell.setCellStyle(headerStyle);
//        }
//
//        // data
//        int rowCount = 1;
//        for (Product product : SampleList.productList) {
//            Row row = sheet.createRow(rowCount++);
//
//            Object[] values = {product.getId(), product.getName(), product.getPrice().toString(), product.getDiscountPrice().toString()};
//            for (int i = 0; i < values.length; i++) {
//                Cell cell = row.createCell(i);
//                cell.setCellValue((String) values[i]);
//            }
//        }
//
//        try {
//            workbook.write(response.getOutputStream());
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void writeDataToExcelSingle(HttpServletResponse response, String sheetName, String[] headers, Collection<Object[]> record) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        // header
        Row header = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headers[i]);
        }
        // data
        AtomicInteger rowCount = new AtomicInteger(1);
        record.forEach(row -> {
            Row newRow = sheet.createRow(rowCount.getAndIncrement());
            for (int i = 0; i < row.length; i++) {
                Cell cell = newRow.createCell(i);
                cell.setCellValue((String) row[i]);
            }
        });

        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeDataToExcelMulti(HttpServletResponse response, Map<String, Collection<Object[]>> records) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        records.forEach((sheetKey, sheetRecord) -> {
            AtomicInteger rowCount = new AtomicInteger(0);
            Sheet sheet = workbook.createSheet(sheetKey);

            sheetRecord.forEach(row -> {
                Row newRow = sheet.createRow(rowCount.getAndIncrement());
                for (int i = 0; i < row.length; i++) {
                    Cell cell = newRow.createCell(i);
                    cell.setCellValue((String) row[i]);
                }
            });
        });


        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
