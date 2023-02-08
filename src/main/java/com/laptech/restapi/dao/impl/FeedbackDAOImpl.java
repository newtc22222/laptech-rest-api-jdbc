package com.laptech.restapi.dao.impl;

import com.laptech.restapi.dao.FeedbackDAO;
import com.laptech.restapi.mapper.FeedbackMapper;
import com.laptech.restapi.model.Feedback;
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
 * @author Nhat Phi
 * @since 2022-11-21
 */

@Transactional
@Log4j2
@Component
@PropertySource("query.properties")
public class FeedbackDAOImpl implements FeedbackDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Query String
    private final String TABLE_NAME = "joshua_tbl_feedback";
    private final String INSERT = String.format("insert into %s values (?, ?, ?, ?, ?, , now(), now())", TABLE_NAME);
    private final String UPDATE = String.format("update %s " +
            "set content=?, rating_point=?, modified_date=now() where id=? and product_id=? and user_id=?", TABLE_NAME);
    private final String DELETE = String.format("remove from %s where id=?", TABLE_NAME);

    private final String QUERY_ALL = String.format("select * from %s order by created_date desc", TABLE_NAME);
    private final String QUERY_LIMIT = String.format("select * from %s order by created_date desc limit ? offset ?", TABLE_NAME);
    private final String QUERY_ONE_BY_ID = String.format("select * from %s where id=?", TABLE_NAME);
    private final String QUERY_CHECK_EXITS = String.format("select * from %s where " +
            "content=? and rating_point=? and product_id=? and user_id=?", TABLE_NAME);

    private final String QUERY_ALL_FEEDBACKS_OF_PRODUCT =
            String.format("select * from %s where product_id=?", TABLE_NAME);
    private final String QUERY_ALL_FEEDBACKS_OF_USER =
            String.format("select * from %s where user_id=?", TABLE_NAME);
    private final String QUERY_ALL_FEEDBACKS_BY_RATING_POINT =
            String.format("select * from %s where rating_point=?", TABLE_NAME);
    private final String QUERY_ALL_FEEDBACKS_OF_PRODUCT_BY_RATING_POINT =
            String.format("select * from %s where product_id=? and rating_point=?", TABLE_NAME);

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
            log.error(err);
            return null;
        }
    }

    @Override
    public int update(Feedback feedback) {
        try {
            return jdbcTemplate.update(
                    UPDATE,
                    feedback.getContent(),
                    feedback.getRatingPoint(),
                    feedback.getId(),
                    feedback.getProductId(),
                    feedback.getUserId()
            );
        } catch (DataAccessException err) {
            log.error(err);
            return 0;
        }
    }

    @Override
    public int delete(String feedbackId) {
        try {
            return jdbcTemplate.update(
                    DELETE,
                    feedbackId
            );
        } catch (DataAccessException err) {
            log.error(err);
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
    public int count() {
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
            log.error(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
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
            log.warn(err);
            return null;
        }
    }
}
