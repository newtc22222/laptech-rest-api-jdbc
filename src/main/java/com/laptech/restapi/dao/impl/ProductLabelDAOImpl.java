package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.ProductLabelDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @since 2023-02-04
 */
@Transactional
@Slf4j
@Component
@RequiredArgsConstructor
@PropertySource("classpath:query.properties")
public class ProductLabelDAOImpl implements ProductLabelDAO {
    private final JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertProductLabel}")
    private String INSERT;
    @Value("${sp_DeleteProductLabel}")
    private String REMOVE;

    @Override
    public int insert(String productId, Long labelId) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    productId,
                    labelId
            );
        } catch (DataAccessException err) {
            log.error("[ADD LABEL INTO PRODUCT] {}", err.getLocalizedMessage());
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
            log.error("[REMOVE LABEL FORM PRODUCT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int updateMultiple(String productId, List<Long> labelIdAddList, List<Long> labelIdRemoveList) {
        try {
            labelIdAddList
                    .stream()
                    .parallel()
                    .forEach(labelId -> jdbcTemplate.update(
                            INSERT,
                            productId,
                            labelId
                    ));
            labelIdRemoveList
                    .stream()
                    .parallel()
                    .forEach(labelId -> jdbcTemplate.update(
                            REMOVE,
                            productId,
                            labelId
                    ));
            return 1;
        } catch (DataAccessException err) {
            log.error("[UPDATE MULTIPLE LABELS] {}", err.getLocalizedMessage());
            return 0;
        }
    }
}
