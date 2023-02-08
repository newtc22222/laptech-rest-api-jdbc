package com.laptech.restapi.dto.response;

import com.laptech.restapi.model.Label;
import com.laptech.restapi.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @since 2023-02-07
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductCardDTO {
    private String id;
    private String name;
    private List<String> imagesRepresentUrl;
    private List<Label> labelList;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private LocalDateTime saleOffEndDate;
}
