package com.laptech.restapi.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceStatusDTO {
    @ApiModelProperty(required = true)
    @NotNull
    private String id;
    @ApiModelProperty(required = true, example = "cash | momo | paypal | ...")
    @NotNull
    @Size(max = 50)
    private String paymentType;
    @ApiModelProperty(required = true)
    @NotNull
    private Boolean isPaid;
    @ApiModelProperty(required = true)
    @NotNull
    private String orderStatus;
    private String note;
    @Size(max = 100)
    private String updateBy;
}

