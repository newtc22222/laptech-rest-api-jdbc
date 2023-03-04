package com.laptech.restapi.mapper;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.model.ProductImage;
import com.laptech.restapi.util.ConvertBaseModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class ProductImageMapper implements RowMapper<ProductImage> {
    @Override
    public ProductImage mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductImage image = new ProductImage();
        image.setId(rs.getString("id"));
        image.setProductId(rs.getString("product_id"));
        image.setFeedbackId(rs.getString("feedback_id"));
        image.setUrl(rs.getString("url"));
        image.setType(ImageType.valueOf(rs.getString("type")));
        image.setData(ConvertBaseModel.getBaseModel(rs));
        return image;
    }
}
