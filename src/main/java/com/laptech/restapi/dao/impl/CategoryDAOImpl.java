package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.CategoryDAO;
import com.laptech.restapi.mapper.CategoryMapper;
import com.laptech.restapi.model.Category;
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
 * @since 2022-11-21
 */

@Transactional
@Log4j2
@Component
@PropertySource("query.properties")
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "joshua_tbl_category";
    private final String INSERT = String.format("insert into %s values (0, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set name=?, image=?, description=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXISTS = String.format("select * from %s where " +
            "name=? and image=? and description=?", TABLE_NAME);

    @Override
    public Long insert(Category category) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, category.getName());
                ps.setString(2, category.getImage());
                ps.setString(3, category.getDescription());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Category category) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    category.getName(),
                    category.getImage(),
                    category.getDescription(),
                    category.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(Long categoryId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    categoryId
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
    public boolean isExists(Category category) {
        try {

        Category existsCategory = jdbcTemplate.queryForObject(
                QUERY_CHECK_EXISTS,
                new CategoryMapper(),
                category.getName(),
                category.getImage(),
                category.getDescription()
        );
        return existsCategory != null;
        }
        catch (DataAccessException err) {
            log.error(err);
            return false;
        }
    }

    @Override
    public List<Category> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new CategoryMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Category> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new CategoryMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public Category findById(Long categoryId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new CategoryMapper(),
                    categoryId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }
}
