package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Role;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getNString("name"));
        role.setDescription(rs.getNString("description"));
        role.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        role.setModifiedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "modified_date"));
        return role;
    }
}
