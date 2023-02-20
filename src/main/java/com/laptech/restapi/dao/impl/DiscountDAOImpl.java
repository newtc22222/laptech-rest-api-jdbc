package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.DiscountType;
import com.laptech.restapi.dao.DiscountDAO;
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
import java.util.List;

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
    @Value("${sp_FindAllDiscountsLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindDiscountById}")
    private String QUERY_ONE_BY_ID;
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "code=? and rate=? and applied_type=? and max_amount=? and applied_date=? and ended_date=?", "tbl_discount");
    @Value("${sp_FindDiscountOfProductUseInDate}")
    private String QUERY_DISCOUNT_OF_PRODUCT_IN_DATE;
    @Value("${sp_FindDiscountByCode}")
    private String QUERY_DISCOUNTS_BY_CODE;

    // Query in another table (tbl_product_discount)
    @Value("${sp_FindDiscountByProductId}")
    private String QUERY_DISCOUNTS_BY_PRODUCT_ID;
    @Value("${sp_FindDiscountByDateRange}")
    private String QUERY_DISCOUNTS_BY_DATE_RANGE;
    @Value("${sp_FindDiscountByType}")
    private String QUERY_DISCOUNTS_BY_TYPE;

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
    public int delete(Long discountId) {
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
    public int count() {
        return this.findAll().size();
    }

    @Override
    public boolean isExists(Discount discount) {
        try {

            Discount existsDiscount = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new DiscountMapper(),
                    discount.getCode(),
                    discount.getRate(),
                    discount.getAppliedType().toString(),
                    discount.getMaxAmount().doubleValue(),
                    Timestamp.valueOf(discount.getAppliedDate()),
                    Timestamp.valueOf(discount.getEndedDate())
            );
            return existsDiscount != null;
        } catch (DataAccessException err) {
            log.error("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Discount> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new DiscountMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Discount> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new DiscountMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND LIMIT] {}", err.getLocalizedMessage());
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
    public List<Discount> findDiscountByCode(String code) {
        try {
            return jdbcTemplate.query(
                    QUERY_DISCOUNTS_BY_CODE,
                    new DiscountMapper(),
                    "%" + code + "%"
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY CODE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Discount> findDiscountByProductId(String productId) {
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
    public List<Discount> findDiscountByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
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

    @Override
    public List<Discount> findDiscountByType(DiscountType type) {
        try {
            return jdbcTemplate.query(
                    QUERY_DISCOUNTS_BY_TYPE,
                    new DiscountMapper(),
                    type.toString()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY TYPE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
