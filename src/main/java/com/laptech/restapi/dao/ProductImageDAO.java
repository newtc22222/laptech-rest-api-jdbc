package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.dto.filter.ProductImageFilter;
import com.laptech.restapi.model.ProductImage;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ProductImageDAO extends BaseDAO<ProductImage, ProductImageFilter, String> {
    int updateUrlAndType(String imageId, String url, ImageType type);

    Collection<ProductImage> findProductImageByProductId(String productId);

    Collection<ProductImage> findProductImageByImageType(ImageType type);
}
