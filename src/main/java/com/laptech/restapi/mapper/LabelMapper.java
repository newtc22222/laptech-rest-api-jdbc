package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Label;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
public class LabelMapper implements RowMapper<Label> {
    @Override
    public Label mapRow(ResultSet rs, int rowNum) throws SQLException {
        Label label = new Label();
        label.setId(rs.getLong("id"));
        label.setName(rs.getNString("name"));
        label.setIcon(rs.getString("icon"));
        label.setTitle(rs.getNString("title"));
        label.setDescription(rs.getNString("description"));
        label.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        label.setModifiedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "modified_date"));
        return label;
    }
}
