package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.BannerDAO;
import com.laptech.restapi.mapper.BannerMapper;
import com.laptech.restapi.model.Banner;
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

import java.sql.Date;
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
@PropertySource("classpath:query.properties")
public class BannerDAOImpl implements BannerDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "tbl_banner";
    private final String INSERT = String.format("insert into %s values (0, ?, ?, ?, ?, ?, ?, now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set path=?, type=?, title=?, link_product=?, used_date=?, ended_date=?, modified_date=now() where id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=? limit 1", TABLE_NAME);
    private final String QUERY_CHECK_EXISTS = String.format("select * from %s where " +
            "path=? and type=? and title=? and link_product=? and used_date=? and ended_date=?", TABLE_NAME);
    // started_date - using_date(use-end) - ended_date
    private final String QUERY_BANNERS_BY_DATE_RANGE = String.format("select * from %s " +
            "where ended_date > ? or used_date < ?", TABLE_NAME);
    private final String QUERY_BANNERS_BY_DATE = String.format("select * from %s " +
            "where ? between used_date and ended_date", TABLE_NAME);
    private final String QUERY_BANNERS_BY_TYPE = String.format("select * from %s where type = ?", TABLE_NAME);

    @Override
    public Long insert(Banner banner) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, banner.getPath());
                ps.setNString(2, banner.getType());
                ps.setNString(3, banner.getTitle());
                ps.setString(4, banner.getLinkProduct());
                ps.setDate(5, Date.valueOf(banner.getUsedDate()));
                ps.setDate(6, Date.valueOf(banner.getEndedDate()));
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException | NullPointerException err) {
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Banner banner) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    banner.getPath(),
                    banner.getType(),
                    banner.getTitle(),
                    banner.getLinkProduct(),
                    banner.getUsedDate(),
                    banner.getEndedDate(),
                    banner.getId()
            );
        } catch (DataAccessException err) {
            log.error(err);
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
            log.error(err);
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
        }
        catch (DataAccessException err) {
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }
}
