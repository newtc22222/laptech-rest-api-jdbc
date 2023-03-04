package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dao.ProductImageDAO;
import com.laptech.restapi.dto.filter.ProductImageFilter;
import com.laptech.restapi.model.ProductImage;
import com.laptech.restapi.service.ProductImageService;
import com.laptech.restapi.util.ConvertMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageDAO productImageDAO;

    @Autowired
    private ProductDAO productDAO;

    @Override
    public ProductImage insert(ProductImage productImage) {
        // check

        if (productImageDAO.isExists(productImage)) {
            throw new ResourceAlreadyExistsException("[Info] This image has already existed in system!");
        }
        String newImageId = productImageDAO.insert(productImage);
        if (newImageId == null) {
            throw new InternalServerErrorException("[Error] Failed to insert new image!");
        }
        return productImageDAO.findById(newImageId);
    }

    @Override
    public void update(ProductImage productImage, String imageId) {
        // check

        ProductImage oldImage = productImageDAO.findById(imageId);
        if (oldImage == null) {
            throw new ResourceNotFoundException("[Info] Cannot find image with id=" + imageId);
        } else {
            oldImage.setProductId(productImage.getProductId());
            oldImage.setFeedbackId(productImage.getFeedbackId());
            oldImage.setUrl(productImage.getUrl());
            oldImage.setType(productImage.getType());
            oldImage.setUpdateBy(productImage.getUpdateBy());

            if (productImageDAO.update(oldImage) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this image!");
            }
        }
    }

    @Override
    public void delete(String imageId, String updateBy) {
        if (productImageDAO.findById(imageId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find image with id=" + imageId);
        } else {
            if (productImageDAO.delete(imageId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this image!");
            }
        }
    }

    @Override
    public long count() {
        return productImageDAO.count();
    }

    @Override
    public Collection<ProductImage> findAll(String sortBy, String sortDir, Long page, Long size) {
        return productImageDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<ProductImage> findWithFilter(Map<String, Object> params) {
        ProductImageFilter filter = new ProductImageFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return productImageDAO.findWithFilter(filter);
    }

    @Override
    public ProductImage findById(String imageId) {
        ProductImage image = productImageDAO.findById(imageId);
        if (image == null) {
            throw new ResourceNotFoundException("[Info] Cannot find image with id=" + imageId);
        }
        return image;
    }

    @Override
    public void updateUrlAndType(String imageId, String url, ImageType type, String updateBy) {
        // check

        if (productImageDAO.findById(imageId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find image with id=" + imageId);
        } else {
            if (productImageDAO.updateUrlAndType(imageId, url, type, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this image!");
            }
        }
    }

    @Override
    public Collection<ProductImage> findByProductId(String productId) {
        if (productDAO.findById(productId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        return productImageDAO.findProductImageByProductId(productId);
    }

    @Override
    public Set<ProductImage> filter(String productId, String imageType) {
        Collection<ProductImage> imageCollectionByProduct = productImageDAO.findProductImageByProductId(productId);
        Collection<ProductImage> imageCollectionByType = productImageDAO.findProductImageByImageType(ImageType.valueOf(imageType));
        imageCollectionByProduct.removeIf(item -> !imageCollectionByType.contains(item));
        return (Set<ProductImage>) imageCollectionByProduct;
    }
}
