package com.laptech.restapi.mapper;

import com.laptech.restapi.model.ProductUnit;
import com.laptech.restapi.util.ConvertBaseModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class ProductUnitMapper implements RowMapper<ProductUnit> {
    @Override
    public ProductUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductUnit unit = new ProductUnit();
        unit.setId(rs.getString("id"));
        unit.setCartId(rs.getString("cart_id"));
        unit.setInvoiceId(rs.getString("invoice_id"));
        unit.setProductId(rs.getString("product_id"));
        unit.setProductName(rs.getString("product_name"));
        unit.setQuantity(rs.getInt("quantity"));
        unit.setPrice(rs.getBigDecimal("price"));
        unit.setDiscountPrice(rs.getBigDecimal("discount_price"));
        unit.setData(ConvertBaseModel.getBaseModel(rs));
        return unit;
    }
}
