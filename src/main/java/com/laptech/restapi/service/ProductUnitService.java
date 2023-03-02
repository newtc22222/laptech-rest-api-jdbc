package com.laptech.restapi.service;

import com.laptech.restapi.dto.response.ProductUnitCardDTO;
import com.laptech.restapi.model.ProductUnit;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface ProductUnitService extends BaseService<ProductUnit, String> {
    void updateProductUnitProperties(String unitId, int quantity, BigDecimal price, BigDecimal discountPrice, String updateBy);

    Collection<ProductUnit> getProductUnitByCartId(String cartId);

    Collection<ProductUnit> getProductUnitByInvoiceId(String invoiceId);
}
