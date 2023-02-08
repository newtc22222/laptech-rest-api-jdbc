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

import java.util.List;

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
    public ResponseEntity<List<Label>> getAllLabel(@RequestParam(required = false, defaultValue = "1") Long page,
                                                   @RequestParam(required = false) Long size) {
        return ResponseEntity.ok(labelService.findAll(page, size));
    }

    @ApiOperation(value = "Get a label with id", response = Label.class)
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Label> getLabelById(@PathVariable(value = "id") long labelId) {
        return ResponseEntity.ok(labelService.findById(labelId));
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
    public ResponseEntity<BaseResponse> deleteLabel(@PathVariable(value = "id") long labelId) {
        labelService.delete(labelId);
        return DataResponse.success("Delete label");
    }
}
