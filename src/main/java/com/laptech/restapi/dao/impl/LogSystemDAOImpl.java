package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.LogSystemDAO;
import com.laptech.restapi.dto.filter.LogSystemFilter;
import com.laptech.restapi.mapper.LogSystemMapper;
import com.laptech.restapi.model.LogSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2023-02-27
 */
@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class LogSystemDAOImpl implements LogSystemDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_FindAllLogSystems}")
    private String QUERY_ALL;
    @Value("${sp_FindLogSystemByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_CountAllLogSystem}")
    private String COUNT_ALL;
    @Value("${sp_CountLogSystemWithCondition}")
    private String COUNT_WITH_CONDITION;


    @Override
    public Collection<LogSystem> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new LogSystemMapper(),
                    pagingOption.getObject()
            );
        }
        catch (DataAccessException err) {
            log.error("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<LogSystem> findByFilter(LogSystemFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new LogSystemMapper(),
                    filter.getObject(true)
            );
        }
        catch (DataAccessException err) {
            log.error("[FIND FILTER] {}", err.getLocalizedMessage());
            return null;
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
        }
        catch (NullPointerException | DataAccessException err) {
            log.error("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long count(LogSystemFilter filter) {
        try {
            Long count = jdbcTemplate.queryForObject(
                    COUNT_WITH_CONDITION,
                    Long.class,
                    filter.getObject(false)
            );
            return Objects.requireNonNull(count);
        }
        catch (NullPointerException | DataAccessException err) {
            log.error("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }
}
