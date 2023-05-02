package com.laptech.restapi.service.export;

import javax.servlet.http.HttpServletResponse;

public interface PdfService {
    void writeDataToPDF(HttpServletResponse response);
}
