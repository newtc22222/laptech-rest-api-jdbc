package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.BrandDAO;
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

import java.util.List;

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
    private final String QUERY_CHECK_EXISTS = String.format("select * from %s " +
            "where name=? and country=? and establish_date=? and logo=?", "tbl_brand");
    @Value("${sp_FindAllBrands}")
    private String QUERY_ALL;
    @Value("${sp_FindAllBrandsLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindBrandById}")
    private String QUERY_ONE_BY_ID;

    @Override
    public Long insert(Brand brand) {
        try {
            Long newBrandId = jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    brand.getName(),
                    brand.getCountry(),
                    brand.getEstablishDate(),
                    brand.getLogo()
            );
            System.out.println(newBrandId);
            return newBrandId;
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
                    brand.getLogo()
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
                    brandId
            );
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long count() {
        return this.findAll().size();
    }

    @Override
    public boolean isExists(Brand brand) {
        try {
            Brand existBrand = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXISTS,
                    new BrandMapper(),
                    brand.getName(),
                    brand.getCountry(),
                    brand.getEstablishDate(),
                    brand.getLogo()
            );
            return existBrand != null;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Brand> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new BrandMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Brand> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new BrandMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
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