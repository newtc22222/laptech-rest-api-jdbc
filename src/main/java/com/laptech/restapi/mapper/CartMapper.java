package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Cart;
import com.laptech.restapi.util.ConvertBaseModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class CartMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getString("id"));
        cart.setUserId(rs.getLong("user_id"));
        cart.setDiscountId(rs.getLong("discount_id"));
        cart.setData(ConvertBaseModel.getBaseModel(rs));
        return cart;
    }
}
