package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.ImageType;
import com.laptech.restapi.model.ProductImage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2022-11-26
 */
@Getter
@Setter
@ApiModel("Class representing for ProductImage request body")
public class ProductImageDTO {
    private String feedbackId;
    @ApiModelProperty(required = true)
    private String productId;
    @ApiModelProperty(required = true)
    private String url;
    @ApiModelProperty(required = true, example = "ADVERTISE | DETAIL | EXTRA | FEEDBACK")
    private String type;

    public static ProductImage transform(ProductImageDTO productImageDTO) {
        ProductImage image = new ProductImage();
        image.setId(UUID.randomUUID().toString());
        if (productImageDTO.getFeedbackId() != null) {
            image.setFeedbackId(productImageDTO.getFeedbackId());
        }
        image.setProductId(productImageDTO.getProductId());
        image.setUrl(productImageDTO.getUrl());
        image.setType(ImageType.valueOf(productImageDTO.getType()));
        return image;
    }
}

