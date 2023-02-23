package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @version 2.0.0
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel {
    private String id;
    private Long brandId;
    private long categoryId;
    private String name;
    private LocalDate releasedDate;
    private int quantityInStock;
    private BigDecimal listedPrice;
    private String specifications;
    private String descriptionDetail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return categoryId == product.categoryId
                && quantityInStock == product.quantityInStock
                && id.equals(product.id)
                && Objects.equals(brandId, product.brandId)
                && name.equals(product.name)
                && releasedDate.equals(product.releasedDate)
                && listedPrice.equals(product.listedPrice)
                && specifications.equals(product.specifications)
                && descriptionDetail.equals(product.descriptionDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brandId, categoryId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", brandId=" + brandId +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", releasedDate=" + releasedDate +
                ", quantityInStock=" + quantityInStock +
                ", listedPrice=" + listedPrice +
                ", specifications='" + specifications + '\'' +
                ", descriptionDetail='" + descriptionDetail + '\'' +
                "} " + super.toString();
    }
}
