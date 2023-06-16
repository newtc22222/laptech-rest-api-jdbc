package com.laptech.restapi.service.export.impl;

import com.laptech.restapi.service.export.CsvService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CsvServiceImpl implements CsvService {
    @Override
    public void writeDataToCsv(HttpServletResponse response, Object[] headers, List<Object[]> objects) {
        try {
            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", "attachment; filename=data_" + LocalDateTime.now() + ".csv");
            CSVPrinter printer = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT);
            // header
            printer.printRecord(headers);
            printer.printRecord();
            // data
            objects.forEach(o -> {
                try {
                    printer.printRecord(o);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            printer.flush();
            printer.close();
        } catch (IOException err) {
            log.error("[WRITE CSV FILE] {}", err.getMessage());
        }
    }
}
