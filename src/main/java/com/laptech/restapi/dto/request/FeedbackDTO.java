package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Feedback;

import java.util.Map;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
public class FeedbackDTO {
    public static Feedback transform(Map<String, String> request) {
        Feedback feedback = new Feedback();
        feedback.setId(UUID.randomUUID().toString());
        feedback.setProductId(request.get("productId"));
        feedback.setUserId(Long.parseLong(request.get("userId")));
        feedback.setContent(request.get("content"));
        feedback.setRatingPoint(Byte.parseByte(request.get("ratingPoint")));
        return feedback;
    }
}
