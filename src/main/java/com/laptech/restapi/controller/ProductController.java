package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @version 1.2
 * @since 2022-11-22 (update 2023-02-07
 */
@Api(tags = "Product CRUD apis", value = "Product controller")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "Get all products in system", response = Product.class)
    @GetMapping("/products")
    public ResponseEntity<DataResponse> getAllProduct(@RequestParam(defaultValue = "false") boolean isCard,
                                                      @RequestParam(required = false) String sortBy,
                                                      @RequestParam(required = false) String sortDir,
                                                      @RequestParam(required = false) Long page,
                                                      @RequestParam(required = false) Long size) {
        Collection<Product> products = productService.findAll(sortBy, sortDir, page, size);
        Collection<?> collection = (isCard) ? productService.getProductCardDTO(products) : products; // how?
        return DataResponse.getCollectionSuccess(
                "Get all products",
                productService.count(),
                collection
        );
    }

    @ApiOperation(value = "Get product with filter", response = Product.class)
    @GetMapping("/products/filter")
    public ResponseEntity<DataResponse> getProductWithFilter(@RequestParam(defaultValue = "false") boolean isCard,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String[] brandId,
                                                             @RequestParam(required = false) String[] categoryId,
                                                             @RequestParam(required = false) String releasedDate,
                                                             @RequestParam(required = false) Integer quantityInStock,
                                                             @RequestParam(required = false) BigDecimal listedPrice,
                                                             @RequestParam(required = false) String[] labelId,
                                                             @RequestParam(required = false) String startPrice,
                                                             @RequestParam(required = false) String endPrice,
                                                             @RequestParam(required = false) String createdDate,
                                                             @RequestParam(required = false) String modifiedDate,
                                                             @RequestParam(required = false) String deletedDate,
                                                             @RequestParam(required = false) Boolean isDel,
                                                             @RequestParam(required = false) String updateBy,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("brandId", brandId);
        params.put("categoryId", categoryId);
        params.put("releasedDate", releasedDate);
        params.put("quantityInStock", quantityInStock);
        params.put("listedPrice", listedPrice);
        params.put("labelId", labelId);
        params.put("startPrice", startPrice);
        params.put("endPrice", endPrice);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);

        Collection<Product> products = productService.findWithFilter(params);
        Collection<?> collection = (isCard) ? productService.getProductCardDTO(products) : products;

        return DataResponse.getCollectionSuccess(
                "Get product with filter",
                collection
        );
    }

    @ApiOperation(value = "Get a product in system with id", response = Product.class)
    @GetMapping("/products/{id}")
    public ResponseEntity<DataResponse> getProductById(@PathVariable("id") String productId) {
        return DataResponse.getObjectSuccess(
                "Get product",
                productService.findById(productId)
        );
    }

    @ApiOperation(value = "Get a product with id and full detail", response = Product.class)
    @GetMapping("/products/{id}/detail")
    public ResponseEntity<DataResponse> getProductByIdWithDetails(@PathVariable("id") String productId) {
        Product product = productService.findById(productId);
        return DataResponse.getObjectSuccess(
                "Get product with full detail",
                productService.getProductDetail(product)
        );
    }

    @ApiOperation(value = "Get all products of a brand in system", response = Product.class)
    @GetMapping("/brands/{brandId}/products")
    public ResponseEntity<DataResponse> getProductOfBrand(@PathVariable("brandId") long brandId,
                                                          @RequestParam(defaultValue = "false") boolean isCard) {
        Collection<Product> products = productService.findProductByBrandId(brandId);
        Collection<?> collection = (isCard) ? productService.getProductCardDTO(products) : products;

        return DataResponse.getCollectionSuccess(
                "Get product of brand",
                collection
        );
    }

    @ApiOperation(value = "Get all products of a category in system", response = Product.class)
    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<DataResponse> getProductOfCategory(@PathVariable("categoryId") long categoryId,
                                                             @RequestParam(defaultValue = "false") boolean isCard) {
        Collection<Product> products = productService.findProductByCategoryId(categoryId);
        Collection<?> collection = (isCard) ? productService.getProductCardDTO(products) : products;

        return DataResponse.getCollectionSuccess(
                "Get product of category",
                collection
        );
    }

    @ApiOperation(value = "Get all accessories of a product", response = Product.class)
    @GetMapping("/products/{productId}/accessories")
    public ResponseEntity<DataResponse> getAccessoryOfProduct(@PathVariable("productId") String productId,
                                                              @RequestParam(defaultValue = "false") boolean isCard) {
        Collection<Product> products = productService.findAccessoryOfProduct(productId);
        Collection<?> collection = (isCard) ? productService.getProductCardDTO(products) : products;

        return DataResponse.getCollectionSuccess(
                "Get accessory of product",
                collection
        );
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
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable("id") String productId,
                                                      @RequestBody(required = false) Map<String, String> body) {
        productService.delete(productId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete product");
    }

    @ApiOperation(value = "Add a discount to product", response = BaseResponse.class)
    @PostMapping("/products/{id}/discounts")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> addDiscountToProduct(@PathVariable("id") String productId,
                                                             @RequestBody Map<String, Long> body) {
        productService.insertDiscount(productId, body.get("discountId"));
        return DataResponse.success("Add discount into product");
    }

    @ApiOperation(value = "Update discounts of product", response = BaseResponse.class)
    @PutMapping("/products/{id}/discounts")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateMultipleDiscountsOfProduct(@PathVariable("id") String productId,
                                                                         @RequestBody Map<String, List<Long>> body) {
        List<Long> discountIdAddList = body.get("addList");
        List<Long> discountIdRemoveList = body.get("removeList");
        productService.updateMultipleDiscounts(productId, discountIdAddList, discountIdRemoveList);
        return DataResponse.success("Update discounts of product");
    }

    @ApiOperation(value = "Remove a discount from product", response = BaseResponse.class)
    @DeleteMapping("/products/{id}/discounts")
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

    @ApiOperation(value = "Update accessories of product", response = BaseResponse.class)
    @PutMapping("/products/{id}/accessories")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateMultipleAccessoriesOfProduct(@PathVariable("id") String productId,
                                                                           @RequestBody Map<String, List<String>> body) {
        List<String> accessoryIdAddList = body.get("addList");
        List<String> accessoryIdRemoveList = body.get("removeList");
        productService.updateMultipleAccessories(productId, accessoryIdAddList, accessoryIdRemoveList);
        return DataResponse.success("Update accessories of product");
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

    @ApiOperation(value = "Update labels of product", response = BaseResponse.class)
    @PutMapping("/products/{id}/labels")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateMultipleLabelsOfProduct(@PathVariable("id") String productId,
                                                                      @RequestBody Map<String, List<Long>> body) {
        List<Long> labelIdAddList = body.get("addList");
        List<Long> labelIdRemoveList = body.get("removeList");
        productService.updateMultipleLabels(productId, labelIdAddList, labelIdRemoveList);
        return DataResponse.success("Update labels of product");
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
