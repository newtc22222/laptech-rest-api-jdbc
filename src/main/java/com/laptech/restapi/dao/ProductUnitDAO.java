package com.laptech.restapi.dao;

import com.laptech.restapi.model.ProductUnit;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ProductUnitDAO extends BaseDAO<ProductUnit, String> {
    int updateProductUnitProperties(String itemId, int quantity, BigDecimal price, BigDecimal discountPrice);

    List<ProductUnit> findProductUnitByCartId(String cartId);

    List<ProductUnit> findProductUnitByInvoiceId(String invoiceId);
}
