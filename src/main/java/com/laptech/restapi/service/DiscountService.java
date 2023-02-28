package com.laptech.restapi.service;

import com.laptech.restapi.model.Discount;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface DiscountService extends BaseService<Discount, Long> {
    Collection<Discount> getDiscountsOfProduct(String productId);
}
