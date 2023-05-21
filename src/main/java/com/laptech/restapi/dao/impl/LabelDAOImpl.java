package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.LabelDAO;
import com.laptech.restapi.dto.filter.LabelFilter;
import com.laptech.restapi.mapper.LabelMapper;
import com.laptech.restapi.model.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class LabelDAOImpl implements LabelDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${sp_InsertNewLabel}")
    private String INSERT;
    @Value("${sp_UpdateLabel}")
    private String UPDATE;
    @Value("${sp_DeleteLabel}")
    private String DELETE;
    @Value("${sp_FindAllLabels}")
    private String QUERY_ALL;
    @Value("${sp_FindLabelByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindLabelById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindLabelByProductId}")
    private String QUERY_LABEL_BY_PRODUCT_ID;
    @Value("${sp_CheckExistLabel}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllLabel}")
    private String COUNT_ALL;
    @Value("${sp_CountLabelWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public Long insert(Label label) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    label.getName(),
                    label.getIcon(),
                    label.getTitle(),
                    label.getDescription(),
                    label.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Label label) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    label.getId(),
                    label.getName(),
                    label.getIcon(),
                    label.getTitle(),
                    label.getDescription(),
                    label.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long labelId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    labelId,
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
            log.error("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(LabelFilter filter) {
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
    public boolean isExists(Label label) {
        try {
            Collection<Label> existsLabel = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new LabelMapper(),
                    label.getName(),
                    label.getIcon(),
                    label.getTitle(),
                    label.getDescription()
            );
            return existsLabel.size() > 0;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Label> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new LabelMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Label> findWithFilter(LabelFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new LabelMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
            log.warn("[FIND FILTER] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Label findById(Long labelId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new LabelMapper(),
                    labelId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Label> findLabelByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_LABEL_BY_PRODUCT_ID,
                    new LabelMapper(),
                    productId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY PRODUCT ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
