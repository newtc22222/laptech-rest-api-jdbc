package com.laptech.restapi.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * This class is used to update price of product
 *
 * @author Nhat Phi
 * @since 2022-11-22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceDTO {
    @ApiModelProperty(required = true)
    @NotEmpty
    private String id;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal listedPrice;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal price;
    @ApiModelProperty(required = true)
    @NotNull
    private String updateBy;

}
