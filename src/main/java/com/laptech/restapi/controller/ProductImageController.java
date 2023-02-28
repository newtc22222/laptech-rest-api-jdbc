package com.laptech.restapi.controller;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.common.exception.InvalidArgumentException;
import com.laptech.restapi.dto.request.ProductImageDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.ProductImage;
import com.laptech.restapi.service.ProductImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Image of product", value = "ProductImage controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    @ApiOperation(value = "Get all images in system", response = ProductImage.class)
    @GetMapping("/images")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<ProductImage>> getAllImages(@RequestParam(required = false, defaultValue = "1") Long page,
                                                           @RequestParam(required = false) Long size) {
        return ResponseEntity.ok(productImageService.findAll(page, size));
    }

    @ApiOperation(value = "Get an image with id", response = ProductImage.class)
    @GetMapping("/images/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ProductImage> getImageById(@PathVariable("id") String imageId) {
        return ResponseEntity.ok(productImageService.findById(imageId));
    }

    @ApiOperation(value = "Get all images of product", response = ProductImage.class)
    @GetMapping("/products/{productId}/images")
    public ResponseEntity<Collection<ProductImage>> getProductImagesByProductId(@PathVariable("productId") String productId,
                                                                                @RequestParam(value = "type", required = false) String type) {
        if (type == null) {
            return ResponseEntity.ok(productImageService.findByProductId(productId));
        }
        return ResponseEntity.ok(productImageService.filter(productId, type));
    }

    @ApiOperation(value = "Import a new image (link)", response = DataResponse.class)
    @PostMapping("/images")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewProductImage(@RequestBody ProductImageDTO imageDTO) {
        return DataResponse.success(
                "Insert new image for product",
                productImageService.insert(ProductImageDTO.transform(imageDTO))
        );
    }

    @ApiOperation(value = "Update all information of image", response = BaseResponse.class)
    @PutMapping("/images/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateProductImage(@PathVariable("id") String imageId,
                                                           @RequestBody ProductImageDTO imageDTO) {
        productImageService.update(ProductImageDTO.transform(imageDTO), imageId);
        return DataResponse.success("Update product's image");
    }

    @ApiOperation(value = "Update path and type of image", response = BaseResponse.class)
    @PatchMapping("/images/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateProductImagePathAndType(@PathVariable("id") String imageId,
                                                                      @RequestBody Map<String, String> imageRequest) {
        String path = imageRequest.get("path");
        ImageType type;
        try {
            type = ImageType.valueOf(imageRequest.get("type"));
        } catch (IllegalArgumentException err) {
            throw new InvalidArgumentException(err.getLocalizedMessage());
        }
        productImageService.updateUrlAndType(imageId, path, type, );
        return DataResponse.success("Update product's image");
    }

    @ApiOperation(value = "Delete an image", response = BaseResponse.class)
    @DeleteMapping("/images/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteProductImage(@PathVariable("id") String imageId) {
        productImageService.delete(imageId);
        return DataResponse.success("Delete product's image");
    }
}
