package com.laptech.restapi.service;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.model.Discount;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface DiscountService extends BaseService<Discount, Long> {
    List<Discount> getDiscountsOfProduct(String productId);

    Set<Discount> filter(Map<String, String> params);
}
