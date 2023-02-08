package com.laptech.restapi.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-20 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportProduct extends SetupDate {
    private String id;
    private String productId;
    private long quantity;
    private BigDecimal importedPrice;
    private LocalDateTime importedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportProduct that = (ImportProduct) o;
        return quantity == that.quantity
                && id.equals(that.id)
                && productId.equals(that.productId)
                && importedPrice.equals(that.importedPrice)
                && importedDate.equals(that.importedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId);
    }
}
