package com.laptech.restapi.dao;

import com.laptech.restapi.common.enums.DiscountType;
import com.laptech.restapi.dto.filter.DiscountFilter;
import com.laptech.restapi.model.Discount;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface DiscountDAO extends BaseDAO<Discount, DiscountFilter, Long> {
    /**
     * Get discount is using in current time
     */
    Discount findDiscountByProductIdUseInDate(String productId);

    List<Discount> findDiscountByCode(String code);

    List<Discount> findDiscountByProductId(String productId);

    List<Discount> findDiscountByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Discount> findDiscountByType(DiscountType type);
}

