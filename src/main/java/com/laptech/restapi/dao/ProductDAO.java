package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.ProductFilter;
import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.model.Product;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ProductDAO extends BaseDAO<Product, ProductFilter, String> {
    int updatePrice(ProductPriceDTO productPriceDTO, String updateBy);

    Collection<Product> findAccessoryByProductId(String productId);

    Collection<Product> findProductByBrandId(long brandId);

    Collection<Product> findProductByCategoryId(long categoryId);

    Collection<Product> findProductByLabelId(long labelId);

    Collection<Product> findProductByReleasedYear(int year);

    Collection<Product> findProductByPriceRange(BigDecimal startPrice, BigDecimal endPrice);

}
