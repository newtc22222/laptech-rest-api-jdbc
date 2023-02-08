package com.laptech.restapi.service;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.model.ProductImage;

import java.util.List;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface ProductImageService extends BaseService<ProductImage, String> {
    void updatePathAndType(String imageId, String path, ImageType type);

    List<ProductImage> findByProductId(String productId);

    Set<ProductImage> filter(String productId, String imageType);
}
