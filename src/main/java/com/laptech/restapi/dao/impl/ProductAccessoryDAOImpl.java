package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductAccessoryDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class ProductAccessoryDAOImpl implements ProductAccessoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "tbl_product_accessory";
    private final String INSERT = String.format("insert into %s values (?, ?)", TABLE_NAME);
    private final String REMOVE = String.format("delete from %s where product_id=? and accessory_id=?", TABLE_NAME);

    @Override
    public int insert(String productId, String accessoryId) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    productId,
                    accessoryId
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int remove(String productId, String accessoryId) {
        try {
            return jdbcTemplate.update(
                    REMOVE,
                    productId,
                    accessoryId
            );
        } catch (Exception err) {
            log.error(err);
            return 0;
        }
    }
}
