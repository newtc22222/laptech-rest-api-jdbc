package com.laptech.restapi.dto.response;

import com.laptech.restapi.model.Discount;
import com.laptech.restapi.model.Product;
import com.laptech.restapi.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Nhat Phi
 * @since 2023-03-02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUnitCardDTO {
    private String id;
    private String cartId;
    private String invoiceId;
    private Product product;
    private String imageRepresent;
    private Collection<Discount> discounts;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountPrice;
}
