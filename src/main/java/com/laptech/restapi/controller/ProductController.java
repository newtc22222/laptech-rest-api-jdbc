package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.dto.response.ProductDetailDTO;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Nhat Phi
 * @version 1.2
 * @since 2022-11-22 (update 2023-02-07
 */
@Api(tags = "Product CRUD apis", value = "Product controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Get all products in system", response = Product.class)
    @GetMapping("/products")
    public ResponseEntity<Collection<?>> getAllProduct(@RequestParam(required = false, defaultValue = "1") Long page,
                                                       @RequestParam(required = false) Long size,
                                                       @RequestParam(value = "isCard", required = false, defaultValue = "false") Boolean isCard,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "brandId", required = false) List<String> brandId,
                                                       @RequestParam(value = "categoryId", required = false) String categoryId,
                                                       @RequestParam(value = "releasedYear", required = false) String releasedYear,
                                                       @RequestParam(value = "startPrice", required = false) String startPrice,
                                                       @RequestParam(value = "endPrice", required = false) String endPrice,
                                                       @RequestParam(value = "label", required = false) List<String> labelId) {
        Collection<Product> productCollection;
        if (name == null && brandId == null && categoryId == null && releasedYear == null
                && startPrice == null && endPrice == null) {
            productCollection = productService.findAll(page, size);
        } else {
            Map<String, Object> params = new HashMap<>();
            if (name != null) params.put("name", name);
            if (brandId != null) params.put("brandId", brandId);
            if (categoryId != null) params.put("categoryId", categoryId);
            if (releasedYear != null) params.put("releasedYear", releasedYear);
            if (startPrice != null) params.put("startPrice", startPrice);
            if (endPrice != null) params.put("endPrice", endPrice);
            if (labelId != null) params.put("labelId", labelId);
            productCollection = productService.filter(params);
        }
        if (isCard != null && isCard) {
            return ResponseEntity.ok(productService.getProductCardDTO(productCollection));
        }
        return ResponseEntity.ok(productCollection);
    }

    @ApiOperation(value = "Get a product in system with id", response = Product.class)
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") String productId,
                                            @RequestParam(value = "isDetail", required = false, defaultValue = "false") Boolean isDetail) {
        Product product = productService.findById(productId);
        if (isDetail != null && isDetail) {
            return ResponseEntity.ok(productService.getProductDetail(product));
        }
        return ResponseEntity.ok(product);
    }

    @ApiOperation(value = "Get all products of a brand in system", response = Product.class)
    @GetMapping("/brands/{brandId}/products")
    public ResponseEntity<List<Product>> getProductOfBrand(@PathVariable("brandId") long brandId) {
        return ResponseEntity.ok(productService.findProductByBrandId(brandId));
    }

    @ApiOperation(value = "Get all products of a category in system", response = Product.class)
    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductOfCategory(@PathVariable("categoryId") long categoryId) {
        return ResponseEntity.ok(productService.findProductByCategoryId(categoryId));
    }

    @ApiOperation(value = "Get all accessories of a product", response = Product.class)
    @GetMapping("/products/{productId}/accessories")
    public ResponseEntity<Set<Product>> getAccessoryOfProduct(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(productService.findAccessoryOfProduct(productId));
    }

    @ApiOperation(value = "Get a product with full detail in system", response = ProductDetailDTO.class)
    @GetMapping("/products/{id}/details")
    public ResponseEntity<ProductDetailDTO> getProductDetail(@PathVariable("id") String productId) {
        return ResponseEntity.ok(productService.getProductDetail(productService.findById(productId)));
    }

    @ApiOperation(value = "Create a new product (product)", response = DataResponse.class)
    @PostMapping("/products")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewProduct(@RequestBody Product product) {
        return DataResponse.success(
                "Create new product",
                productService.insert(product)
        );
    }

    @ApiOperation(value = "Update all information of product (product)", response = BaseResponse.class)
    @PutMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateAllForProduct(@PathVariable("id") String productId,
                                                            @RequestBody Product product) {
        productService.update(product, productId);
        return DataResponse.success("Update product detail");
    }

    @ApiOperation(value = "Update list price and price of product (product)", response = BaseResponse.class)
    @PatchMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updatePriceOfProduct(@RequestBody ProductPriceDTO productDTO) {
        productService.updatePrice(productDTO);
        return DataResponse.success("Update product's price");
    }

    @ApiOperation(value = "Delete product from system", response = BaseResponse.class)
    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable("id") String productId) {
        productService.delete(productId);
        return DataResponse.success("Delete product");
    }

    @ApiOperation(value = "Add a discount to product", response = BaseResponse.class)
    @PostMapping("/products/{id}/discount")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> addDiscountToProduct(@PathVariable("id") String productId,
                                                             @RequestBody Map<String, Long> body) {
        productService.insertDiscount(productId, body.get("discountId"));
        return DataResponse.success("Add discount into product");
    }

    @ApiOperation(value = "Remove a discount from product", response = BaseResponse.class)
    @DeleteMapping("/products/{id}/discount")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> removeDiscountToProduct(@PathVariable("id") String productId,
                                                                @RequestBody Map<String, Long> body) {
        productService.removeDiscount(productId, body.get("discountId"));
        return DataResponse.success("Remove discount from product");
    }

    // Product
    @ApiOperation(value = "Add a accessory to product", response = BaseResponse.class)
    @PostMapping("/products/{id}/accessories")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> addAccessoryToProduct(@PathVariable("id") String productId,
                                                              @RequestBody Map<String, String> body) {
        productService.insertAccessory(productId, body.get("accessoryId"));
        return DataResponse.success("Add accessory into product");
    }

    @ApiOperation(value = "Remove a graphic card from product", response = BaseResponse.class)
    @DeleteMapping("/products/{id}/accessories")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> removeAccessoryToProduct(@PathVariable("id") String productId,
                                                                 @RequestBody Map<String, String> body) {
        productService.removeAccessory(productId, body.get("accessoryId"));
        return DataResponse.success("Remove accessory form product");
    }

    @ApiOperation(value = "Add a label to product", response = BaseResponse.class)
    @PostMapping("/products/{id}/labels")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> addLabelToProduct(@PathVariable("id") String productId,
                                                          @RequestBody Map<String, Long> body) {
        productService.insertLabel(productId, body.get("labelId"));
        return DataResponse.success("Add label into product");
    }

    @ApiOperation(value = "Remove a label from product", response = BaseResponse.class)
    @DeleteMapping("/products/{id}/labels")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> removeLabelToProduct(@PathVariable("id") String productId,
                                                             @RequestBody Map<String, Long> body) {
        productService.removeLabel(productId, body.get("labelId"));
        return DataResponse.success("Remove label from product");
    }
}
