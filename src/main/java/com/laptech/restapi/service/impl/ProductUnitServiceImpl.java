package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.*;
import com.laptech.restapi.dto.filter.ProductUnitFilter;
import com.laptech.restapi.dto.response.ProductUnitCardDTO;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.ProductImage;
import com.laptech.restapi.model.ProductUnit;
import com.laptech.restapi.service.ProductUnitService;
import com.laptech.restapi.util.ConvertMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@RequiredArgsConstructor
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    private final ProductUnitDAO productUnitDAO;
    private final CartDAO cartDAO;
    private final InvoiceDAO invoiceDAO;
    private final DiscountDAO discountDAO;
    private final ProductDAO productDAO;
    private final ProductImageDAO productImageDAO;

    @Override
    public ProductUnit insert(ProductUnit productUnit) {
         if (productUnitDAO.isExists(productUnit)) {
             throw new ResourceAlreadyExistsException("[Info] This unit has already existed in system!");
         }

         if(productUnit.getProductName() == null) {
             Product product = productDAO.findById(productUnit.getProductId());
             productUnit.setProductName(product.getName());
         }
         if (productUnit.getCartId() != null) {
             List<ProductUnit> itemInCart = new ArrayList<>(productUnitDAO.findProductUnitByCartId(productUnit.getCartId()));
             ProductUnit currentUnit = itemInCart.stream()
                     .filter(unit -> unit.getProductId().equals(productUnit.getProductId()))
                     .findAny()
                     .orElse(null);
             if(currentUnit != null) {
                currentUnit.setQuantity(currentUnit.getQuantity() + productUnit.getQuantity());
                productUnitDAO.update(currentUnit);
                return productUnitDAO.findById(currentUnit.getId());
             } else {
                 String newUnitId = productUnitDAO.insert(productUnit);
                 if (newUnitId == null) {
                     throw new InternalServerErrorException("[Error] Failed to insert new product unit");
                 }
                 return productUnitDAO.findById(newUnitId);
             }
         }
         String newUnitId = productUnitDAO.insert(productUnit);
         if (newUnitId == null) {
             throw new InternalServerErrorException("[Error] Failed to insert new product unit");
         }
        return productUnitDAO.findById(newUnitId);
    }

    @Override
    public void update(ProductUnit productUnit, String unitId) {
        // check

        ProductUnit oldUnit = productUnitDAO.findById(unitId);
        if (oldUnit == null) {
            throw new ResourceNotFoundException("[Info] Cannot find unit with id=" + unitId);
        } else {
            oldUnit.setProductId(productUnit.getProductId());
            oldUnit.setCartId(productUnit.getCartId());
            oldUnit.setInvoiceId(productUnit.getInvoiceId());
            oldUnit.setQuantity(productUnit.getQuantity());
            oldUnit.setPrice(productUnit.getPrice());
            oldUnit.setDiscountPrice(productUnit.getDiscountPrice());
            oldUnit.setUpdateBy(productUnit.getUpdateBy());

            if (productUnitDAO.update(oldUnit) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this unit!");
            }
        }
    }

    @Override
    public void delete(String unitId, String updateBy) {
        if (productUnitDAO.findById(unitId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find unit with id=" + unitId);
        } else {
            if (productUnitDAO.delete(unitId, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this unit!");
            }
        }
    }

    @Override
    public long count() {
        return productUnitDAO.count();
    }

    @Override
    public Collection<ProductUnit> findAll(String sortBy, String sortDir, Long page, Long size) { // maybe useless
        return productUnitDAO.findAll(new PagingOptionDTO(sortBy, sortDir, page, size));
    }

    @Override
    public Collection<ProductUnit> findWithFilter(Map<String, Object> params) {
        ProductUnitFilter filter = new ProductUnitFilter(ConvertMap.changeAllValueFromObjectToString(params));
        return productUnitDAO.findWithFilter(filter);
    }

    @Override
    public ProductUnit findById(String unitId) {
        ProductUnit unit = productUnitDAO.findById(unitId);
        if (unit == null) {
            throw new ResourceNotFoundException("[Info] Cannot find product unit with id=" + unitId);
        }
        return unit;
    }

    @Override
    public void updateProductUnitProperties(String unitId, int quantity, BigDecimal price, BigDecimal discountPrice, String updateBy) {
        // check

        if (productUnitDAO.findById(unitId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find unit with id=" + unitId);
        } else {
            if (productUnitDAO.updateProductUnitProperties(unitId, quantity, price, discountPrice, updateBy) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this unit!");
            }
        }
    }

    @Override
    public Collection<ProductUnit> getProductUnitByCartId(String cartId) {
        if (cartDAO.findById(cartId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find cart with id=" + cartId);
        }
        return productUnitDAO.findProductUnitByCartId(cartId);
    }

    @Override
    public Collection<ProductUnit> getProductUnitByInvoiceId(String invoiceId) {
        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        }
        return productUnitDAO.findProductUnitByInvoiceId(invoiceId);
    }

    @Override
    public Collection<ProductUnitCardDTO> getProductUnitCardCollection(Collection<ProductUnit> collection) {
        return collection.stream().map(item -> {
            ProductUnitCardDTO dto = new ProductUnitCardDTO();
            dto.setId(item.getId());
            dto.setCartId(item.getCartId());
            dto.setInvoiceId(item.getInvoiceId());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            dto.setDiscountPrice(item.getDiscountPrice());
            dto.setDiscounts(discountDAO.findDiscountByProductId(item.getProductId()));
            dto.setProduct(productDAO.findById(item.getProductId()));

            List<ProductImage> images = productImageDAO
                    .findProductImageByProductId(item.getProductId())
                    .stream()
                    .filter(image -> image.getType() == ImageType.ADVERTISE)
                    .collect(Collectors.toList());
            dto.setImageRepresent(images.get(0).getUrl());

            return dto;
        }).collect(Collectors.toList());
    }
}
