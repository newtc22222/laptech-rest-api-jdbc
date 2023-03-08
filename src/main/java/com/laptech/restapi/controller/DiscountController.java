package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.DiscountDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Discount;
import com.laptech.restapi.service.DiscountService;
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
@Api(tags = "Discount code in system", value = "Discount controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @ApiOperation(value = "Get all discounts in system", response = Discount.class)
    @GetMapping("/discounts")
    public ResponseEntity<DataResponse> getAllDiscounts(@RequestParam(required = false) String sortBy,
                                                        @RequestParam(required = false) String sortDir,
                                                        @RequestParam(required = false) Long page,
                                                        @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all discount",
                discountService.count(),
                discountService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get discount with filter", response = Discount.class)
    @GetMapping("/discounts/filter")
    public ResponseEntity<DataResponse> getDiscountWithFilter(@RequestParam(required = false) String code,
                                                              @RequestParam(required = false) Float rate,
                                                              @RequestParam(required = false) String appliedType,
                                                              @RequestParam(required = false) BigDecimal maxAmount,
                                                              @RequestParam(required = false) String appliedDate,
                                                              @RequestParam(required = false) String endedDate,
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
        params.put("code", code);
        params.put("rate", rate);
        params.put("appliedType", appliedType);
        params.put("maxAmount", maxAmount);
        params.put("appliedDate", appliedDate);
        params.put("endedDate", endedDate);
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
                "Get discount with filter",
                discountService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get one discount with id", response = Discount.class)
    @GetMapping("/discounts/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<DataResponse> getDiscountById(@PathVariable("id") long discountId) {
        return DataResponse.getObjectSuccess(
                "Get discount",
                discountService.findById(discountId)
        );
    }

    @ApiOperation(value = "Get all discounts of product (with productId)", response = Discount.class)
    @GetMapping("/products/{productId}/discounts")
    public ResponseEntity<DataResponse> getDiscountsOfProduct(@PathVariable("productId") String productId) {
        return DataResponse.getCollectionSuccess(
                "Get discount of product",
                discountService.getDiscountsOfProduct(productId)
        );
    }

    @ApiOperation(value = "Create a new discount", response = DataResponse.class)
    @PostMapping("/discounts")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewDiscount(@RequestBody DiscountDTO discountDTO) {
        return DataResponse.success(
                "Create new discount",
                discountService.insert(DiscountDTO.transform(discountDTO))
        );
    }

    @ApiOperation(value = "Update a discount", response = BaseResponse.class)
    @PutMapping("/discounts/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateDiscount(@PathVariable("id") long discountId,
                                                       @RequestBody DiscountDTO discountDTO) {
        discountService.update(DiscountDTO.transform(discountDTO), discountId);
        return DataResponse.success("Update discount");
    }

    @ApiOperation(value = "Remove a discount", response = BaseResponse.class)
    @DeleteMapping("/discounts/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteDiscount(@PathVariable("id") long discountId,
                                                       @RequestBody(required = false) Map<String, String> body) {
        discountService.delete(discountId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete discount");
    }
}
