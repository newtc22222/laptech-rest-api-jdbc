package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductDiscountDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class ProductDiscountDAOImpl implements ProductDiscountDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertProductDiscount}")
    private String INSERT;
    @Value("${sp_DeleteProductDiscount}")
    private String REMOVE;

    @Override
    public int insert(String productId, Long discountId) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    productId,
                    discountId
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int remove(String productId, Long discountId) {
        try {
            return jdbcTemplate.update(
                    REMOVE,
                    productId,
                    discountId
            );
        } catch (DataAccessException err) {
            log.error("[REMOVE] {}", err.getLocalizedMessage());
            return 0;
        }
    }
}

