package com.laptech.restapi.mapper;

import com.laptech.restapi.model.RefreshToken;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RefreshTokenMapper implements RowMapper<RefreshToken> {

    @Override
    public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        RefreshToken token = new RefreshToken();
        token.setCode(rs.getString("code"));
        token.setUserId(rs.getLong("user_id"));
        token.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        token.setExpiredDate(ConvertDateTime.getDateTimeFromResultSet(rs, "expired_date"));
        return token;
    }
}
