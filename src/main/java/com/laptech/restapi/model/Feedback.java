package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseModel {
    private String id;
    private String productId;
    private long userId;
    private String content;
    private byte ratingPoint;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return userId == feedback.userId
                && ratingPoint == feedback.ratingPoint
                && id.equals(feedback.id)
                && productId.equals(feedback.productId)
                && content.equals(feedback.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, userId);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", ratingPoint=" + ratingPoint +
                "} " + super.toString();
    }
}
