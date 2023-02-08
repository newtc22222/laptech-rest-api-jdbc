package com.laptech.restapi.model;

import com.laptech.restapi.common.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is a blueprint for the image of product <br/>
 * Image will save as path
 *
 * @author Nhat Phi
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage extends SetupDate {
    private String id;
    private String productId;
    private String feedbackId;
    private String url;
    private ImageType type;

    @Override
    public String toString() {
        return "ProductImage{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", feedbackId='" + feedbackId + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                "} " + super.toString();
    }
}
