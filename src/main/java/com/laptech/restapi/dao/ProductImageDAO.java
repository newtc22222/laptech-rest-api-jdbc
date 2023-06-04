package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.dto.filter.ProductImageFilter;
import com.laptech.restapi.model.ProductImage;

import java.util.Collection;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ProductImageDAO extends BaseDAO<ProductImage, ProductImageFilter, String> {
    Collection<String> updateMultipleImages(List<ProductImage> imageIdAddList, List<ProductImage> imageUpdateList, List<String> imageIdRemoveList);

    int updateUrlAndType(String imageId, String url, ImageType type, String updateBy);

    Collection<ProductImage> findProductImageByProductId(String productId);

    Collection<ProductImage> findProductImageByImageType(ImageType type);
}
