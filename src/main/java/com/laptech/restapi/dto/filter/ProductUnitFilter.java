package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class ProductUnitFilter extends BaseFilter {
    private String productId;
    private String cartId;
    private String invoiceId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discountPrice;

    public ProductUnitFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                             LocalDate deletedDate, Boolean isDel, String updateBy, String productId, String cartId,
                             String invoiceId, Integer quantity, BigDecimal price, BigDecimal discountPrice) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.productId = productId;
        this.cartId = cartId;
        this.invoiceId = invoiceId;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
    }
}
