package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Feedback;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
public class FeedbackDTO {
    private String id;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String productId;
    @ApiModelProperty(required = true)
    private Long userId;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(min = 20, max = 255)
    private String content;
    @ApiModelProperty(example = "5", notes = "value in range 1 to 5")
    @NotNull
    private Byte ratingPoint;
    @Size(max = 100)
    private String updateBy;

    public FeedbackDTO() {
    }

    public FeedbackDTO(String id, String productId, Long userId, String content, Byte ratingPoint, String updateBy) {
        this.id = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.ratingPoint = ratingPoint;
        this.updateBy = updateBy;
    }

    public static Feedback transform(FeedbackDTO dto) {
        String newFeedbackId = dto.getId() != null
                ? dto.getId()
                : UUID.randomUUID().toString().replace("-", "").substring(0, 15);

        Feedback feedback = new Feedback();
        feedback.setId(newFeedbackId);
        feedback.setProductId(dto.getProductId());
        feedback.setUserId(dto.getUserId());
        feedback.setContent(dto.getContent());
        feedback.setRatingPoint(dto.getRatingPoint());
        feedback.setUpdateBy(dto.getUpdateBy());
        return feedback;
    }
}
