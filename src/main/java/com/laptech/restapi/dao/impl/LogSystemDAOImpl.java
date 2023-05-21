package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.LogSystemDAO;
import com.laptech.restapi.dto.filter.LogSystemFilter;
import com.laptech.restapi.model.LogSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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
        return null;
    }

    @Override
    public Collection<LogSystem> findByFilter(LogSystemFilter filter) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long count(LogSystemFilter filter) {
        return 0;
    }
}
