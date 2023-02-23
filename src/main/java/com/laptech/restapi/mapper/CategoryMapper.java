package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Category;
import com.laptech.restapi.util.ConvertBaseModel;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setName(rs.getNString("name"));
        category.setImage(rs.getString("image"));
        category.setDescription(rs.getNString("description"));
        category.setData(ConvertBaseModel.getBaseModel(rs));
        return category;
    }
}
