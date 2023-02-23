package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Feedback;
import com.laptech.restapi.util.ConvertBaseModel;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class FeedbackMapper implements RowMapper<Feedback> {
    @Override
    public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setId(rs.getString("id"));
        feedback.setProductId(rs.getString("product_id"));
        feedback.setUserId(rs.getLong("user_id"));
        feedback.setContent(rs.getNString("content"));
        feedback.setRatingPoint(rs.getByte("rating_point"));
        feedback.setData(ConvertBaseModel.getBaseModel(rs));
        return feedback;
    }
}
