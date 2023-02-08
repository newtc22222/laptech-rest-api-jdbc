package com.laptech.restapi.mapper;

import com.laptech.restapi.model.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public class AddressMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setId(rs.getString("id"));
        address.setUserId(rs.getLong("user_id"));
        address.setCountry(rs.getNString("country"));
        address.setLine1(rs.getNString("line1"));
        address.setLine2(rs.getNString("line2"));
        address.setLine3(rs.getNString("line3"));
        address.setStreet(rs.getNString("street"));
        return address;
    }
}
