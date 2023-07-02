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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(description = "Image collection of product", tags = "Product Image controller")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ProductImageController {
    private final ProductImageService productImageService;

    @ApiOperation(value = "Get all images in system", response = ProductImage.class)
    @GetMapping("/images")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getAllImages(@RequestParam(required = false) String sortBy,
                                                     @RequestParam(required = false) String sortDir,
                                                     @RequestParam(required = false) Long page,
                                                     @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all images",
                productImageService.count(),
                productImageService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get image with filter", response = ProductImage.class)
    @GetMapping("/images/filter")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getImageWithFilter(@RequestParam(required = false) String productId,
                                                           @RequestParam(required = false) String feedbackId,
                                                           @RequestParam(required = false) String url,
                                                           @RequestParam(required = false) String type,
                                                           @RequestParam(required = false) String createdDate,
                                                           @RequestParam(required = false) String modifiedDate,
                                                           @RequestParam(required = false) String deletedDate,
                                                           @RequestParam(required = false) Boolean isDel,
                                                           @RequestParam(required = false) String updateBy,
                                                           @RequestParam(required = false) String sortBy,
                                                           @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("feedbackId", feedbackId);
        params.put("url", url);
        params.put("type", type);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);
        return DataResponse.getCollectionSuccess(
                "Get all images",
                productImageService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get an image with id", response = ProductImage.class)
    @GetMapping("/images/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getImageById(@PathVariable("id") String imageId) {
        return DataResponse.getObjectSuccess(
                "Get image",
                productImageService.findById(imageId)
        );
    }

    @ApiOperation(value = "Get all images of product", response = ProductImage.class)
    @GetMapping("/products/{productId}/images")
    public ResponseEntity<DataResponse> getProductImagesByProductId(@PathVariable("productId") String productId,
                                                                    @RequestParam(required = false) String type) {
        Collection<ProductImage> collection = (type != null)
                ? productImageService.filter(productId, type)
                : productImageService.findByProductId(productId);
        return DataResponse.getCollectionSuccess(
                "Get image of product",
                collection
        );
    }

    @ApiOperation(value = "Update images of product", response = BaseResponse.class)
    @PutMapping("/products/{productId}/images")
    public ResponseEntity<BaseResponse> updateMultipleProductImages(@PathVariable("productId") String productId,
                                                                    @RequestBody Map<String, List<ProductImage>> body) {
        List<ProductImage> imageAddList = body.get("addList").stream().peek(image -> image.setProductId(productId)).collect(Collectors.toList());
        List<ProductImage> imageUpdateList = body.get("updateList");
        List<String> imageIdRemoveList = body.get("removeList").stream().map(ProductImage::getId).collect(Collectors.toList());
        productImageService.updateMultipleProductImages(imageAddList, imageUpdateList, imageIdRemoveList);
        return DataResponse.success("Update multiple images of product");
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
                                                                      @RequestBody Map<String, String> body) {
        String url = body.get("url");
        ImageType type;
        try {
            type = ImageType.valueOf(body.get("type"));
        } catch (IllegalArgumentException err) {
            throw new InvalidArgumentException(err.getLocalizedMessage());
        }
        productImageService.updateUrlAndType(imageId, url, type, body.get("updateBy"));
        return DataResponse.success("Update product's image");
    }

    @ApiOperation(value = "Delete an image", response = BaseResponse.class)
    @DeleteMapping("/images/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteProductImage(@PathVariable("id") String imageId,
                                                           @RequestBody(required = false) Map<String, String> body) {
        productImageService.delete(imageId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete product's image");
    }
}
