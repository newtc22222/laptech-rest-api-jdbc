package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Brand;
import com.laptech.restapi.util.ConvertBaseModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class BrandMapper implements RowMapper<Brand> {
    @Override
    public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getLong("id"));
        brand.setName(rs.getString("name"));
        brand.setCountry(rs.getString("country"));
        brand.setEstablishDate(rs.getDate("establish_date").toLocalDate());
        brand.setLogo(rs.getString("logo"));
        brand.setData(ConvertBaseModel.getBaseModel(rs));
        return brand;
    }
}
