package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.LabelDAO;
import com.laptech.restapi.mapper.LabelMapper;
import com.laptech.restapi.model.Label;
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

    private final String TABLE_NAME = "tbl_label"; // tbl_label
    private final String INSERT = String.format("insert into %s values (0, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set name=?, icon=?, title=?, description=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "name=? and icon=? and title=? and description=?", TABLE_NAME);

    private final String QUERY_LABEL_BY_PRODUCT_ID = String.format("select * from %s " +
            "where id in (select label_id from %s where product_id=?)", TABLE_NAME, "tbl_label");
    private final String QUERY_LABEL_BY_NAME = String.format("select * from %s where name like ?", TABLE_NAME);
    private final String QUERY_LABEL_BY_TITLE = String.format("select * from %s where title like ?", TABLE_NAME);

    @Override
    public Long insert(Label label) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, label.getName());
                ps.setString(2, label.getIcon());
                ps.setString(3, label.getTitle());
                ps.setString(4, label.getDescription());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Label label) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    label.getName(),
                    label.getIcon(),
                    label.getTitle(),
                    label.getDescription(),
                    label.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
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
            log.error(err);
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
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }
}
