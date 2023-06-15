package com.laptech.restapi.service.export;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

public interface ExcelService {
    void writeDataToExcelSingle(HttpServletResponse response, String sheetName, String[] headers, Collection<Object[]> record);

    void writeDataToExcelMulti(HttpServletResponse response, Map<String, Collection<Object[]>> records);
}
