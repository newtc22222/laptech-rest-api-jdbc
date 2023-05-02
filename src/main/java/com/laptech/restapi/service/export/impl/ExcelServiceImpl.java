package com.laptech.restapi.service.export.impl;

import com.laptech.restapi.samples.SampleList;
import com.laptech.restapi.samples.products.Product;
import com.laptech.restapi.service.export.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public void writeDataToXlsx(HttpServletResponse response) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Product");
        Sheet sheet2 = workbook.createSheet("Test");
//        sheet.setColumnWidth(0, 1000);

        // header
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontName("Tahoma");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);

        String[] headerText = { "ID", "Name", "Price", "Discount price" };
        for (int i = 0; i < headerText.length; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerText[i]);
            headerCell.setCellStyle(headerStyle);
        }

        // data
        int rowCount = 1;
        for (Product product : SampleList.productList) {
            Row row = sheet.createRow(rowCount++);

            Object[] values = {product.getId(), product.getName(), product.getPrice().toString(), product.getDiscountPrice().toString()};
            for (int i = 0; i < values.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue((String) values[i]);
            }
        }

        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
