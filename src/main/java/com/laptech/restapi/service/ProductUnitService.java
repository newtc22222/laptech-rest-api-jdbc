package com.laptech.restapi.service;

import com.laptech.restapi.model.ProductUnit;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface ProductUnitService extends BaseService<ProductUnit, String> {
    void updateProductUnitProperties(String unitId, int quantity, BigDecimal price, BigDecimal discountPrice);

    List<ProductUnit> getProductUnitByCartId(String cartId);

    List<ProductUnit> getProductUnitByInvoiceId(String invoiceId);
}
