package com.laptech.restapi.dao;

import com.laptech.restapi.model.Feedback;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public interface FeedbackDAO extends BaseDAO<Feedback, String> {
    List<Feedback> findFeedbackByProductId(String productId);

    List<Feedback> findFeedbackByUserId(long userId);

    List<Feedback> findFeedbackByRatingPoint(byte ratingPoint); // maybe useful

    List<Feedback> findFeedbackByProductIdAndRatingPoint(String productId, byte ratingPoint);
}
