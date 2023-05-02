package com.laptech.restapi.samples.products;

import java.math.BigDecimal;

public class Product {
    private String id;
    private String name;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String[] imageList;

    public Product(String id, String name, BigDecimal price, BigDecimal discountPrice, String[] imageList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imageList = imageList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String[] getImageList() {
        return imageList;
    }

    public void setImageList(String[] imageList) {
        this.imageList = imageList;
    }
}
