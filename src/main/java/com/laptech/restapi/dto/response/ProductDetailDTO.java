package com.laptech.restapi.dto.response;

import com.laptech.restapi.model.Label;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @since 2023-02-07
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductDetailDTO {
    private Product product;
    private String brandName;
    private String categoryName;
    private BigDecimal discountPrice;
    private List<Label> labelList;
    private List<ProductImage> imageList;
    private List<Product> accessories;
}
