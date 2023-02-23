package com.laptech.restapi.mapper;

import com.laptech.restapi.common.enums.DiscountType;
import com.laptech.restapi.model.Discount;
import com.laptech.restapi.util.ConvertBaseModel;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21 (update 2023-02-02)
 */
public class DiscountMapper implements RowMapper<Discount> {
    @Override
    public Discount mapRow(ResultSet rs, int rowNum) throws SQLException {
        Discount discount = new Discount();
        discount.setId(rs.getLong("id"));
        discount.setCode(rs.getString("code"));
        discount.setRate(rs.getFloat("rate"));
        discount.setAppliedType(DiscountType.valueOf(rs.getString("applied_type"))); // enum
        discount.setMaxAmount(rs.getBigDecimal("max_amount"));
        discount.setAppliedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "applied_date"));
        discount.setEndedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "ended_date"));
        discount.setData(ConvertBaseModel.getBaseModel(rs));
        return discount;
    }
}
