package com.laptech.restapi.mapper;

import com.laptech.restapi.model.ResetPassword;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordMapper implements RowMapper<ResetPassword> {
    @Override
    public ResetPassword mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setId(rs.getLong("id"));
        resetPassword.setUserId(rs.getLong("user_id"));
        resetPassword.setToken(rs.getString("token"));
        resetPassword.setExpiredTime(ConvertDateTime.getDateTimeFromResultSet(rs, "expired_time"));
        resetPassword.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        return resetPassword;
    }
}
