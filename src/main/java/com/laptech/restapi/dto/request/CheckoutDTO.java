package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.ProductUnit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class CheckoutDTO {
    private String cartId;
    private String invoiceId;
    @ApiModelProperty(required = true)
    @NotNull
    private Long userId;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(min = 20, max = 255)
    private String address;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(min = 10, max = 15)
    private String phone;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal paymentAmount;

    private Double shipCost;
    private BigDecimal discountAmount;
    private BigDecimal tax;
    private BigDecimal paymentTotal;

    private String paymentType;
    private Boolean isPaid;
    private String orderStatus;

    private String note;
    @Size(max = 100)
    private String updateBy;

    private List<ProductUnit> productUnitList;
}
