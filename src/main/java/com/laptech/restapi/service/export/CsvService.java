package com.laptech.restapi.service.export;

import java.io.Writer;

public interface CsvService {
    void writeDataToCsv(Writer writer);
}
