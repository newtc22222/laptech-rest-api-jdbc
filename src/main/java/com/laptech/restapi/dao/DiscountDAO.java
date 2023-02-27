package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.DiscountFilter;
import com.laptech.restapi.model.Discount;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface DiscountDAO extends BaseDAO<Discount, DiscountFilter, Long> {
    /**
     * Get discount is using in current time
     */
    Discount findDiscountByProductIdUseInDate(String productId);

    Collection<Discount> findDiscountByProductId(String productId);

    Collection<Discount> findDiscountByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}

