package com.laptech.restapi.controller.export;

import com.laptech.restapi.service.export.ExcelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api(description = "Export Excel API", tags = "Export Excel Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/export-excel")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ExcelFileController {
    private final ExcelService excelService;

    @GetMapping("")
    public void exportExcel(@RequestParam List<String> options,
                            HttpServletResponse response) {
        excelService.writeDataToExcel(response, options);
    }
}
