package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Product;
import com.laptech.restapi.util.ConvertBaseModel;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (change 2023-02-02)
 */
public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getString("id"));
        product.setBrandId(rs.getLong("brand_id"));
        product.setCategoryId(rs.getLong("category_id"));
        product.setName(rs.getNString("name"));
        product.setReleasedDate(rs.getDate("released_date").toLocalDate());
        product.setQuantityInStock(rs.getInt("quantity_in_stock"));
        product.setListedPrice(rs.getBigDecimal("listed_price"));
        product.setSpecifications(rs.getNString("specifications"));
        product.setDescriptionDetail(rs.getNString("description_detail"));
        product.setData(ConvertBaseModel.getBaseModel(rs));
        return product;
    }
}
