package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.BaseFilter;
import com.laptech.restapi.model.Cart;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
public interface CartDAO extends BaseDAO<Cart, BaseFilter, String> {
    Cart findCartByUserId(long userId);
}
