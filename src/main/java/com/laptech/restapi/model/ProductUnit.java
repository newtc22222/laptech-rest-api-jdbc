package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * use with cartId or invoiceId
 *
 * @author Nhat Phi
 * @since 2022-11-20 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUnit extends SetupDate {
    private String id;
    private String productId;
    private String cartId;
    private String invoiceId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountPrice;

    @Override
    public String toString() {
        return "ProductUnit{" +
                "id='" + id + '\'' +
                ", cartId='" + cartId + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                "} " + super.toString();
    }
}
