package com.laptech.restapi.controller.statistic.system;

import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.LogSystem;
import com.laptech.restapi.service.LogSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-03-02
 */
@Api(tags = "Show log of system", value = "LogSystem Controller")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log-system")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class LogSystemController {
    private final LogSystemService service;

    @ApiOperation(value = "Get all log in system", response = LogSystem.class)
    @GetMapping("")
    public ResponseEntity<DataResponse> getAllLogs(@RequestParam(required = false) String sortBy,
                                                   @RequestParam(required = false) String sortDir,
                                                   @RequestParam(required = false) Long page,
                                                   @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all log-systems",
                service.count(),
                service.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get log in system with filter", response = LogSystem.class)
    @GetMapping("filter")
    public ResponseEntity<DataResponse> getLogWithFilter() {
        Map<String, Object> params = new HashMap<>();

        return DataResponse.getCollectionSuccess(
                "Get log-system with filter",
                service.findByFilter(params)
        );
    }
}
