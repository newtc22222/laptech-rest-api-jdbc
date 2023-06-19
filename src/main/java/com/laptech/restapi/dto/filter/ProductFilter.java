package com.laptech.restapi.dto.filter;

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
public class ProductFilter extends BaseFilter {
    private Long brandId;
    private Long categoryId;
    private String name;
    private LocalDate releasedDate;
    private Integer quantityInStock;
    private BigDecimal listedPrice;

    public ProductFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                         LocalDate deletedDate, Boolean isDel, String updateBy, Long brandId, Long categoryId,
                         String name, LocalDate releasedDate, Integer quantityInStock, BigDecimal listedPrice) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.releasedDate = releasedDate;
        this.quantityInStock = quantityInStock;
        this.listedPrice = listedPrice;
    }

    public ProductFilter(Map<String, String> params) {
        super(params);
        this.brandId = (params.get("brandId") != null) ? Long.parseLong(params.get("brandId")) : null;
        this.categoryId = (params.get("categoryId") != null) ? Long.parseLong(params.get("categoryId")) : null;
        this.name = params.get("name");
        this.releasedDate = ConvertDate.getLocalDateFromString(params.get("releasedDate"));
        this.quantityInStock = (params.get("quantityInStock") != null) ? Integer.parseInt(params.get("quantityInStock")) : null;
        this.listedPrice = (params.get("listedPrice") != null) ? new BigDecimal(params.get("listedPrice")) : null;
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.brandId);
        objects.add(this.categoryId);
        objects.add(this.name);
        objects.add(this.releasedDate != null ? Date.valueOf(this.releasedDate) : null);
        objects.add(this.quantityInStock);
        objects.add(this.listedPrice);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
