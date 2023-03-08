package com.laptech.restapi.dto.filter;

import com.laptech.restapi.common.enums.DiscountType;
import com.laptech.restapi.util.ConvertDate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
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

    public DiscountFilter(Map<String, String> params) {
        super(params);
        this.code = params.get("code");
        this.rate = (params.get("rate") != null) ? Float.parseFloat(params.get("rate")) : null;
        this.appliedType = DiscountType.valueOf(params.get("appliedType"));
        this.maxAmount = (params.get("maxAmount") != null) ? new BigDecimal(params.get("maxAmount")) : null;
        this.appliedDate = ConvertDate.getLocalDateFromString(params.get("appliedDate"));
        this.endedDate = ConvertDate.getLocalDateFromString(params.get("endedDate"));
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.code);
        objects.add(this.rate);
        objects.add(this.appliedType.toString());
        objects.add(this.maxAmount);
        objects.add(Date.valueOf(this.appliedDate));
        objects.add(Date.valueOf(this.endedDate));
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
