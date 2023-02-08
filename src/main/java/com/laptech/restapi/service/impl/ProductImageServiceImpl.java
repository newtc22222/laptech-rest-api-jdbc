package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.ProductDAO;
import com.laptech.restapi.dao.ProductImageDAO;
import com.laptech.restapi.model.ProductImage;
import com.laptech.restapi.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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

            if (productImageDAO.update(oldImage) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this image!");
            }
        }
    }

    @Override
    public void delete(String imageId) {
        if (productImageDAO.findById(imageId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find image with id=" + imageId);
        } else {
            if (productImageDAO.delete(imageId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this image!");
            }
        }
    }

    @Override
    public List<ProductImage> findAll(Long page, Long size) {
        if (size == null)
            return productImageDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return productImageDAO.findAll(limit, skip);
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
    public void updatePathAndType(String imageId, String path, ImageType type) {
        // check

        if (productImageDAO.findById(imageId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find image with id=" + imageId);
        } else {
            if (productImageDAO.updatePathAndType(imageId, path, type) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this image!");
            }
        }
    }

    @Override
    public List<ProductImage> findByProductId(String productId) {
        if (productDAO.findById(productId) == null)
            throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
        return productImageDAO.findProductImageByProductId(productId);
    }

    @Override
    public Set<ProductImage> filter(String productId, String imageType) {
        List<ProductImage> images = null;
        if (productId != null || imageType != null) {
            if (productId == null) {
                images = productImageDAO.findProductImageByImageType(ImageType.valueOf(imageType));
            } else if (imageType == null) {
                if (productDAO.findById(productId) == null)
                    throw new ResourceNotFoundException("[Info] Cannot find product with id=" + productId);
                images = productImageDAO.findProductImageByProductId(productId);
            } else {
                images = productImageDAO.findProductImageByProductIdAndImageType(
                        productId,
                        ImageType.valueOf(imageType)
                );
            }
        }
        return (images != null) ? new HashSet<>(images) : null;
    }
}
