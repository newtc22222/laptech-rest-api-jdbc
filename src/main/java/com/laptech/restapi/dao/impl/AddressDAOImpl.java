package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.AddressDAO;
import com.laptech.restapi.mapper.AddressMapper;
import com.laptech.restapi.model.Address;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Value("${sp_InsertNewAddress}")
    private String INSERT;
    @Value("${sp_UpdateAddress}")
    private String UPDATE;
    @Value("${sp_DeleteAddress}")
    private String DELETE;

    @Value("${sp_FindAllAddress}")
    private String QUERY_ALL;
    private String QUERY_LIMIT; // useless
    @Value("${sp_FindAddressById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindAddressByUserId}")
    private String QUERY_ALL_BY_USER_ID;
    private final String QUERY_CHECK_EXISTS = String.format("select * from %s " +
            "where user_id=? and country=? and line1=? and line2=? and line3=? and street=?", "tbl_address");

    @Override
    public Long insert(Address address) {
        try {
            return jdbcTemplate.queryForObject(
                            INSERT,
                            Long.class,
                            address.getUserId(),
                            address.getCountry(),
                            address.getLine1(),
                            address.getLine2(),
                            address.getLine3(),
                            address.getStreet(),
                            address.isDefault()
                    );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Address address) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    address.getId(),
                    address.getUserId(),
                    address.getCountry(),
                    address.getLine1(),
                    address.getLine2(),
                    address.getLine3(),
                    address.getStreet(),
                    address.isDefault()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long addressId) {
        try {
            return jdbcTemplate.update(DELETE, addressId);
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
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
            log.warn("[CHECK EXITS] {}", err.getLocalizedMessage());
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
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
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
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
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
            log.warn("[FIND BY USER ID] {}", err.getLocalizedMessage());
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
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
