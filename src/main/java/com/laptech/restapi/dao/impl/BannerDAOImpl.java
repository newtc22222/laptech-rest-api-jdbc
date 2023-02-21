package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.BannerDAO;
import com.laptech.restapi.mapper.BannerMapper;
import com.laptech.restapi.model.Banner;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */

@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class BannerDAOImpl implements BannerDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewBanner}")
    private String INSERT;
    @Value("${sp_UpdateBanner}")
    private String UPDATE;
    @Value("${sp_DeleteBanner}")
    private String DELETE;

    @Value("${sp_FindAllBanners}")
    private String QUERY_ALL;
    @Value("${sp_FindAllBannersLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindBannerById}")
    private String QUERY_ONE_BY_ID;

    private final String QUERY_CHECK_EXISTS = String.format("select * from %s where " +
            "path=? and type=? and title=? and link_product=? and used_date=? and ended_date=?", "tbl_banner");

    @Value("${sp_FindBannerByDateRange}")
    private String QUERY_BANNERS_BY_DATE_RANGE;
    @Value("${sp_FindBannerByDate}")
    private String QUERY_BANNERS_BY_DATE;
    @Value("${sp_FindBannerByType}")
    private String QUERY_BANNERS_BY_TYPE;

    @Override
    public Long insert(Banner banner) {
        try {
            return jdbcTemplate.queryForObject(
                    INSERT,
                    Long.class,
                    banner.getPath(),
                    banner.getType(),
                    banner.getTitle(),
                    banner.getLinkProduct(),
                    banner.getUsedDate(),
                    banner.getEndedDate()
            );
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Banner banner) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    banner.getId(),
                    banner.getPath(),
                    banner.getType(),
                    banner.getTitle(),
                    banner.getLinkProduct(),
                    banner.getUsedDate(),
                    banner.getEndedDate()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long bannerId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    bannerId
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
    public boolean isExists(Banner banner) {
        try {
            Banner existBanner = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXISTS,
                    new BannerMapper(),
                    banner.getPath(),
                    banner.getType(),
                    banner.getTitle(),
                    banner.getLinkProduct(),
                    banner.getUsedDate(),
                    banner.getEndedDate()
            );
            return existBanner != null;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Banner> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new BannerMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Banner> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new BannerMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Banner findById(Long bannerId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new BannerMapper(),
                    bannerId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Banner> findBannerByDateRange(Date startDate, Date ended_date) {
        try {
            return jdbcTemplate.query(
                    QUERY_BANNERS_BY_DATE_RANGE,
                    new BannerMapper(),
                    startDate,
                    ended_date
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY DATE RANGE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Banner> findBannerByDate(Date date) {
        try {
            return jdbcTemplate.query(
                    QUERY_BANNERS_BY_DATE,
                    new BannerMapper(),
                    date
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY DATE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Banner> findBannerByType(String type) {
        try {
            return jdbcTemplate.query(
                    QUERY_BANNERS_BY_TYPE,
                    new BannerMapper(),
                    type
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY TYPE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
