package com.laptech.restapi.service.export.impl;

import com.laptech.restapi.samples.SampleList;
import com.laptech.restapi.samples.products.Product;
import com.laptech.restapi.service.export.CsvService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;

@Service
public class CsvServiceImpl implements CsvService {
    @Override
    public void writeDataToCsv(Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            // header
            printer.printRecord("ID", "Name", "Price", "Discount price");
            // data
            for (Product p : SampleList.productList) {
                printer.printRecord(p.getId(), p.getName(), p.getPrice(), p.getDiscountPrice());
            }
            printer.flush();
            printer.close();
        } catch (IOException err) {
            System.out.println(err);
        }
    }
}
