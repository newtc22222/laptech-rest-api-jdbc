package com.laptech.restapi.dao.impl;

import com.laptech.restapi.common.dto.PagingOptionDTO;
import com.laptech.restapi.dao.FeedbackDAO;
import com.laptech.restapi.dto.filter.FeedbackFilter;
import com.laptech.restapi.mapper.FeedbackMapper;
import com.laptech.restapi.model.Feedback;
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
 * @since 2022-11-21
 */

@Transactional
@Slf4j
@Component
@PropertySource("classpath:query.properties")
public class FeedbackDAOImpl implements FeedbackDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sp_InsertNewFeedback}")
    private String INSERT;
    @Value("${sp_UpdateFeedback}")
    private String UPDATE;
    @Value("${sp_DeleteFeedback}")
    private String DELETE;

    @Value("${sp_FindAllFeedbacks}")
    private String QUERY_ALL;
    @Value("${sp_FindFeedbackByFilter}")
    private String QUERY_FILTER;
    @Value("${sp_FindFeedbackById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindFeedbackByProductId}")
    private String QUERY_ALL_FEEDBACKS_OF_PRODUCT;
    @Value("${sp_FindFeedbackByUserId}")
    private String QUERY_ALL_FEEDBACKS_OF_USER;
    @Value("${sp_CheckExistFeedback}")
    private String QUERY_CHECK_EXITS;

    @Value("${sp_CountAllFeedback}")
    private String COUNT_ALL;
    @Value("${sp_CountFeedbackWithCondition}")
    private String COUNT_WITH_CONDITION;

    @Override
    public String insert(Feedback feedback) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    feedback.getId(),
                    feedback.getProductId(),
                    feedback.getUserId(),
                    feedback.getContent(),
                    feedback.getRatingPoint(),
                    feedback.getUpdateBy()
            );
            return feedback.getId();
        } catch (DataAccessException err) {
            log.error("[INSERT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public int update(Feedback feedback) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    feedback.getId(),
                    feedback.getProductId(),
                    feedback.getUserId(),
                    feedback.getContent(),
                    feedback.getRatingPoint(),
                    feedback.getUpdateBy()
            );
        } catch (DataAccessException err) {
            log.error("[UPDATE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public int delete(String feedbackId, String updateBy) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    feedbackId,
                    updateBy
            );
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public Collection<Feedback> findFeedbackByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_FEEDBACKS_OF_PRODUCT,
                    new FeedbackMapper(),
                    productId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND FEEDBACK BY PRODUCT ID]: {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Feedback> findFeedbackByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_FEEDBACKS_OF_USER,
                    new FeedbackMapper(),
                    userId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND FEEDBACK BY USER ID]: {}", err.getLocalizedMessage());
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
        } catch (DataAccessException | NullPointerException err) {
            log.error("[COUNT ALL] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public long countWithFilter(FeedbackFilter filter) {
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
    public boolean isExists(Feedback feedback) {
        try {
            Collection<Feedback> existsFeedback = jdbcTemplate.query(
                    QUERY_CHECK_EXITS,
                    new FeedbackMapper(),
                    feedback.getContent(),
                    feedback.getRatingPoint(),
                    feedback.getProductId(),
                    feedback.getUserId()
            );
            return existsFeedback.size() > 0;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Collection<Feedback> findAll(PagingOptionDTO pagingOption) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new FeedbackMapper(),
                    pagingOption.getObject()
            );
        } catch (DataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Collection<Feedback> findWithFilter(FeedbackFilter filter) {
        try {
            return jdbcTemplate.query(
                    QUERY_FILTER,
                    new FeedbackMapper(),
                    filter.getObject(true)
            );
        } catch (DataAccessException err) {
            log.warn("[FIND FILTER] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public Feedback findById(String feedbackId) {
        try {
            return jdbcTemplate.queryForObject(
                    QUERY_ONE_BY_ID,
                    new FeedbackMapper(),
                    feedbackId
            );
        } catch (DataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
