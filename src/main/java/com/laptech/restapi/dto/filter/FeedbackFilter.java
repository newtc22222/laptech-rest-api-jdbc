package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter @Setter
public class FeedbackFilter extends BaseFilter {
    private String productId;
    private Long userId;
    private String content;
    private Byte ratingPoint;

    public FeedbackFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                          LocalDate deletedDate, Boolean isDel, String updateBy, String productId, Long userId,
                          String content, Byte ratingPoint) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.ratingPoint = ratingPoint;
    }
}
