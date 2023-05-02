package com.laptech.restapi.service.export;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {
    void writeDataToXlsx(HttpServletResponse response);
}
