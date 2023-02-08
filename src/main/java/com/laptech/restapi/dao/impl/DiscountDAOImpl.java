package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.enums.DiscountType;
import com.laptech.restapi.dao.DiscountDAO;
import com.laptech.restapi.mapper.DiscountMapper;
import com.laptech.restapi.model.Discount;
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

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
@Transactional
@Log4j2
@Component
@PropertySource("query.properties")
public class DiscountDAOImpl implements DiscountDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Query String
    private final String TABLE_NAME = "joshua_tbl_discount";
    private final String INSERT = String.format("insert into %s values (0, ?, ?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set code=?, rate=?, applied_type=?, max_amount=?, applied_date=?, ended_date=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "code=? and rate=? and applied_type=? and max_amount=? and applied_date=? and ended_date=?", TABLE_NAME);
    private final String QUERY_DISCOUNT_OF_PRODUCT_IN_DATE =
            String.format("select d.* " +
                    "from %s d, joshua_tbl_product_discount pd " +
                    "where d.id = pd.discount_id " +
                    "and pd.product_id = ?" +
                    "and ? between d.applied_date and d.ended_date " +
                    "limit 1;", TABLE_NAME);
    private final String QUERY_DISCOUNTS_BY_CODE =
            String.format("select * from %s where code like ?", TABLE_NAME);

    // Query in another table (tbl_product_discount)
    private final String QUERY_DISCOUNTS_BY_PRODUCT_ID =
            String.format("select d.* " +
                    "from %s d, joshua_tbl_product_discount pd " +
                    "where d.id = pd.discount_id " +
                    "and pd.product_id = ?", TABLE_NAME);
    private final String QUERY_DISCOUNTS_BY_DATE_RANGE =
            String.format("select * from %s where ended_date >= ? or applied_date <= ?", TABLE_NAME);
    private final String QUERY_DISCOUNTS_BY_TYPE =
            String.format("select * from %s where applied_type=?", TABLE_NAME);

    @Override
    public Long insert(Discount discount) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, discount.getCode());
                ps.setFloat(2, discount.getRate());
                ps.setString(3, discount.getAppliedType().toString());
                ps.setBigDecimal(4, discount.getMaxAmount());
                ps.setTimestamp(5, Timestamp.valueOf(discount.getAppliedDate()));
                ps.setTimestamp(6, Timestamp.valueOf(discount.getEndedDate()));
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Discount discount) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    discount.getCode(),
                    discount.getRate(),
                    discount.getAppliedType().toString(),
                    discount.getMaxAmount().doubleValue(),
                    Timestamp.valueOf(discount.getAppliedDate()),
                    Timestamp.valueOf(discount.getEndedDate()),
                    discount.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
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
            log.error(err);
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
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }
}
