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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<Collection<Discount>> getAllDiscount(@RequestParam(required = false, defaultValue = "1") Long page,
                                                               @RequestParam(required = false) Long size,
                                                               @RequestParam(value = "code", required = false) String code,
                                                               @RequestParam(value = "startDate", required = false) String startDate,
                                                               @RequestParam(value = "endDate", required = false) String endDate,
                                                               @RequestParam(value = "type", required = false) String type) {
        if (code == null && startDate == null && endDate == null && type == null) {
            return ResponseEntity.ok(discountService.findAll(page, size));
        }
        Map<String, String> params = new HashMap<>();
        if (code != null) params.put("code", code);
        if (startDate != null) params.put("startDate", code);
        if (endDate != null) params.put("endDate", code);
        if (type != null) params.put("type", type);
        return ResponseEntity.ok(discountService.filter(params));
    }

    @ApiOperation(value = "Get one discount with id", response = Discount.class)
    @GetMapping("/discounts/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<Discount> getDiscountById(@PathVariable("id") long discountId) {
        return ResponseEntity.ok(discountService.findById(discountId));
    }

    @ApiOperation(value = "Get all discounts of product (with productId)", response = Discount.class)
    @GetMapping("/products/{productId}/discounts")
    public ResponseEntity<List<Discount>> getDiscountsOfProduct(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(discountService.getDiscountsOfProduct(productId));
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
    public ResponseEntity<BaseResponse> deleteDiscount(@PathVariable("id") long discountId) {
        discountService.delete(discountId);
        return DataResponse.success("Delete discount");
    }
}
