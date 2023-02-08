package com.laptech.restapi.mapper;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.model.User;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getNString("name"));
        user.setGender(Gender.valueOf(rs.getString("gender")));
        user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setActive(rs.getBoolean("is_active"));
        user.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        user.setModifiedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "modified_date"));
        return user;
    }
}
