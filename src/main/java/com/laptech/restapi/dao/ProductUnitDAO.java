package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.ProductUnitFilter;
import com.laptech.restapi.model.ProductUnit;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ProductUnitDAO extends BaseDAO<ProductUnit, ProductUnitFilter, String> {
    int updateProductUnitProperties(String itemId, int quantity, BigDecimal price, BigDecimal discountPrice, String updateBy);

    Collection<ProductUnit> findProductUnitByCartId(String cartId);

    Collection<ProductUnit> findProductUnitByInvoiceId(String invoiceId);
}
