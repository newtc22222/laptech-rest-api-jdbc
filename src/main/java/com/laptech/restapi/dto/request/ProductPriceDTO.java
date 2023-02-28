package com.laptech.restapi.dto.request;

import java.math.BigDecimal;

/**
 * This class is used to update price of product
 *
 * @author Nhat Phi
 * @since 2022-11-22
 */
public class ProductPriceDTO {
    private String id;
    private BigDecimal listed_price;
    private BigDecimal price;
    private String updateBy;

    public ProductPriceDTO(String id, BigDecimal listed_price, BigDecimal price, String updateBy) {
        this.id = id;
        this.listed_price = listed_price;
        this.price = price;
        this.updateBy = updateBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getListed_price() {
        return listed_price;
    }

    public void setListed_price(BigDecimal listed_price) {
        this.listed_price = listed_price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
