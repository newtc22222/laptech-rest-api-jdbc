package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.ProductUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 10-12-2022
 */
@Getter
@Setter
@ApiModel("Class representing for ProductItem request body")
public class ProductUnitDTO {
    @ApiModelProperty("This is need when user store their wanted unit")
    private String cartId;
    @ApiModelProperty("This is need when user make a bill")
    private String invoiceId;
    @ApiModelProperty(required = true)
    private String productId;
    @ApiModelProperty(required = true)
    private Integer quantity;
    @ApiModelProperty(required = true)
    private BigDecimal price;
    @ApiModelProperty(required = true)
    private BigDecimal discountPrice;

    public static ProductUnit transform(ProductUnitDTO unitDTO) {
        ProductUnit unit = new ProductUnit();
        unit.setId(UUID.randomUUID().toString());
        if (unitDTO.getCartId() != null) {
            unit.setCartId(unitDTO.getCartId());
        }
        if (unitDTO.getInvoiceId() != null) {
            unit.setInvoiceId(unitDTO.getInvoiceId());
        }
        unit.setProductId(unitDTO.getProductId());
        unit.setQuantity(unitDTO.getQuantity());
        unit.setPrice(unitDTO.getPrice());
        unit.setDiscountPrice(unitDTO.getDiscountPrice());
        return unit;
    }
}

