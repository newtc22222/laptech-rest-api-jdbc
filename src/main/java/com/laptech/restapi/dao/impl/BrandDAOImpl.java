package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.BrandDAO;
import com.laptech.restapi.dto.filter.BrandFilter;
import com.laptech.restapi.mapper.BrandMapper;
import com.laptech.restapi.model.Brand;
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
 * @since 2022-11-18
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class BrandDAOImpl implements BrandDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewBrand}")
    private String INSERT;
    @Value("${sp_UpdateBrand}")
    private String UPDATE;
    @Value("${sp_DeleteBrand}")
    private String DELETE;

    @Value("${sp_CheckExistBrand}")
    private String QUERY_CHECK_EXISTS;
    @Value("${sp_FindAllBrands}")
    private String QUERY_ALL;
    @Value("${sp_FindBrandByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindBrandById}")
    private String QUERY_ONE_BY_ID;

    @Value("${sp_CountAllBrand}")
    private String COUNT_ALL;
    @Value("${sp_CountBrandWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public Long insert(Brand brand) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    brand.getName(),
                    brand.getCountry(),
                    brand.getEstablishDate(),
                    brand.getLogo(),
                    brand.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[CREATE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Brand brand) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    brand.getId(),
                    brand.getName(),
                    brand.getCountry(),
                    brand.getEstablishDate(),
                    brand.getLogo(),
                    brand.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long brandId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    brandId,
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
            log.warn("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(BrandFilter filter) {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_WITH_CONDITION,
                    Long.class,
                    filter.getObject(false)
            );
            return Objects.requireNonNull(count);
        } catch (DataAccessException | NullPointerException err) {
            log.warn("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public boolean isExists(Brand brand) {
        try {
            Collection<Brand> existBrand = jdbcTemplate.query(
                    QUERY_CHECK_EXISTS,
                    new BrandMapper(),
                    brand.getName(),
                    brand.getCountry(),
                    brand.getEstablishDate()
            );
            return existBrand.size() > 0;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Brand> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new BrandMapper(),
                    pagingOption.getObject()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Brand> findWithFilter(BrandFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new BrandMapper(),
                    filter.getObject(true)
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND WITH FILTER] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Brand findById(Long brandId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new BrandMapper(),
                    brandId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}