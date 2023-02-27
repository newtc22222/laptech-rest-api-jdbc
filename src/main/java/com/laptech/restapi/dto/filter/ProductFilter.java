package com.laptech.restapi.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nhat Phi
 * @since 2023-02-24
 */
@Getter
@Setter
public class ProductFilter extends BaseFilter{
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

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.brandId);
        objects.add(this.categoryId);
        objects.add(this.name);
        objects.add(Date.valueOf(this.releasedDate));
        objects.add(this.quantityInStock);
        objects.add(this.listedPrice);
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
