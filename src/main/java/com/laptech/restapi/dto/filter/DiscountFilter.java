package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.enums.DiscountType;
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
public class DiscountFilter extends BaseFilter {
    private String code;
    private Float rate;
    private DiscountType appliedType;
    private BigDecimal maxAmount;
    private LocalDate appliedDate;
    private LocalDate endedDate;

    public DiscountFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                          LocalDate deletedDate, Boolean isDel, String updateBy, String code, Float rate,
                          DiscountType appliedType, BigDecimal maxAmount, LocalDate appliedDate, LocalDate endedDate) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.code = code;
        this.rate = rate;
        this.appliedType = appliedType;
        this.maxAmount = maxAmount;
        this.appliedDate = appliedDate;
        this.endedDate = endedDate;
    }
}
