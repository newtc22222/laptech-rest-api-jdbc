package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Banner;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class BannerMapper implements RowMapper<Banner> {
    @Override
    public Banner mapRow(ResultSet rs, int rowNum) throws SQLException {
        Banner banner = new Banner();
        banner.setId(rs.getLong("id"));
        banner.setPath(rs.getString("path"));
        banner.setType(rs.getString("type"));
        banner.setUsedDate(rs.getDate("used_date").toLocalDate());
        banner.setEndedDate(rs.getDate("ended_date").toLocalDate());
        banner.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        banner.setModifiedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "modified_date"));
        return banner;
    }
}
