package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductAccessoryDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Component
@RequiredArgsConstructor
@PropertySource("classpath:query.properties")
public class ProductAccessoryDAOImpl implements ProductAccessoryDAO {
    private final JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertProductAccessory}")
    private String INSERT;
    @Value("${sp_DeleteProductAccessory}")
    private String REMOVE;

    @Override
    public int insert(String productId, String accessoryId) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    productId,
                    accessoryId
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
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
            log.error("[REMOVE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updateMultiple(String productId, List<String> accessoryIdAddList, List<String> accessoryIdRemoveList) {
        try {
            accessoryIdAddList
                    .stream()
                    .parallel()
                    .forEach(accessoryId -> jdbcTemplate.update(
                            INSERT,
                            productId,
                            accessoryId
                    ));
            accessoryIdRemoveList
                    .stream()
                    .parallel()
                    .forEach(accessoryId -> jdbcTemplate.update(
                            REMOVE,
                            productId,
                            accessoryId
                    ));
            return 1;
        } catch (DataAccessException err) {
            log.error("[UPDATE MULTIPLE ACCESSORIES] {}", err.getLocalizedMessage());
            return 0;
        }
    }
}
