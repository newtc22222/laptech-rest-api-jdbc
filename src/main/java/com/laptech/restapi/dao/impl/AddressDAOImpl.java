package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.AddressDAO;
import com.laptech.restapi.dto.filter.AddressFilter;
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

import java.util.Collection;
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

    @Value("${sp_InsertNewAddress}")
    private String INSERT;
    @Value("${sp_UpdateAddress}")
    private String UPDATE;
    @Value("${sp_SetDefaultAddressOfUser}")
    private String UPDATE_DEFAULT_ADDRESS;
    @Value("${sp_DeleteAddress}")
    private String DELETE;

    @Value("${sp_FindAllAddress}")
    private String QUERY_ALL;
    @Value("${sp_FindAddressById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindAddressByUserId}")
    private String QUERY_ALL_BY_USER_ID;
    @Value("${sp_CheckExistAddress}")
    private String QUERY_CHECK_EXISTS;

    @Value("${sp_CountAllAddress}")
    private String COUNT_ALL;
    @Value("${sp_CountAddressWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public String insert(Address address) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    String.class,
                    address.getId(),
                    address.getUserId(),
                    address.getCountry(),
                    address.getLine1(),
                    address.getLine2(),
                    address.getLine3(),
                    address.getStreet(),
                    address.isDefault(),
                    address.getUpdateBy()
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
                    address.isDefault(),
                    address.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String addressId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    addressId,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long count() {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_ALL,
                    Long.class
            );
            return Objects.requireNonNull(count);
        } catch (DataAccessException | NullPointerException err) {
            log.warn("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(AddressFilter filter) {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_WITH_CONDITION,
                    Long.class,
                    filter.getObject(false)
            );
            return Objects.requireNonNull(count);
        } catch (DataAccessException | NullPointerException err) {
            log.warn("[COUNT WITH CONDITION] {}", err.getLocalizedMessage());
            return 0;
        }
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
    public Collection<Address> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new AddressMapper(),
                    pagingOption.getObject()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Address> findWithFilter(AddressFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new AddressMapper(),
                    filter.getObject(true)
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int setDefaultAddress(String addressId, long userId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    UPDATE_DEFAULT_ADDRESS,
                    addressId,
                    userId,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE DEFAULT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    /**
     * Get all address of 1 user (show detail)
     */
    @Override
    public Collection<Address> findAddressByUserId(long userId) {
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
    public Address findById(String addressId) {
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
