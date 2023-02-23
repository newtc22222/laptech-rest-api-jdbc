package com.laptech.restapi.model;

import com.laptech.restapi.common.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends BaseModel {
    private long id;
    private String code;
    private float rate;
    private DiscountType appliedType;
    private BigDecimal maxAmount;
    private LocalDateTime appliedDate;
    private LocalDateTime endedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return id == discount.id
                && Float.compare(discount.rate, rate) == 0
                && code.equals(discount.code)
                && appliedType == discount.appliedType
                && maxAmount.equals(discount.maxAmount)
                && appliedDate.equals(discount.appliedDate)
                && endedDate.equals(discount.endedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", rate=" + rate +
                ", appliedType=" + appliedType +
                ", maxAmount=" + maxAmount +
                ", appliedDate=" + appliedDate +
                ", endedDate=" + endedDate +
                "} " + super.toString();
    }
}
