package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.ProductUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 10-12-2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Class representing for ProductItem request body")
public class ProductUnitDTO {
    private String id;
    @ApiModelProperty("This is need when user store their wanted unit")
    private String cartId;
    @ApiModelProperty("This is need when user make a bill")
    private String invoiceId;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String productId;
    private String productName;
    @ApiModelProperty(required = true)
    @NotNull
    private Integer quantity;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal price;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal discountPrice;
    @Size(max = 100)
    private String updateBy;

    public static ProductUnit transform(ProductUnitDTO dto) {
        String newProductUnitId = dto.getId() == null
                ? UUID.randomUUID().toString().replace("-", "").substring(0, 15)
                : dto.getId();

        ProductUnit unit = new ProductUnit();
        unit.setId(newProductUnitId);
        if (dto.getCartId() != null) {
            unit.setCartId(dto.getCartId());
        }
        if (dto.getInvoiceId() != null) {
            unit.setInvoiceId(dto.getInvoiceId());
        }
        unit.setProductId(dto.getProductId());
        unit.setProductName(dto.getProductName());
        unit.setQuantity(dto.getQuantity());
        unit.setPrice(dto.getPrice());
        unit.setDiscountPrice(dto.getDiscountPrice());
        unit.setUpdateBy(dto.getUpdateBy());
        return unit;
    }
}

