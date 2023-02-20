package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.CategoryDAO;
import com.laptech.restapi.mapper.CategoryMapper;
import com.laptech.restapi.model.Category;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "tbl_category";
    @Value("${sp_InsertNewCategory}")
    private String INSERT;
    @Value("${sp_UpdateCategory}")
    private String UPDATE;
    @Value("${sp_DeleteCategory}")
    private String DELETE;
    @Value("${sp_FindAllCategories}")
    private String QUERY_ALL;
    @Value("${sp_FindAllCategoriesLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindCategoryById}")
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXISTS = String.format("select * from %s where " +
            "name=? and image=? and description=?", TABLE_NAME);

    @Override
    public Long insert(Category category) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    category.getName(),
                    category.getImage(),
                    category.getDescription()
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Category category) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    category.getId(),
                    category.getName(),
                    category.getImage(),
                    category.getDescription()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
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
            log.error("[DELETE] {}", err.getLocalizedMessage());
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
        } catch (DataAccessException err) {
            log.error("[CHECK EXIST] {}", err.getLocalizedMessage());
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
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
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
            log.error("[FIND LIMIT] {}", err.getLocalizedMessage());
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
            log.error("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
