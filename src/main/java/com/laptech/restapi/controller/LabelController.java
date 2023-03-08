package com.laptech.restapi.controller;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Label;
import com.laptech.restapi.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 2023-02-07
 */
@Api(tags = "Label or tag of product's special accessories", value = "Label controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/labels")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @ApiOperation(value = "Get all label in system", response = Label.class)
    @GetMapping("")
    public ResponseEntity<DataResponse> getAllLabels(@RequestParam(required = false) String sortBy,
                                                     @RequestParam(required = false) String sortDir,
                                                     @RequestParam(required = false) Long page,
                                                     @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all labels",
                labelService.count(),
                labelService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get label with limit", response = Label.class)
    @GetMapping("filter")
    public ResponseEntity<DataResponse> getLabelWithFilter(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String icon,
                                                           @RequestParam(required = false) String title,
                                                           @RequestParam(required = false) String createdDate,
                                                           @RequestParam(required = false) String modifiedDate,
                                                           @RequestParam(required = false) String deletedDate,
                                                           @RequestParam(required = false) Boolean isDel,
                                                           @RequestParam(required = false) String updateBy,
                                                           @RequestParam(required = false) String sortBy,
                                                           @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("icon", icon);
        params.put("title", title);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);
        return DataResponse.getCollectionSuccess(
                "Get all labels",
                labelService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get a label with id", response = Label.class)
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getLabelById(@PathVariable(value = "id") long labelId) {
        return DataResponse.getObjectSuccess(
                "Get label",
                labelService.findById(labelId)
        );
    }

    @ApiOperation(value = "Create new label", response = DataResponse.class)
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewLabel(@RequestBody Label label) {
        return DataResponse.success(
                "Create new label",
                labelService.insert(label)
        );
    }

    @ApiOperation(value = "Update label's detail", response = BaseResponse.class)
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateLabel(@PathVariable(value = "id") long labelId,
                                                    @RequestBody Label label) {
        labelService.update(label, labelId);
        return DataResponse.success("Update label");
    }

    @ApiOperation(value = "Delete label in system", response = BaseResponse.class)
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteLabel(@PathVariable(value = "id") long labelId,
                                                    @RequestBody(required = false) Map<String, String> body) {
        labelService.delete(labelId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete label");
    }
}
