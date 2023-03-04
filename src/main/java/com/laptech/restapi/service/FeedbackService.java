package com.laptech.restapi.service;

import com.laptech.restapi.model.Feedback;

import java.util.Set;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */

public interface FeedbackService extends BaseService<Feedback, String> {
    Set<Feedback> getAllFeedbacksOfProduct(String productId);

    Set<Feedback> getAllFeedbacksOfUser(long userId);
}
