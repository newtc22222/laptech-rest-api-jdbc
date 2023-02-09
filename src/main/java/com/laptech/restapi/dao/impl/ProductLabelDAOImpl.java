package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductLabelDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since 2023-02-04
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class ProductLabelDAOImpl implements ProductLabelDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "tbl_product_label"; // tbl_product_label
    private final String INSERT = String.format("insert into %s values (?, ?)", TABLE_NAME);
    private final String REMOVE = String.format("delete from %s where product_id=? and label_id=?", TABLE_NAME);

    @Override
    public int insert(String productId, Long labelId) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    productId,
                    labelId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int remove(String productId, Long labelId) {
        try {
            return jdbcTemplate.update(
                    REMOVE,
                    productId,
                    labelId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }
}
