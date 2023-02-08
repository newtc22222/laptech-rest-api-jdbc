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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-18
 */

@Transactional
@Log4j2
@Component
@PropertySource("query.properties")
public class BrandDAOImpl implements BrandDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_CreateNewBrand}")
    private String INSERT;
    @Value("${sp_UpdateBrand}")
    private String UPDATE;
    @Value("${sp_DeleteBrand}")
    private String DELETE;

    @Value("${sp_GetAllBrands}")
    private String QUERY_ALL;
    @Value("${sp_GetBrandWithId}")
    private String QUERY_ONE_BY_ID;

    @Override
    public Long insert(Brand brand) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, brand.getName());
                ps.setNString(2, brand.getCountry());
                ps.setDate(3, Date.valueOf(brand.getEstablishDate()));
                ps.setString(4, brand.getLogo());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error(err);
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
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(Long brandId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    brandId
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
    public boolean isExists(Brand brand) {
        String QUERY_CHECK_EXISTS = "select * from joshua_tbl_brand where " +
                "name=? and country=? and establish_date=? and logo=?";
        Brand existBrand = jdbcTemplate.queryForObject(
                QUERY_CHECK_EXISTS,
                new BrandMapper(),
                brand.getName(),
                brand.getCountry(),
                brand.getEstablishDate(),
                brand.getLogo()
        );
        return existBrand != null;
    }

    @Override
    public List<Brand> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new BrandMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Brand> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new BrandMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }
}