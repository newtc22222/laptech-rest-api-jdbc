package com.laptech.restapi.dto.filter;

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
}
