package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.RefreshTokenDAO;
import com.laptech.restapi.mapper.RefreshTokenMapper;
import com.laptech.restapi.model.RefreshToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @since 2023-02-08
 */
@Transactional
@Log4j2
@Component
@PropertySource("classpath:query.properties")
public class RefreshTokenDAOImpl implements RefreshTokenDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String TABLE_NAME = "tbl_refresh_token"; // tbl_refresh_token

    private final String TIME_USE = "2 day";
    private final String INSERT = String.format("insert into %s value(?, ?, now(), addDate(now(), interval %s))", TABLE_NAME, TIME_USE);
    private final String DELETE = String.format("delete from %s where code=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_CODE = String.format("select * from %s where code=?", TABLE_NAME);
    private final String QUERY_REFRESH_TOKEN_BY_USER_ID = String.format("select * from %s where user_id=?", TABLE_NAME);

    @Override
    public int insert(RefreshToken token) {
        try {
            return jdbcTemplate.update(
                    INSERT,
                    token.getCode(),
                    token.getUserId()
            );
        } catch (DataAccessException err) {
            log.error("[INSERT]: {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String code) {
        try {
            return jdbcTemplate.update(DELETE, code);
        } catch (DataAccessException err) {
            log.error("[DELETE]: {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int count() {
        return this.findAll().size();
    }

    @Override
    public List<RefreshToken> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new RefreshTokenMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND ALL]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<RefreshToken> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new RefreshTokenMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND LIMIT]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public RefreshToken findRefreshTokenByCode(String code) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_CODE,
                    new RefreshTokenMapper(),
                    code
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND BY CODE]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<RefreshToken> findRefreshTokenByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_REFRESH_TOKEN_BY_USER_ID,
                    new RefreshTokenMapper(),
                    userId
            );
        } catch (EmptyResultDataAccessException err) {
            log.error("[FIND TOKEN BY USER ID]: {}", err.getLocalizedMessage());
            return null;
        }
    }
}
