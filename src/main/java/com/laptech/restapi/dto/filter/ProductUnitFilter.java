package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private BigDecimal discountPrice; // maybe remove

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

    public ProductUnitFilter(Map<String, String> params) {
        super(params);
        this.productId = params.get("productId");
        this.cartId = params.get("cartId");
        this.invoiceId = params.get("invoiceId");
        this.quantity = (params.get("quantity") != null) ? Integer.parseInt(params.get("quantity")) : null;
        this.price = (params.get("price") != null) ? new BigDecimal(params.get("price")) : null;
        this.discountPrice = (params.get("discountPrice") != null) ? new BigDecimal(params.get("discountPrice")) : null;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.productId);
        objects.add(this.cartId);
        objects.add(this.invoiceId);
        objects.add(this.quantity);
        objects.add(this.price);
        objects.add(this.discountPrice);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
