package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.CategoryDAO;
import com.laptech.restapi.dto.filter.CategoryFilter;
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

import java.util.Collection;
import java.util.Objects;

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

    @Value("${sp_InsertNewCategory}")
    private String INSERT;
    @Value("${sp_UpdateCategory}")
    private String UPDATE;
    @Value("${sp_DeleteCategory}")
    private String DELETE;
    @Value("${sp_FindAllCategories}")
    private String QUERY_ALL;
    @Value("${sp_FindCategoryByFilter}")
    private String QUERY_WITH_CONDITION;
    @Value("${sp_FindCategoryById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_CheckExistCategory}")
    private String QUERY_CHECK_EXISTS;

    @Value("${sp_CountAllCategory}")
    private String COUNT_ALL;
    @Value("${sp_CountCategoryWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public Long insert(Category category) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    category.getName(),
                    category.getImage(),
                    category.getDescription(),
                    category.getUpdateBy()
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
                    category.getDescription(),
                    category.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long categoryId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    categoryId,
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
    public long countWithFilter(CategoryFilter filter) {
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
    public boolean isExists(Category category) {
        try {
            Collection<Category> existsCategory = jdbcTemplate.query(
                    QUERY_CHECK_EXISTS,
                    new CategoryMapper(),
                    category.getName(),
                    category.getImage(),
                    category.getDescription()
            );
            return existsCategory.size() > 0;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Category> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new CategoryMapper(),
                    pagingOption.getObject()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Category> findWithFilter(CategoryFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_WITH_CONDITION,
                    new CategoryMapper(),
                    filter.getObject(true)
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND FILTER] {}", err.getLocalizedMessage());
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
