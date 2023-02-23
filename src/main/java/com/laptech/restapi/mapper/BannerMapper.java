package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Banner;
import com.laptech.restapi.util.ConvertBaseModel;
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
        banner.setType(rs.getNString("type"));
        banner.setTitle(rs.getNString("title"));
        banner.setLinkProduct(rs.getString("link_product"));
        banner.setUsedDate(rs.getDate("used_date").toLocalDate());
        banner.setEndedDate(rs.getDate("ended_date").toLocalDate());
        banner.setData(ConvertBaseModel.getBaseModel(rs));
        return banner;
    }
}
