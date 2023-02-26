package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.FeedbackDAO;
import com.laptech.restapi.mapper.FeedbackMapper;
import com.laptech.restapi.model.Feedback;
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
 * @since 2022-11-21
 */

@Transactional
@Log4j2
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
    @Value("${sp_FindAllFeedbacksLimit}")
    private String QUERY_LIMIT;
    @Value("${sp_FindFeedbackById}")
    private String QUERY_ONE_BY_ID;
    @Value("${sp_FindFeedbackByProductId}")
    private String QUERY_ALL_FEEDBACKS_OF_PRODUCT;
    @Value("${sp_FindFeedbackByUserId}")
    private String QUERY_ALL_FEEDBACKS_OF_USER;
    @Value("${sp_FindFeedbackByRatingPoint}")
    private String QUERY_ALL_FEEDBACKS_BY_RATING_POINT;
    @Value("${sp_FindFeedbackByProductIdAndRatingPoint}")
    private String QUERY_ALL_FEEDBACKS_OF_PRODUCT_BY_RATING_POINT;

    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "content=? and rating_point=? and product_id=? and user_id=?", "tbl_feedback");

    @Override
    public String insert(Feedback feedback) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    feedback.getId(),
                    feedback.getProductId(),
                    feedback.getUserId(),
                    feedback.getContent(),
                    feedback.getRatingPoint()
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
                    feedback.getRatingPoint()
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
                    feedbackId
            );
        } catch (DataAccessException err) {
            log.error("[DELETE] {}", err.getLocalizedMessage());
            return 0;
        }
    }

    @Override
    public List<Feedback> findFeedbackByProductId(String productId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_FEEDBACKS_OF_PRODUCT,
                    new FeedbackMapper(),
                    productId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public List<Feedback> findFeedbackByUserId(long userId) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_FEEDBACKS_OF_USER,
                    new FeedbackMapper(),
                    userId
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn(err);
            return null;
        }
    }

    @Override
    public long count() {
        return this.findAll().size();
    }

    @Override
    public boolean isExists(Feedback feedback) {
        try {
            Feedback existsFeedback = jdbcTemplate.queryForObject(
                    QUERY_CHECK_EXITS,
                    new FeedbackMapper(),
                    feedback.getContent(),
                    feedback.getRatingPoint(),
                    feedback.getProductId(),
                    feedback.getUserId()
            );
            return existsFeedback != null;
        } catch (DataAccessException err) {
            log.warn("[CHECK EXIST] {}", err.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Feedback> findAll() {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL,
                    new FeedbackMapper()
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND ALL] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Feedback> findAll(long limit, long skip) {
        try {
            return jdbcTemplate.query(
                    QUERY_LIMIT,
                    new FeedbackMapper(),
                    limit,
                    skip
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND LIMIT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Feedback> findFeedbackByRatingPoint(byte ratingPoint) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_FEEDBACKS_BY_RATING_POINT,
                    new FeedbackMapper(),
                    ratingPoint
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY RATING POINT] {}", err.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<Feedback> findFeedbackByProductIdAndRatingPoint(String productId, byte ratingPoint) {
        try {
            return jdbcTemplate.query(
                    QUERY_ALL_FEEDBACKS_OF_PRODUCT_BY_RATING_POINT,
                    new FeedbackMapper(),
                    productId,
                    ratingPoint
            );
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY PRODUCT ID AND RATING POINT] {}", err.getLocalizedMessage());
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
        } catch (EmptyResultDataAccessException err) {
            log.warn("[FIND BY ID] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
