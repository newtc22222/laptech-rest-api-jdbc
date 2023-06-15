package com.laptech.restapi.service.export;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CsvService {
    void writeDataToCsv(HttpServletResponse response, Object[] headers, List<Object[]> records);
}
