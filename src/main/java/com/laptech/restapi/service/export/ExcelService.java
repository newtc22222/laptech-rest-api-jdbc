package com.laptech.restapi.service.export;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public interface ExcelService {
    void writeDataToExcel(HttpServletResponse response, Collection<String> options);
}
