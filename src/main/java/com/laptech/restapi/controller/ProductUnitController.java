package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.ProductUnitDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.ProductUnit;
import com.laptech.restapi.service.ProductUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Units in cart or invoice", value = "ProductUnit controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1")
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;

    @ApiOperation(value = "Get all units in cart", response = ProductUnit.class)
    @GetMapping("/cart/{cartId}/units")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ProductUnit>> getProductUnitsByCartId(@PathVariable("cartId") String cartId) {
        return ResponseEntity.ok(productUnitService.getProductUnitByCartId(cartId));
    }

    @ApiOperation(value = "Get all units in receipt", response = ProductUnit.class)
    @GetMapping("/invoice/{invoiceId}/units")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<List<ProductUnit>> getProductUnitsByInvoiceId(@PathVariable("invoiceId") String invoiceId) {
        return ResponseEntity.ok(productUnitService.getProductUnitByInvoiceId(invoiceId));
    }

    @ApiOperation(value = "Get an unit with id", response = ProductUnit.class)
    @GetMapping("/units/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<ProductUnit> getProductUnitsById(@PathVariable("id") String unitId) {
        return ResponseEntity.ok(productUnitService.findById(unitId));
    }

    @ApiOperation(value = "Add an unit to cart", response = DataResponse.class)
    @PostMapping("/units")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DataResponse> insertNewUnitToCart(@RequestBody ProductUnitDTO unitDTO) {
        return DataResponse.success(
                "Create new unit",
                productUnitService.insert(ProductUnitDTO.transform(unitDTO))
        );
    }

    @ApiOperation(value = "Update an unit's information", response = BaseResponse.class)
    @PutMapping("/units/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateProductUnit(@PathVariable("id") String unitId,
                                                          @RequestBody ProductUnitDTO unitDTO) {
        productUnitService.update(ProductUnitDTO.transform(unitDTO), unitId);
        return DataResponse.success("Update unit's information");
    }

    @ApiOperation(value = "Update quantity, price and discount price of an unit", response = BaseResponse.class)
    @PatchMapping("/units/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateProductUnitProperties(@PathVariable("id") String unitId,
                                                                    @RequestBody Map<String, String> unitRequest) {
        int quantity = Integer.getInteger(unitRequest.get("quantity"));
        BigDecimal price = new BigDecimal(unitRequest.get("price"));
        BigDecimal discountPrice = new BigDecimal(unitRequest.get("discountPrice"));
        productUnitService.updateProductUnitProperties(unitId, quantity, price, discountPrice);
        return DataResponse.success("Update unit's information");
    }

    @ApiOperation(value = "Remove an unit in cart (or invoice)", response = BaseResponse.class)
    @DeleteMapping("/units/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BaseResponse> deleteProductUnit(@PathVariable("id") String unitId) {
        productUnitService.delete(unitId);
        return DataResponse.success("Remove unit");
    }
}
