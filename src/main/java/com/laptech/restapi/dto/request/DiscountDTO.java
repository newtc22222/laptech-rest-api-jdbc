package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.DiscountType;
import com.laptech.restapi.model.Discount;
import com.laptech.restapi.util.ConvertDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Nhat Phi
 * @since 2022-11-25
 */
@Getter
@Setter
@ApiModel("Class representing for discount request body")
public class DiscountDTO {
    private Long id;
    @ApiModelProperty(required = true, example = "HASAGI")
    @NotNull
    @Size(min = 3, max = 20)
    private String code;
    @ApiModelProperty(required = true, example = "0.5", notes = "value in range 0 to 1")
    @NotNull
    private Float rate;
    @ApiModelProperty(required = true, example = "PURCHASE | PRODUCT")
    @NotEmpty
    private String appliedType;
    @ApiModelProperty(required = true)
    @NotNull
    private BigDecimal maxAmount;
    @ApiModelProperty(required = true, example = "2023-02-07")
    @NotNull
    private String appliedDate;
    @ApiModelProperty(required = true, example = "2023-02-07")
    @NotNull
    private String endedDate;
    @Size(max = 100)
    private String updateBy;

    public DiscountDTO() {}

    public DiscountDTO(Long id, String code, Float rate, String appliedType, BigDecimal maxAmount,
                       String appliedDate, String endedDate, String updateBy) {
        this.id = (id == null) ? 0L : id;
        this.code = code;
        this.rate = rate;
        this.appliedType = appliedType;
        this.maxAmount = maxAmount;
        this.appliedDate = appliedDate;
        this.endedDate = endedDate;
        this.updateBy = updateBy;
    }

    public static Discount transform(DiscountDTO dto) {
        Discount discount = new Discount();
        discount.setId(dto.getId());
        discount.setCode(dto.getCode());
        discount.setRate(dto.getRate());

        try {
            discount.setAppliedType(DiscountType.valueOf(dto.getAppliedType()));
        } catch (IllegalArgumentException err) {
            discount.setAppliedType(DiscountType.PRODUCT);
        }

        discount.setMaxAmount(dto.getMaxAmount());
        discount.setAppliedDate(ConvertDateTime.getDateTimeFromString(dto.getAppliedDate()));
        discount.setEndedDate(ConvertDateTime.getDateTimeFromString(dto.getEndedDate()));
        discount.setUpdateBy(dto.getUpdateBy());
        return discount;
    }
}

