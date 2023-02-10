package com.laptech.restapi.dao;

import com.laptech.restapi.dto.request.ProductPriceDTO;
import com.laptech.restapi.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface ProductDAO extends BaseDAO<Product, String> {
    int updatePrice(ProductPriceDTO productPriceDTO);

    List<Product> findAccessoryByProductId(String productId);

    // For product searching...
    List<Product> findProductByName(String name);

    List<Product> findProductByBrandId(long brandId);

    List<Product> findProductByCategoryId(long categoryId);

    List<Product> findProductByReleasedYear(int year);

    List<Product> findProductByPriceRange(BigDecimal startPrice, BigDecimal endPrice);

    List<Product> findProductByLabel(long labelId);
}
