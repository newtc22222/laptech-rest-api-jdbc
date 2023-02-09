package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.AddressDAO;
import com.laptech.restapi.mapper.AddressMapper;
import com.laptech.restapi.model.Address;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-20
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class AddressDAOImpl implements AddressDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "tbl_address";
    private final String INSERT = String.format("insert into %s values (0, ?, ?, ?, ?,?, ?, ?)", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set user_id=?, country=?, line1=?, line2=?, line3=?, street=?, is_default=? where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_CHECK_EXISTS = String.format("select * from %s " +
            "where user_id=? and country=? and line1=? and line2=? and line3=? and street=?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_ALL_BY_USER_ID = String.format("select * from %s where user_id=?", TABLE_NAME);

    @Override
    public Long insert(Address address) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, address.getUserId());
                ps.setNString(2, address.getCountry());
                ps.setNString(3, address.getLine1());
                ps.setNString(4, address.getLine2());
                ps.setNString(5, address.getLine3());
                ps.setNString(6, address.getStreet());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Address address) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    address.getUserId(),
                    address.getCountry(),
                    address.getLine1(),
                    address.getLine2(),
                    address.getLine3(),
                    address.getStreet(),
                    address.isDefault(),
                    address.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(Long addressId) {
        try {
            return jdbcTemplate.update(DELETE, addressId);
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int count() {
        return this.findAll().size();
    }

    @Override
    public boolean isExists(Address address) {
        try {

            Address existAddress =
                    jdbcTemplate.queryForObject(
                            QUERY_CHECK_EXISTS,
                            new AddressMapper(),
                            address.getUserId(),
                            address.getCountry(),
                            address.getLine1(),
                            address.getLine2(),
                            address.getLine3(),
                            address.getStreet(),
                            address.isDefault()
                    );
            return existAddress != null;
        } catch (DataAccessException err) {
            log.warn(err);
            return false;
        }
    }

    @Override
    public List<Address> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new AddressMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Address> findAll(long limit, long skip) {
        try {

            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new AddressMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    /**
     * Get all address of 1 user (show detail)
     */
    @Override
    public List<Address> findAddressByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_BY_USER_ID,
                    new AddressMapper(),
                    userId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public Address findById(Long addressId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new AddressMapper(),
                    addressId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }
}
