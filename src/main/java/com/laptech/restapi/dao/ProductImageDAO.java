package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.model.ProductImage;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ProductImageDAO extends BaseDAO<ProductImage, String> {
    int updatePathAndType(String imageId, String path, ImageType type);

    List<ProductImage> findProductImageByProductId(String productId);

    List<ProductImage> findProductImageByImageType(ImageType type);

    List<ProductImage> findProductImageByProductIdAndImageType(String productId, ImageType type);
}
