package com.laptech.restapi.dao;

import com.laptech.restapi.dto.filter.FeedbackFilter;
import com.laptech.restapi.model.Feedback;

import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface FeedbackDAO extends BaseDAO<Feedback, FeedbackFilter, String> {
    Collection<Feedback> findFeedbackByProductId(String productId);

    Collection<Feedback> findFeedbackByUserId(long userId);
}
