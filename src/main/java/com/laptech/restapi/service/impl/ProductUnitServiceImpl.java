package com.laptech.restapi.service.impl;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceAlreadyExistsException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.dao.CartDAO;
import com.laptech.restapi.dao.InvoiceDAO;
import com.laptech.restapi.dao.ProductUnitDAO;
import com.laptech.restapi.model.ProductUnit;
import com.laptech.restapi.service.ProductUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitDAO productUnitDAO;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private InvoiceDAO invoiceDAO;

    @Override
    public ProductUnit insert(ProductUnit productUnit) {
        // check

        if (productUnitDAO.isExists(productUnit)) {
            throw new ResourceAlreadyExistsException("[Info] This unit has already existed in system!");
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

            if (productUnitDAO.update(oldUnit) == 0) {
                throw new InternalServerErrorException("[Error] Failed to update this unit!");
            }
        }
    }

    @Override
    public void delete(String unitId) {
        if (productUnitDAO.findById(unitId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find unit with id=" + unitId);
        } else {
            if (productUnitDAO.delete(unitId) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this unit!");
            }
        }
    }

    @Override
    public List<ProductUnit> findAll(Long page, Long size) { // maybe useless
        if (size == null)
            return productUnitDAO.findAll();
        long limit = size;
        long skip = size * (page - 1);
        return productUnitDAO.findAll(limit, skip);
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
    public void updateProductUnitProperties(String unitId, int quantity, BigDecimal price, BigDecimal discountPrice) {
        // check

        if (productUnitDAO.findById(unitId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find unit with id=" + unitId);
        } else {
            if (productUnitDAO.updateProductUnitProperties(unitId, quantity, price, discountPrice) == 0) {
                throw new InternalServerErrorException("[Error] Failed to remove this unit!");
            }
        }
    }

    @Override
    public List<ProductUnit> getProductUnitByCartId(String cartId) {
        if (cartDAO.findById(cartId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find cart with id=" + cartId);
        }
        return productUnitDAO.findProductUnitByCartId(cartId);
    }

    @Override
    public List<ProductUnit> getProductUnitByInvoiceId(String invoiceId) {
        if (invoiceDAO.findById(invoiceId) == null) {
            throw new ResourceNotFoundException("[Info] Cannot find invoice with id=" + invoiceId);
        }
        return productUnitDAO.findProductUnitByInvoiceId(invoiceId);
    }
}
