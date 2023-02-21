package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.LabelDAO;
import com.laptech.restapi.mapper.LabelMapper;
import com.laptech.restapi.model.Label;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2023-02-02
 */
@Transactional
@Log4j2
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
    @Value("${sp_FindAllLabelsLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindLabelById}")
    private String QUERY_ONE_BY_ID;
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "name=? and icon=? and title=? and description=?", "tbl_label");

    @Value("${sp_FindLabelByProductId}")
    private String QUERY_LABEL_BY_PRODUCT_ID;
    @Value("${sp_FindLabelByName}")
    private String QUERY_LABEL_BY_NAME;
    @Value("${sp_FindLabelByTitle}")
    private String QUERY_LABEL_BY_TITLE;

    @Override
    public Long insert(Label label) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    label.getName(),
                    label.getIcon(),
                    label.getTitle(),
                    label.getDescription()
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
                    label.getDescription()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long labelId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    labelId
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
    public boolean isExists(Label label) {
        try {
            Label existsLabel = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new LabelMapper(),
                    label.getName(),
                    label.getIcon(),
                    label.getTitle(),
                    label.getDescription()
            );
            return existsLabel != null;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Label> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new LabelMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Label> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new LabelMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
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
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Label> findLabelByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_LABEL_BY_PRODUCT_ID,
                    new LabelMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY PRODUCT ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Label> findLabelByName(String name) {
        try {
            return jdbcTemplate.query(
                    QUERY_LABEL_BY_NAME,
                    new LabelMapper(),
                    "%" + name + "%"
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY NAME] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Label> findLabelByTitle(String title) {
        try {
            return jdbcTemplate.query(
                    QUERY_LABEL_BY_TITLE,
                    new LabelMapper(),
                    "%" + title + "%"
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY TITLE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
