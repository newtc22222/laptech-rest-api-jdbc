package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.BannerDAO;
import com.laptech.restapi.dto.filter.BannerFilter;
import com.laptech.restapi.mapper.BannerMapper;
import com.laptech.restapi.model.Banner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */

@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class BannerDAOImpl implements BannerDAO {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewBanner}")
    private String INSERT;
    @Value("${sp_UpdateBanner}")
    private String UPDATE;
    @Value("${sp_DeleteBanner}")
    private String DELETE;

    @Value("${sp_FindAllBanners}")
    private String QUERY_ALL;
    @Value("${sp_FindBannerByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindBannerById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindBannerByDateRange}")
    private String QUERY_BANNERS_BY_DATE_RANGE;
    @Value("${sp_FindBannerByDate}")
    private String QUERY_BANNERS_BY_DATE;

    @Value("${sp_CountAllBanner}")
    private String QUERY_COUNT;
    @Value("${sp_CountBannerWithCondition}")
    private String QUERY_COUNT_WITH_CONDITION;
    @Value("${sp_CheckExistBanner}")
    private String QUERY_CHECK_EXISTS;

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
                    banner.getEndedDate(),
                    banner.getUpdateBy()
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
                    banner.getEndedDate(),
                    banner.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(Long bannerId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    bannerId,
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
                    QUERY_COUNT, Long.class
            );
            return (count != null) ? count : 0;
        } catch (DataAccessException | NullPointerException err) {
            log.error("[COUNT] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(BannerFilter filter) {
        try {
            Long count = jdbcTemplate.queryForObject(
                    QUERY_COUNT_WITH_CONDITION,
                    Long.class,
                    filter.getObject(false)
            );
            return (count != null) ? count : 0;
        } catch (DataAccessException | NullPointerException err) {
            log.error("[COUNT WITH CONDITION] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public boolean isExists(Banner banner) {
        try {
            Collection<Banner> existBanner = jdbcTemplate.query(
                    QUERY_CHECK_EXISTS,
                    new BannerMapper(),
                    banner.getPath(),
                    banner.getType(),
                    banner.getTitle(),
                    banner.getLinkProduct(),
                    banner.getUsedDate(),
                    banner.getEndedDate()
            );
            return existBanner.size() > 0;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Banner> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new BannerMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Banner> findWithFilter(BannerFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new BannerMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
            log.warn("[FIND FILTER] {}", err.getLocalizedMessage());
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
        } catch (DataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Banner> findBannerByDateRange(Date startDate, Date ended_date) {
        try {
            return jdbcTemplate.query(
                    QUERY_BANNERS_BY_DATE_RANGE,
                    new BannerMapper(),
                    startDate,
                    ended_date
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY DATE RANGE] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Banner> findBannerByDate(Date date) {
        try {
            return jdbcTemplate.query(
                    QUERY_BANNERS_BY_DATE,
                    new BannerMapper(),
                    date
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY DATE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
