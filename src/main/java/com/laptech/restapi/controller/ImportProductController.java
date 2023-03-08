package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.ImportProductDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.ImportProduct;
import com.laptech.restapi.service.ImportProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Ticket to import products", value = "Import Product controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/")
public class ImportProductController {
    @Autowired
    private ImportProductService importProductService;

    @ApiOperation(value = "Get all tickets", response = ImportProduct.class)
    @GetMapping("/imported")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getAllImportProductTickets(@RequestParam(required = false) String sortBy,
                                                                   @RequestParam(required = false) String sortDir,
                                                                   @RequestParam(required = false) Long page,
                                                                   @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all tickets",
                importProductService.count(),
                importProductService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get ticket with filter", response = ImportProduct.class)
    @GetMapping("/imported/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getImportProductWithFilter(@RequestParam(required = false) String productId,
                                                                   @RequestParam(required = false) Long quantity,
                                                                   @RequestParam(required = false) BigDecimal importedPrice,
                                                                   @RequestParam(required = false) String importedDate,
                                                                   @RequestParam(required = false) String date,
                                                                   @RequestParam(required = false) String startDate,
                                                                   @RequestParam(required = false) String endDate,
                                                                   @RequestParam(required = false) String createdDate,
                                                                   @RequestParam(required = false) String modifiedDate,
                                                                   @RequestParam(required = false) String deletedDate,
                                                                   @RequestParam(required = false) Boolean isDel,
                                                                   @RequestParam(required = false) String updateBy,
                                                                   @RequestParam(required = false) String sortBy,
                                                                   @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("quantity", quantity);
        params.put("importedPrice", importedPrice);
        params.put("importedDate", importedDate);
        params.put("date", date);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);

        return DataResponse.getCollectionSuccess(
                "Get ticket with filter",
                importProductService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get a ticket with id", response = ImportProduct.class)
    @GetMapping("/imported/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getImportProductTicket(@PathVariable("id") String ticketId) {
        return DataResponse.getObjectSuccess(
                "Get ticket",
                importProductService.findById(ticketId)
        );
    }

    @ApiOperation(value = "Get all tickets of a product", response = ImportProduct.class)
    @GetMapping("/products/{productId}/imported")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getImportProductTicketOfProduct(@PathVariable("productId") String productId) {
        return DataResponse.getCollectionSuccess(
                "Get import tickets of product",
                importProductService.findImportProductByProductId(productId)
        );
    }

    @ApiOperation(value = "Create a new ticket", response = DataResponse.class)
    @PostMapping("/imported")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewImportProductTicket(@RequestBody ImportProductDTO importProductDTO) {
        return DataResponse.success(
                "Create new import product ticket",
                importProductService.insert(ImportProductDTO.transform(importProductDTO))
        );
    }

    @ApiOperation(value = "Update a ticket", response = BaseResponse.class)
    @PutMapping("/imported/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateImportProductTicker(@PathVariable("id") String ticketId,
                                                                  @RequestBody ImportProductDTO importProductDTO) {
        importProductService.update(ImportProductDTO.transform(importProductDTO), ticketId);
        return DataResponse.success("Update import product ticket");
    }

    @ApiOperation(value = "Delete a ticket", response = BaseResponse.class)
    @DeleteMapping("/imported/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteImportProductTicket(@PathVariable("id") String ticketId,
                                                                  @RequestBody(required = false) Map<String, String> body) {
        importProductService.delete(ticketId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete import product ticket");
    }
}
