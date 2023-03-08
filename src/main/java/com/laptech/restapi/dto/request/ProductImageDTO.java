package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.model.ProductImage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-26
 */
@Getter
@Setter
@ApiModel("Class representing for ProductImage request body")
public class ProductImageDTO {
    private String id;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String productId;
    private String feedbackId;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(max = 255)
    private String url;
    @ApiModelProperty(required = true, example = "ADVERTISE | DETAIL | EXTRA | FEEDBACK")
    @NotEmpty
    @Size(min = 3, max = 50)
    private String type;
    @Size(max = 100)
    private String updateBy;

    public ProductImageDTO() {}

    public ProductImageDTO(String id, String productId, String feedbackId, String url, String type, String updateBy) {
        this.id = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        this.productId = productId;
        this.feedbackId = feedbackId;
        this.url = url;
        this.type = type;
        this.updateBy = updateBy;
    }

    public static ProductImage transform(ProductImageDTO dto) {
        ProductImage image = new ProductImage();
        image.setId(dto.getId());
        image.setFeedbackId(dto.getFeedbackId());
        image.setProductId(dto.getProductId());
        image.setUrl(dto.getUrl());
        try {
            image.setType(ImageType.valueOf(dto.getType()));
        } catch (IllegalArgumentException err) {
            image.setType(ImageType.ADVERTISE);
        }
        image.setUpdateBy(dto.getUpdateBy());
        return image;
    }
}

