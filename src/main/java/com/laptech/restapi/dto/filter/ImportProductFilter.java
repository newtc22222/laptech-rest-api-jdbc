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
public class ImportProductFilter extends BaseFilter {
    private String productId;
    private Long quantity;
    private BigDecimal importedPrice;
    private LocalDate importedDate;

    public ImportProductFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                               LocalDate deletedDate, Boolean isDel, String updateBy, String productId,
                               Long quantity, BigDecimal importedPrice, LocalDate importedDate) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.productId = productId;
        this.quantity = quantity;
        this.importedPrice = importedPrice;
        this.importedDate = importedDate;
    }
}
