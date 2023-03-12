package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.DiscountDAO;
import com.laptech.restapi.dto.filter.DiscountFilter;
import com.laptech.restapi.mapper.DiscountMapper;
import com.laptech.restapi.model.Discount;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class DiscountDAOImpl implements DiscountDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewDiscount}")
    private String INSERT;
    @Value("${sp_UpdateDiscount}")
    private String UPDATE;
    @Value("${sp_DeleteDiscount}")
    private String DELETE;
    @Value("${sp_FindAllDiscounts}")
    private String QUERY_ALL;
    @Value("${sp_FindDiscountByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindDiscountById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindDiscountOfProductUseInDate}")
    private String QUERY_DISCOUNT_OF_PRODUCT_IN_DATE;
    @Value("${sp_FindDiscountByProductId}")
    private String QUERY_DISCOUNTS_BY_PRODUCT_ID;
    @Value("${sp_FindDiscountByDateRange}")
    private String QUERY_DISCOUNTS_BY_DATE_RANGE;
    @Value("${sp_CheckExistDiscount}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllDiscount}")
    private String COUNT_ALL;
    @Value("${sp_CountDiscountWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public Long insert(Discount discount) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    discount.getCode(),
                    discount.getRate(),
                    discount.getAppliedType().toString(),
                    discount.getMaxAmount().doubleValue(),
                    Timestamp.valueOf(discount.getAppliedDate()),
                    Timestamp.valueOf(discount.getEndedDate())
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Discount discount) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    discount.getId(),
                    discount.getCode(),
                    discount.getRate(),
                    discount.getAppliedType().toString(),
                    discount.getMaxAmount().doubleValue(),
                    Timestamp.valueOf(discount.getAppliedDate()),
                    Timestamp.valueOf(discount.getEndedDate())
            );
        } catch (DataAccessException err) {
            log.error("[DISCOUNT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long discountId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    discountId
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
            log.error("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(DiscountFilter filter) {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_WITH_CONDITION,
                    Long.class,
                    filter.getObject(false)
            );
            return Objects.requireNonNull(count);
        } catch (DataAccessException | NullPointerException err) {
            log.error("[COUNT WITH CONDITION] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public boolean isExists(Discount discount) {
        try {
            Collection<Discount> existsDiscount = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new DiscountMapper(),
                    discount.getCode(),
                    discount.getRate(),
                    discount.getAppliedType().toString(),
                    discount.getMaxAmount().doubleValue(),
                    Timestamp.valueOf(discount.getAppliedDate()),
                    Timestamp.valueOf(discount.getEndedDate())
            );
            return existsDiscount.size() > 0;
        } catch (DataAccessException err) {
            log.error("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Discount> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new DiscountMapper(),
                    pagingOption.getObject()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Discount> findWithFilter(DiscountFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new DiscountMapper(),
                    filter.getObject(true)
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND FILTER] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Discount findById(Long discountId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new DiscountMapper(),
                    discountId
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Discount findDiscountByProductIdUseInDate(String productId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_DISCOUNT_OF_PRODUCT_IN_DATE,
                    new DiscountMapper(),
                    productId,
                    Date.valueOf(LocalDate.now())
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY PRODUCT USE IN DATE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Discount> findDiscountByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_DISCOUNTS_BY_PRODUCT_ID,
                    new DiscountMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY PRODUCT ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Discount> findDiscountByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return jdbcTemplate.query(
                    QUERY_DISCOUNTS_BY_DATE_RANGE,
                    new DiscountMapper(),
                    Timestamp.valueOf(startDate),
                    Timestamp.valueOf(endDate)
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY DATE RANGE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
